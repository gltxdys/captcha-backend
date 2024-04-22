package com.youlai.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.system.model.entity.CaptchaType;
import com.youlai.system.model.entity.Trade;
import com.youlai.system.model.query.TradeQuery;
import com.youlai.system.model.vo.CaptchaDetailVo;
import com.youlai.system.model.vo.CaptchaTypeVo;

import java.util.List;

/**
* @description: 验证码类型接口
* @author Lovejj
* @date 2024/4/17 下午2:58
* @version 1.0
*/
public interface CaptchaTypeService extends IService<CaptchaType> {

    public List<CaptchaTypeVo> getAllCaptchaTypeVoList();

    public List<CaptchaDetailVo> getAllCaptchaDetailVoList();

    public void addCaptchaType(CaptchaDetailVo captchaTypeVO);
    public void updateCaptchaType(CaptchaDetailVo captchaTypeVO);
    public boolean delCaptchaType(Long id);

}
