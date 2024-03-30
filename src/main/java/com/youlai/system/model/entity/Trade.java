package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlai.system.common.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Decimal;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@ToString
@Accessors(chain = true)
public class Trade extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @TableField("trade_no")
    private Long tradeNo;
    private Integer userId;
    private Double money;
    private int payType;
    private Integer score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    private int status;
}
