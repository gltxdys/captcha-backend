package com.youlai.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.common.base.BasePageQuery;
import com.youlai.system.mapper.RecordMapper;
import com.youlai.system.model.query.TradeQuery;
import com.youlai.system.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youlai.system.model.entity.Record;

/**
 * 识别记录查询服务
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Service
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    RecordMapper recordMapper;

    public IPage<Record> getListByUserId(TradeQuery query) {
        Page<Record> page =new Page<>(query.getPageNum(),query.getPageSize());
        Page<Record> userId1 = recordMapper.selectPage(page, new QueryWrapper<Record>().eq("user_id", query.getUserId()));
        return userId1;
    }

    public IPage<Record> getAllList(BasePageQuery query) {
        Page<Record> page =new Page<>(query.getPageSize(),query.getPageNum());
        return this.getBaseMapper().selectPage(page,new QueryWrapper<>());
    }
}
