package com.youlai.system.service;

import com.youlai.system.model.dto.PreCreateTo;

/**
* @description: 支付接口
* @author Lovejj
* @date 2024/3/27 下午7:14
* @version 1.0
*/
public interface payService {
    /**
    * 创建付款码
    */
    public PreCreateTo preCreate(Integer money);

    public void query();

    public void cancel();

}
