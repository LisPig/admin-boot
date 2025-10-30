-- 动态表
CREATE TABLE applet_square_memos
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '动态ID',
    user_id    BIGINT NOT NULL COMMENT '发布用户ID',
    content    TEXT COMMENT '动态内容',
    imgs       TEXT COMMENT '图片链接，逗号分隔',
    tag_id      BIGINT COMMENT '话题标签ID',
    tag_name    VARCHAR(50) COMMENT '话题标签',
    like_count BIGINT DEFAULT 0 COMMENT '点赞数',
    comment_count BIGINT DEFAULT 0 COMMENT '评论数',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES mini_user (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT = '动态表';

-- 点赞表
CREATE TABLE applet_square_likes
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '点赞ID',
    memo_id     BIGINT NOT NULL COMMENT '动态ID',
    user_id     BIGINT NOT NULL COMMENT '点赞用户ID',
    linked_user BIGINT NOT NULL COMMENT '被点赞用户ID',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (memo_id) REFERENCES applet_square_memos (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES mini_user (id),
    UNIQUE KEY unique_like (memo_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT = '点赞表';

-- 评论表
CREATE TABLE applet_square_comments
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    memo_id     BIGINT      NOT NULL COMMENT '动态ID',
    user_id     BIGINT      NOT NULL COMMENT '评论用户ID',
    username    VARCHAR(50) NOT NULL COMMENT '评论者用户名',
    content     TEXT        NOT NULL COMMENT '评论内容',
    reply_to    VARCHAR(50) COMMENT '回复目标用户名',
    reply_to_id BIGINT COMMENT '回复目标评论ID',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (memo_id) REFERENCES applet_square_memos (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES mini_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT = '评论表';

-- 关注表
CREATE TABLE applet_square_follows
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关注ID',
    user_id          BIGINT NOT NULL COMMENT '关注者ID',
    followed_user_id BIGINT NOT NULL COMMENT '被关注者ID',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (user_id) REFERENCES mini_user (id),
    FOREIGN KEY (followed_user_id) REFERENCES mini_user (id),
    UNIQUE KEY unique_follow (user_id, followed_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT = '关注表';

