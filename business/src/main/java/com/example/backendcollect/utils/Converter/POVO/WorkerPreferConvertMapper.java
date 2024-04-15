package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.po.WorkerProperty;
import com.example.backendcollect.vo.user.WorkerPropertyVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author: Rikka
 * @date: 2022/2/27 下午12:40
 * @description User2UserVO 无需手动实现
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, imports = {OSKind.class})
public interface WorkerPreferConvertMapper {

    WorkerPreferConvertMapper INSTANCE = Mappers.getMapper(WorkerPreferConvertMapper.class);

    @Mappings({
            @Mapping(target = "defaultOs", expression = "java(workerPrefer.getDefaultOs()==null?null:OSKind.values()[workerPrefer.getDefaultOs()])"),
            @Mapping(target = "syntheticalScore", ignore = true)
    })
    WorkerPropertyVO po2vo(WorkerProperty workerPrefer);

    default TaskType[] map1(String preference) {
        if (preference == null || preference.equals("")) return null;
        String[] preferences = preference.split(";");
        TaskType[] ans = new TaskType[preferences.length];
        for (int i = 0; i < preferences.length; i++) {
            ans[i] = TaskType.values()[Integer.parseInt(preferences[i])];
        }
        return ans;
    }

    @Mappings({
            @Mapping(target = "defaultOs", expression = "java(workerPreferVO.getDefaultOs()==null?null:workerPreferVO.getDefaultOs().ordinal())"),

    })
    WorkerProperty vo2po(WorkerPropertyVO workerPreferVO);

    default String map2(TaskType[] preference) {
        if (preference == null) return null;
        StringBuilder ans = new StringBuilder();
        for (TaskType taskType : preference) {
            ans.append(taskType.ordinal()).append(";");
        }
        if (ans.toString().endsWith(";"))
            ans = new StringBuilder(ans.substring(0, ans.length() - 1));
        return ans.toString();
    }

}
