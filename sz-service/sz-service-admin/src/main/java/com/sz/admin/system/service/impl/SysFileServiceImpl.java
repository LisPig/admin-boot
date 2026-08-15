package com.sz.admin.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.CommonFileMapper;
import com.sz.admin.system.pojo.dto.sysfile.SysFileListDTO;
import com.sz.admin.system.pojo.po.SysFile;
import com.sz.admin.system.pojo.po.table.SysFileTableDef;
import com.sz.admin.system.service.SysFileService;
import com.sz.admin.system.service.MediaCheckService;
import com.sz.core.common.entity.PageResult;
import com.sz.core.common.enums.CommonResponseEnum;
import com.sz.core.common.exception.common.BusinessException;
import com.sz.core.util.BeanCopyUtils;
import com.sz.core.util.PageUtils;
import com.sz.core.util.Utils;
import com.sz.oss.OssClient;
import com.sz.oss.UploadResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sz
 * @since 2023-08-31
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<CommonFileMapper, SysFile> implements SysFileService {

    private static final int MAX_IMAGE_WIDTH = 1280;
    private static final int MAX_IMAGE_HEIGHT = 1280;
    private static final float JPEG_QUALITY = 0.70f;

    private final OssClient ossClient;

    private final LocalFileUploadService localFileUploadService;

    private final MediaCheckService mediaCheckService;
    
    @Value("${spring.profiles.active:dev}")
    private String activeProfile;

    /**
     * 文件列表
     *
     * @param dto
     *            dto
     * @return {@link PageResult}<{@link SysFile}>
     */
    @Override
    public PageResult<SysFile> fileList(SysFileListDTO dto) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (Utils.isNotNull(dto.getFilename())) {
            wrapper.where(SysFileTableDef.SYS_FILE.FILENAME.like(dto.getFilename()));
        }
        wrapper.orderBy(SysFileTableDef.SYS_FILE.CREATE_TIME.desc());
        Page<SysFile> page = page(PageUtils.getPage(dto), wrapper);
        return PageUtils.getPageResult(page);
    }

    /**
     * 上传文件
     *
     * @param file
     *            文件
     * @param dirTag
     *            文件夹标识
     * @return {@link String}
     */
    @Override
    public UploadResult uploadFile(MultipartFile file, String dirTag) {
        UploadResult uploadResult = null;
        try {
            MultipartFile uploadFile = compressImageIfNeeded(file);
            uploadResult = ossClient.upload(uploadFile, dirTag);
            Long fileId = fileLog(uploadResult);
            uploadResult.setFileId(fileId);
            // 图片内容安全校验(异步提交微信media_check_async,不阻塞上传响应)
            if (mediaCheckService.shouldCheck(dirTag, uploadFile.getContentType(), uploadResult.getUrl())) {
                mediaCheckService.submitAsyncCheck(fileId, uploadResult.getUrl());
            }
        } catch (Exception e) {
            log.error(" sysFile upload error", e);
            throw new BusinessException(CommonResponseEnum.FILE_UPLOAD_ERROR,null,e.getMessage());
        }
        return uploadResult;
    }

    @Override
    public Long fileLog(UploadResult uploadResult) {
        SysFile sysFile = BeanCopyUtils.copy(uploadResult, SysFile.class);
        this.save(sysFile);
        return sysFile.getId();
    }
    
    @Override
    public Boolean deleteFile(Long fileId) {
        SysFile sysFile = this.getById(fileId);
        if (sysFile == null) {
            return false;
        }
        
        // 删除实际文件（本地和OSS）
        try {
            // 删除OSS文件
            //if (!"local".equals(activeProfile)) {
                // 从objectName中提取实际的对象键
                String objectName = sysFile.getObjectName();
                // 注意：这里可能需要根据具体实现调整对象键的构造方式
                ossClient.delete(objectName);
            //}
            
   /*         // 删除本地文件（如果是local环境或者也保存了本地文件）
            // 根据LocalFileUploadService的实现，文件保存在项目目录外的uploads文件夹中
            String userDir = System.getProperty("user.dir");
            Path projectDir = Paths.get(userDir);
            Path parentDir = projectDir.getParent();
            Path localFilePath = parentDir.resolve("uploads").resolve(sysFile.getObjectName());
            
            if (Files.exists(localFilePath)) {
                Files.delete(localFilePath);
                log.info("本地文件删除成功: {}", localFilePath.toString());
            }*/
        } catch (Exception e) {
            log.error("删除文件失败，fileId: {}", fileId, e);
            // 即使物理文件删除失败，也继续删除数据库记录
        }
        
        // 删除文件记录
        this.removeById(fileId);

        return true;
    }

    /**
     * 图片压缩：尺寸超过1280px时等比缩放，JPEG使用0.70质量压缩，PNG仅缩放尺寸
     */
    private MultipartFile compressImageIfNeeded(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/") || "image/gif".equals(contentType)) {
            return file;
        }
        try {
            BufferedImage src = ImageIO.read(file.getInputStream());
            if (src == null) {
                return file;
            }
            int width = src.getWidth();
            int height = src.getHeight();
            if (width > MAX_IMAGE_WIDTH || height > MAX_IMAGE_HEIGHT) {
                double scale = Math.min((double) MAX_IMAGE_WIDTH / width, (double) MAX_IMAGE_HEIGHT / height);
                src = resizeImage(src, (int) (width * scale), (int) (height * scale));
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if ("image/jpeg".equals(contentType) || "image/jpg".equals(contentType)) {
                writeJpegWithQuality(src, baos, JPEG_QUALITY);
            } else {
                ImageIO.write(src, "png", baos);
            }
            byte[] compressedBytes = baos.toByteArray();
            if (compressedBytes.length >= file.getSize()) {
                return file;
            }
            log.info("图片压缩: {} {} -> {} bytes ({:.0f}%)",
                    file.getOriginalFilename(), file.getSize(), compressedBytes.length,
                    (1.0 - (double) compressedBytes.length / file.getSize()) * 100);
            return new ByteArrayMultipartFile(compressedBytes, file.getOriginalFilename(), contentType);
        } catch (Exception e) {
            log.warn("图片压缩失败，使用原始文件上传: {}", file.getOriginalFilename(), e);
            return file;
        }
    }

    private BufferedImage resizeImage(BufferedImage src, int targetWidth, int targetHeight) {
        java.awt.Image scaled = src.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH);
        BufferedImage result = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        result.getGraphics().drawImage(scaled, 0, 0, null);
        return result;
    }

    private void writeJpegWithQuality(BufferedImage image, ByteArrayOutputStream baos, float quality) throws IOException {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
            writer.write(null, new javax.imageio.IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }
    }

    /**
     * 基于字节数组的 MultipartFile 实现
     */
    private static class ByteArrayMultipartFile implements MultipartFile {
        private final byte[] content;
        private final String originalFilename;
        private final String contentType;

        ByteArrayMultipartFile(byte[] content, String originalFilename, String contentType) {
            this.content = content;
            this.originalFilename = originalFilename;
            this.contentType = contentType;
        }

        @Override
        public String getName() {
            return "file";
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public boolean isEmpty() {
            return content.length == 0;
        }

        @Override
        public long getSize() {
            return content.length;
        }

        @Override
        public byte[] getBytes() {
            return content;
        }

        @Override
        public java.io.InputStream getInputStream() {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(File dest) throws IOException {
            Files.write(dest.toPath(), content);
        }
    }
}