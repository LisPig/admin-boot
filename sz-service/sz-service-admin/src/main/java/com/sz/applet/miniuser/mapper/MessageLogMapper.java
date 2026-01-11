package com.sz.applet.miniuser.mapper;

import com.mybatisflex.core.BaseMapper;
import com.sz.applet.miniuser.pojo.po.MessageLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订阅消息发送日志表 Mapper
 * </p>
 *
 * @author sz
 * @since 2026-01-07
 */
@Mapper
public interface MessageLogMapper extends BaseMapper<MessageLog> {

}