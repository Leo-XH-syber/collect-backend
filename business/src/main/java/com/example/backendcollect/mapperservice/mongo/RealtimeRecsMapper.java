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
 * @date 2022-03-27 15:52:15
 * @description 实时推荐的数据
 */
@Component
public class RealtimeRecsMapper {
    private MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<WorkerRecs.Rec> selectByWorkerId(Integer workerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("workerId").is(workerId));
        List<WorkerRecs> recs = mongoTemplate.find(query, WorkerRecs.class, "StreamRecs");
        if (recs.size() > 0 && recs.get(0).getTasks().size() > 0)
            return recs.get(0).getTasks();
        return new ArrayList<>();
    }

}
