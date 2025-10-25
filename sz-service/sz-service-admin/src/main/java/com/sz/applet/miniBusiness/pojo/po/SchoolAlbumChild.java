package com.sz.applet.miniBusiness.pojo.po;

import lombok.Data;
import lombok.experimental.Accessors;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.lang.Long;
import java.lang.Object;
import java.lang.String;

/**
 * 相册子集表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Accessors(chain = true)
@Data
@Table(value = "school_album_child")
public class SchoolAlbumChild {

    /**
     * ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 父相册ID
     */
    @Column(value = "album_id")
    private Long albumId;

    /**
     * 标题
     */
    @Column(value = "title")
    private String title;

    /**
     * 封面图url
     */
    @Column(value = "cover")
    private String cover;

    /**
     * 删除标识
     */
    @Column(value = "del_flag",isLogicDelete = true)
    private Object delFlag;


}
