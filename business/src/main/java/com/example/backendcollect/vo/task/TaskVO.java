package com.example.backendcollect.vo.task;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskDifficulty;
import com.example.backendcollect.enums.types.TaskState;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.po.Task;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.TaskConvertMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class TaskVO {

    private Integer id;

    //    @Uid
    private Integer employerId;

    private String taskName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    // 与 PO 的 taskKind 对应, 勿改, 已做映射
    private TaskType taskType;
    // 与 PO 的 needWorkers 对应, 勿改, 已做映射
    private Integer workerNum;

    private Integer hasWorkers = 0;

    private TaskState taskState = TaskState.IN_PROCESS;

    private String testApp;

    private String testDoc;
    private OSKind OS;
    private TaskDifficulty difficulty;

    private String introduction;

    public TaskVO(TaskVO taskVO) {
        BeanCopierWithCacheUtil.copy(taskVO, this);
    }

    public TaskVO(@NonNull Task task) {
        this(TaskConvertMapper.INSTANCE.po2vo(task));
    }

}
