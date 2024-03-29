package com.youlai.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.common.base.BasePageQuery;
import com.youlai.system.model.query.TradeQuery;
import com.youlai.system.model.entity.Record;
public interface RecordService extends IService<Record> {
    /**
    * 分页查询用户记录
    */
    public IPage<Record> getListByUserId(TradeQuery query);

    /**
    * 分页查询全部记录
    */
    public IPage<Record> getAllList(BasePageQuery query);
}
