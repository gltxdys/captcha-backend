package com.youlai.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.youlai.system.model.entity.Trade;
import com.youlai.system.model.entity.UserScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserScoreMapper extends BaseMapper<UserScore> {
    @Override
    List<UserScore> selectList(@Param(Constants.WRAPPER) Wrapper<UserScore> queryWrapper);
}
