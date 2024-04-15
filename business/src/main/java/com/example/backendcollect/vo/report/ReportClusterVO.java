package com.example.backendcollect.vo.report;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportClusterVO {

    private Integer clusterIndex;
    private List<ReportVO> reportList;
    private List<WordCount> wordInfo;


    @Data
    @AllArgsConstructor
    public static class WordCount {
        public String name;
        public Double value;
    }
}
