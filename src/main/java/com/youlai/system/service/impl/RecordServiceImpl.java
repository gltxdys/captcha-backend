package com.youlai.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youlai.system.common.base.BasePageQuery;
import com.youlai.system.mapper.RecordMapper;
import com.youlai.system.mapper.UserScoreMapper;
import com.youlai.system.model.entity.UserScore;
import com.youlai.system.model.query.TradeQuery;
import com.youlai.system.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youlai.system.model.entity.Record;

/**
 * 识别记录查询服务
 *
 * @author haoxr
 * @since 2022/1/14
 */
@Service
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    RecordMapper recordMapper;

    @Autowired
    UserScoreMapper userScoreMapper;

    public IPage<Record> getListByUserId(TradeQuery query) {
        Page<Record> page =new Page<>(query.getPageNum(),query.getPageSize());
        return recordMapper.selectPage(page, new QueryWrapper<Record>().eq("user_id", query.getUserId()));
    }

    public IPage<Record> getAllList(BasePageQuery query) {
        Page<Record> page =new Page<>(query.getPageSize(),query.getPageNum());
        return this.getBaseMapper().selectPage(page,new QueryWrapper<>());
    }

    @SneakyThrows
    public boolean save(Record record){
        UserScore userScore = userScoreMapper.selectOne(new QueryWrapper<UserScore>().eq("user_id",record.getUserId()));
        if(record.getScore()>userScore.getScore()){
            throw new Exception("积分不足");
        }
        recordMapper.insert(record);
        userScoreMapper.update(new UserScore(),new UpdateWrapper<UserScore>().set("score",userScore.getScore()-record.getScore()).eq("user_id",record.getUserId()));
        return true;
    }
}
