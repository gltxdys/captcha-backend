package com.youlai.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.common.enums.TradeEnum;
import com.youlai.system.mapper.CaptchaTypeMapper;
import com.youlai.system.mapper.TradeMapper;
import com.youlai.system.mapper.UserScoreMapper;
import com.youlai.system.model.entity.CaptchaType;
import com.youlai.system.model.entity.Trade;
import com.youlai.system.model.query.TradeQuery;
import com.youlai.system.model.vo.CaptchaDetailVo;
import com.youlai.system.model.vo.CaptchaTypeVo;
import com.youlai.system.service.CaptchaTypeService;
import com.youlai.system.service.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @description: CaptchaType实现类
* @author Lovejj
* @date 2024/4/17 下午3:00
* @version 1.0
*/
@Service
@RequiredArgsConstructor
public class CaptchaTypeServiceImpl extends ServiceImpl<CaptchaTypeMapper, CaptchaType> implements CaptchaTypeService {

    private final CaptchaTypeMapper captchaTypeMapper;


    public List<CaptchaTypeVo> getAllCaptchaTypeVoList() {
        List<CaptchaType> captchaTypes = captchaTypeMapper.selectList(new QueryWrapper<CaptchaType>().select("type_id", "type_name", "details", "price", "example_url"));
        List<CaptchaTypeVo> captchaTypeVoList = new ArrayList<>();
        captchaTypes.forEach(item->captchaTypeVoList.add(new CaptchaTypeVo(item)));
        return captchaTypeVoList;
    }

    @Override
    public List<CaptchaDetailVo> getAllCaptchaDetailVoList() {
        List<CaptchaType> list = captchaTypeMapper.selectList(new QueryWrapper<CaptchaType>().select("type_id,type_name,details,price,example_url,req_param,rep_param"));
        List<CaptchaDetailVo> captchaDetailVoList =new ArrayList<>();
        list.forEach(item->captchaDetailVoList.add(new CaptchaDetailVo(item)));
        return captchaDetailVoList;
    }


    @Override
    public void addCaptchaType(CaptchaDetailVo captchaDetailVo) {
        captchaTypeMapper.insert((CaptchaType.transToCaptchaType(captchaDetailVo)));
    }

    public void updateCaptchaType(CaptchaDetailVo captchaDetailVo) {
        captchaTypeMapper.updateById((CaptchaType.transToCaptchaType(captchaDetailVo)));
    }

    public boolean delCaptchaType(Long typeId) {
        return typeId == captchaTypeMapper.deleteById(typeId);
    }
}
