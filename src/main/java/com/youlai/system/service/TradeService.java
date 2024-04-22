package com.youlai.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.model.dto.UserAuthInfo;
import com.youlai.system.model.entity.SysUser;
import com.youlai.system.model.entity.Trade;
import com.youlai.system.model.form.UserForm;
import com.youlai.system.model.query.TradeQuery;
import com.youlai.system.model.query.UserPageQuery;
import com.youlai.system.model.vo.UserExportVO;
import com.youlai.system.model.vo.UserInfoVO;
import com.youlai.system.model.vo.UserPageVO;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author haoxr
 * @since 2022/1/14
 */
public interface TradeService extends IService<Trade> {

    /**
     * 用户分页列表
     *
     * @return
     */
    IPage<Trade> getTradePage(TradeQuery query);

    boolean tradeSuccess(Trade trade);

}
