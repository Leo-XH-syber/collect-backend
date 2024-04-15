package com.example.backendcollect.po.mongo;


import lombok.Data;

import java.io.Serializable;

/**
 * @author Rikka
 * @date 2022-03-27 02:48:11
 * @description 热门任务 id 所以最后还是要去 mysql 拿东西..
 */
@Data
public class PopularTask implements Serializable {
    private Integer taskId;
    private Integer workerCount;
}
