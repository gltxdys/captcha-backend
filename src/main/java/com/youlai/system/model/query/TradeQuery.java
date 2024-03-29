package com.youlai.system.model.query;

import com.youlai.system.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description ="订单分页查询对象")
@Data
public class TradeQuery extends BasePageQuery {
    /**
    * 用户id
    */
    public int userId;

}
