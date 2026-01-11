package com.sz.applet.miniBusiness.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 校友会活动用户参与表
 */
@Data
@Accessors(chain = true)
@Table(value = "applet_alumni_association_activity_user")
public class AppletAlumniAssociationActivityUser {
    private Long id;

    @Column(value = "alumni_association_id")
    private Long alumniAssociationId;

    @Column(value = "alumni_association_activity_id")
    private Long alumniAssociationActivityId;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "status")
    private String status;
}
