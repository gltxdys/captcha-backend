package com.youlai.system.controller;

import com.youlai.system.common.result.PageResult;
import com.youlai.system.model.entity.Trade;
import com.youlai.system.model.form.UserForm;
import com.youlai.system.model.query.TradeQuery;
import com.youlai.system.security.model.SysUserDetails;
import com.youlai.system.security.service.SysUserDetailsService;
import com.youlai.system.service.TradeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "09.支付订单接口")
@RestController
@RequestMapping("/api/v1/trade")
@RequiredArgsConstructor
public class TradeController {
    @Autowired
    TradeService tradeService;

    @PostMapping("/user")
    @PreAuthorize("#tradeQuery.userId==principal.userId")
    public PageResult<Trade> getTradeList(@RequestBody TradeQuery tradeQuery){
        return PageResult.success(tradeService.getTradePage(tradeQuery));
    }
}
