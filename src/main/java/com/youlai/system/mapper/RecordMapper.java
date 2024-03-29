package com.youlai.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.youlai.system.model.entity.Record;

import java.util.List;


@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    @Override
    List<Record> selectList(@Param(Constants.WRAPPER) Wrapper<Record> queryWrapper);
}
