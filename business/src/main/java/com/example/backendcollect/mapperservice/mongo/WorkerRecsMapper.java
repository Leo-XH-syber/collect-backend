package com.example.backendcollect.mapperservice.mongo;

import com.example.backendcollect.po.mongo.WorkerRecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rikka
 * @date 2022-03-27 03:02:07
 * @description 离线推荐: 得到 ALS 计算的结果. 该表位于 mongodb, 定时计算
 * 数据流动: mysql.order---dataloader--->mongodb.order---offlineRecommender ALS--->mongodb.workerRecs
 * 计算量较大, 所以要拿到推荐结果不是现算, 而是从算好的表拿. 这个不追求实时性
 */
@Component
public class WorkerRecsMapper {
    @Autowired
    public WorkerRecsMapper(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private MongoTemplate mongoTemplate;

    public List<WorkerRecs.Rec> selectByWorkerId(Integer workerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("workerId").is(workerId));
        List<WorkerRecs> recs = mongoTemplate.find(query, WorkerRecs.class);
        if (recs.size() > 0 && recs.get(0).getTasks().size() > 0)
            return recs.get(0).getTasks();
        return new ArrayList<>();
    }

    public List<WorkerRecs> selectAll() {
        return mongoTemplate.findAll(WorkerRecs.class);
    }

}
