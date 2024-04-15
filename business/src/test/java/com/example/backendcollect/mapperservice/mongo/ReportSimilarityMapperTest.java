package com.example.backendcollect.mapperservice.mongo;

import com.example.backendcollect.po.mongo.ReportSimilarity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Rikka
 * @date 2022-03-31 14:04:31
 * @description
 */
@SpringBootTest
class ReportSimilarityMapperTest {

    @Resource
    private ReportSimilarityMapper reportSimilarityMapper;
    @Test
    void selectByReportId() {
        List<ReportSimilarity.Reports> reports = reportSimilarityMapper.selectByReportId(1);
        reports.forEach(System.out::println);

    }

    @Test
    void setMongoTemplate() {
    }

    @Test
    void selectAll() {
    }

    @Test
    void selectByReportIds() {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(3);
        List<ReportSimilarity> reportSimilarities = reportSimilarityMapper.selectByReportIds(integers);
    }

    @Test
    void selectBy2Report() {
    }
}