package com.youlai.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.common.constant.SystemConstants;
import com.youlai.system.common.util.DateUtils;
import com.youlai.system.converter.UserConverter;
import com.youlai.system.mapper.SysUserMapper;
import com.youlai.system.mapper.TradeMapper;
import com.youlai.system.model.bo.UserBO;
import com.youlai.system.model.bo.UserFormBO;
import com.youlai.system.model.dto.UserAuthInfo;
import com.youlai.system.model.entity.SysUser;
import com.youlai.system.model.entity.Trade;
import com.youlai.system.model.form.UserForm;
import com.youlai.system.model.query.TradeQuery;
import com.youlai.system.model.query.UserPageQuery;
import com.youlai.system.model.vo.UserExportVO;
import com.youlai.system.model.vo.UserInfoVO;
import com.youlai.system.model.vo.UserPageVO;
import com.youlai.system.security.service.PermissionService;
import com.youlai.system.security.util.SecurityUtils;
import com.youlai.system.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户业务实现类
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Service
@RequiredArgsConstructor
public class TradeServiceImpl extends ServiceImpl<TradeMapper, Trade> implements TradeService {

    @Autowired
    TradeMapper tradeMapper;
    public IPage<Trade> getTradePage(TradeQuery query) {
        // 参数构建
        Page<Trade> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<Trade> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",query.getUserId());
        return tradeMapper.selectPage(page, wrapper);
    }
}
