package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.youlai.system.common.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Builder
@Accessors(chain = true)
@TableName("identify_record")
public class Record extends BaseEntity {
    private Long id;
    private Long userId;
    private String picUrl;
    private int typeId;
    private String result;
    private int score;
    private int status;
    private Integer spend;
}
