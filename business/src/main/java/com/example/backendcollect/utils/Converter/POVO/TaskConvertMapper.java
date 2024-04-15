package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskDifficulty;
import com.example.backendcollect.enums.types.TaskState;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.po.Task;
import com.example.backendcollect.vo.task.TaskVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, imports = {TaskType.class, TaskState.class, OSKind.class, TaskDifficulty.class})
public interface TaskConvertMapper {

    TaskConvertMapper INSTANCE = Mappers.getMapper(TaskConvertMapper.class);

    @Mappings({
            @Mapping(source = "needWorkers", target = "workerNum"),
            @Mapping(expression = "java(TaskType.values()[taskPO.getTaskKind()])", target = "taskType"),
            @Mapping(expression = "java(TaskState.values()[taskPO.getTaskState()])", target = "taskState"),
            @Mapping(expression = "java(OSKind.values()[taskPO.getOS()])", target = "OS"),
            @Mapping(expression = "java(TaskDifficulty.values()[taskPO.getDifficulty()])", target = "difficulty"),
    })
    TaskVO po2vo(Task taskPO);


    @Mappings({
            @Mapping(target = "taskState", expression = "java(taskVO.getTaskState().ordinal())"),
            @Mapping(target = "taskKind", expression = "java(taskVO.getTaskType().ordinal())"),
            @Mapping(target = "OS", expression = "java(taskVO.getOS().ordinal())"),
            @Mapping(target = "difficulty", expression = "java(taskVO.getDifficulty().ordinal())"),
            @Mapping(target = "needWorkers", source = "workerNum"),
    })
    Task vo2po(TaskVO taskVO);

}
