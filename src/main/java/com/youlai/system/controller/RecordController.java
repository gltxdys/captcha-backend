package com.youlai.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.system.common.result.PageResult;
import com.youlai.system.model.query.TradeQuery;
import com.youlai.system.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.youlai.system.model.entity.Record;
@Tag(name = "10.验证码识别订单接口")
@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {
    @Autowired
    RecordService recordService;

    @Operation(summary = "根据用户id获取用户的查询记录")
    @PostMapping("/user")
    @PreAuthorize("#tradeQuery.userId==principal.userId")
    public PageResult<Record> getListByUserId(@RequestBody TradeQuery tradeQuery){
        IPage<Record> listByUserId = recordService.getListByUserId(tradeQuery);
        return PageResult.success(listByUserId);
    }
}
