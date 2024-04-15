package com.example.backendcollect.mapperservice.mongo;

import com.example.backendcollect.po.mongo.ReportSimilarity;
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
 * @date 2022-03-31 13:44:34
 * @description 得到一个 report 的相似度列表
 */
@Component
public class ReportSimilarityMapper {

    private MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public List<ReportSimilarity.Reports> selectAll() {
        return mongoTemplate.findAll(ReportSimilarity.Reports.class);
    }

    public List<ReportSimilarity> selectByReportIds(List<Integer> reportIds) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reportId").in(reportIds));
        List<ReportSimilarity> reportSimilarity1 = mongoTemplate.find(query, ReportSimilarity.class, "reportSimilarity");
        if (!reportSimilarity1.isEmpty()) {
            return reportSimilarity1;
        }
        return new ArrayList<>();
    }
    public List<ReportSimilarity.Reports> selectByReportId(Integer reportId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reportId").is(reportId));
        ReportSimilarity reportSimilarity1 = mongoTemplate.findOne(query, ReportSimilarity.class, "reportSimilarity");
        if (reportSimilarity1 != null && reportSimilarity1.getReports().size() != 0) {
            return reportSimilarity1.getReports();
        }
        return new ArrayList<>();
    }

    public Double selectBy2Report(Integer report1, Integer report2) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reportId").is(report1));
        ReportSimilarity reportSimilarity1 = mongoTemplate.findOne(query, ReportSimilarity.class, "reportSimilarity");
        if (reportSimilarity1 != null && reportSimilarity1.getReports().size() != 0) {
            List<ReportSimilarity.Reports> reports = reportSimilarity1.getReports();
            for (ReportSimilarity.Reports report : reports) {
                if (report.getReportId().equals(report2)) return report.getTextSimilarity();
            }
        }
        return 0.0;
    }


}
