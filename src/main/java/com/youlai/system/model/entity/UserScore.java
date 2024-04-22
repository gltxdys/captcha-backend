package com.youlai.system.model.entity;

import com.youlai.system.common.base.BaseEntity;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class UserScore extends BaseEntity {
    private Long userId;
    private int score;
}
