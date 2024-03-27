package com.youlai.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.youlai.system.model.entity.Storage;
import com.youlai.system.model.entity.SysDept;
import com.youlai.system.plugin.mybatis.annotation.DataPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface StorageMapper extends BaseMapper<Storage> {

    @DataPermission(deptIdColumnName = "id")
    @Override
    List<Storage> selectList(@Param(Constants.WRAPPER) Wrapper<Storage> queryWrapper);
}
