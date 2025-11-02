package com.sz.admin.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.sz.admin.system.mapper.CommonFileMapper;
import com.sz.admin.system.pojo.dto.sysfile.SysFileListDTO;
import com.sz.admin.system.pojo.po.SysFile;
import com.sz.admin.system.pojo.po.table.SysFileTableDef;
import com.sz.admin.system.service.SysFileService;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private final OssClient ossClient;
    
    private final LocalFileUploadService localFileUploadService;
    
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
            // 如果是local环境，使用本地文件上传服务
           /* if ("local".equals(activeProfile)) {
                uploadResult = localFileUploadService.upload(file, dirTag);
            } else {*/
                // 其他环境使用OSS上传
                uploadResult = ossClient.upload(file, dirTag);
            //}
            Long fileId = fileLog(uploadResult);
            uploadResult.setFileId(fileId);
        } catch (Exception e) {
            log.error(" sysFile upload error", e);
           // CommonResponseEnum.FILE_UPLOAD_ERROR.assertTrue(true);
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
}