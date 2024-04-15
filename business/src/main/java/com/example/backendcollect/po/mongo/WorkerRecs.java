package com.example.backendcollect.po.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Rikka
 * @date 2022-03-27 02:58:22
 * @description
 */
@Data
@AllArgsConstructor
public class WorkerRecs {
    @Data
    @AllArgsConstructor
    public static class Rec {
        Integer taskId;
        Double rating;
    }

    Integer workerId;
    List<Rec> tasks;
}
