package com.example.backendcollect.mapperservice.mongo;

import com.example.backendcollect.po.mongo.PopularTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Rikka
 * @date 2022-03-27 03:25:11
 * @description 其实直接从 mysql 拿也行的, 缺点是每次都要计算. 优点是实时性强.
 * 单独使用一个表来汇总是冗余的, 有数据一致性问题, 实时性差, 但是空间换时间
 */
@Component
public class PopularTasksMapper {
    private MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public List<PopularTask> getHistoryPopularTasks(Integer num) {
        Query query = new Query().limit(num);
        return mongoTemplate.find(query, PopularTask.class, "historyPopularTask");
    }

    public List<PopularTask> getHistoryPopularTasks() {
        return mongoTemplate.findAll(PopularTask.class, "historyPopularTask");
    }

    public List<PopularTask> getRecentPopularTasks(Integer num) {
        Query query = new Query().limit(num);
        return mongoTemplate.find(query, PopularTask.class, "recentPopularTask");
    }

    public List<PopularTask> getRecentPopularTasks() {
        return mongoTemplate.findAll(PopularTask.class, "recentPopularTask");
    }
}
