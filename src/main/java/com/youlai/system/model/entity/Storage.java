package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.system.common.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Blob;

@Data
@ToString
@Builder
public class Storage extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private long id;
    private long userId;
    private Integer del;
    private byte[] storageData;
}
