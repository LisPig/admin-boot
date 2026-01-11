package com.sz.applet.miniuser.controller;

import com.sz.applet.miniuser.pojo.dto.MessageLogCreateDTO;
import com.sz.applet.miniuser.pojo.dto.MessageLogListDTO;
import com.sz.applet.miniuser.pojo.dto.MessageLogUpdateDTO;
import com.sz.applet.miniuser.pojo.vo.MessageLogVO;
import com.sz.applet.miniuser.service.MessageLogService;
import com.sz.core.common.entity.ApiPageResult;
import com.sz.core.common.entity.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订阅消息发送日志表 Controller
 * </p>
 *
 * @author sz
 * @since 2026-01-07
 */
@Tag(name = "订阅消息发送日志表")
@RestController
@RequestMapping("message-log")
@RequiredArgsConstructor
public class MessageLogController {

    //private final MessageLogService messageLogService;

    @Operation(summary = "分页查询")
    @GetMapping("page")
    public ApiPageResult<PageResult<MessageLogVO>> page(MessageLogListDTO dto) {
        /*PageResult<MessageLogVO> pageResult = messageLogService.page(dto);
        return ApiPageResult.success(pageResult);*/
        return null;
    }

    @Operation(summary = "新增")
    @PostMapping
    public void create(@RequestBody MessageLogCreateDTO dto) {
        // 实现新增逻辑
    }

    @Operation(summary = "修改")
    @PutMapping
    public void update(@RequestBody MessageLogUpdateDTO dto) {
        // 实现修改逻辑
    }

    @Operation(summary = "删除")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        // 实现删除逻辑
    }

    @Operation(summary = "详情")
    @GetMapping("/{id}")
    public MessageLogVO detail(@PathVariable Long id) {
        // 实现详情逻辑
        return null;
    }
}