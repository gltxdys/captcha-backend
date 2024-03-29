package com.youlai.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.youlai.system.common.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apache.poi.hpsf.Decimal;

import java.sql.Date;

@Data
@ToString
@Builder
public class Trade extends BaseEntity {
    @TableId(type = IdType.AUTO)
    @TableField("trade_no")
    private Long tradeNo;
    private Integer userId;
    private Double money;
    private int payType;
    private Integer score;
    private Date payTime;
    private int status;
}
