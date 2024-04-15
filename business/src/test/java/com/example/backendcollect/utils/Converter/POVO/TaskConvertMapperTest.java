package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.po.Task;
import com.example.backendcollect.vo.task.TaskVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author: Rikka
 * @date: 2022/2/27 下午6:07
 * @description
 */
class TaskConvertMapperTest {
    @Test
    void convert() {
        Task task = Task.builder()
                .employerId(1)
                .taskKind(1)
                .taskName("12")
                .taskState(1)
                .endTime(new Date())
                .needWorkers(12)
                .hasWorkers(1)
                .testApp("213")
                .difficulty(2)
                .OS(0)
                .build();

        Task newTask = new Task(new TaskVO(task));
        Assertions.assertEquals(newTask.getEmployerId(), task.getEmployerId());
        Assertions.assertEquals(newTask.getTaskKind(), task.getTaskKind());
        Assertions.assertEquals(newTask.getHasWorkers(), task.getHasWorkers());
        Assertions.assertEquals(newTask.getNeedWorkers(), task.getNeedWorkers());
    }
}