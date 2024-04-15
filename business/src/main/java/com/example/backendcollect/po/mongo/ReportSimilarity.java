package com.example.backendcollect.po.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

/**
 * @author Rikka
 * @date 2022-03-31 13:47:05
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSimilarity {
    @Field("reportId")
    private Integer reportId;
    @Field("reports")
    List<Reports> reports;
    @Field("category")
    private Integer category;
    @Field("words")
    private List<WordTF> words;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WordTF {
        @Field("word")
        private String word;
        @Field("rating")
        private Double rating;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reports {
        @Field("reportId")
        private Integer reportId;
        @Field("textSimilarity")
        private Double textSimilarity;
        @Field("imgSimilarity")
        private Double imgSimilarity;
    }
}
