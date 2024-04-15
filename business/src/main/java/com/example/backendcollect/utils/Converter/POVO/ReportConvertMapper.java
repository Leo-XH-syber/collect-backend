package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.ReportBugType;
import com.example.backendcollect.enums.types.TaskDifficulty;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.vo.report.ReportVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {OSKind.class, ReportBugType.class, TaskDifficulty.class})
public interface ReportConvertMapper {

    ReportConvertMapper INSTANCE = Mappers.getMapper(ReportConvertMapper.class);

    @Mappings({
            @Mapping(target = "deviceOs", expression = "java(OSKind.values()[report.getDeviceOs()])"),
            @Mapping(target = "bugReport", expression = "java(report.getBugReport()==null?null:ReportBugType.values()[report.getBugReport()])"),
            @Mapping(expression = "java(report.getDifficulty()==null?null: TaskDifficulty.values()[report.getDifficulty()])", target = "difficulty"),

    })
    ReportVO po2Vo(Report report);

    @Mappings({
            @Mapping(target = "deviceOs", expression = "java(reportVO.getDeviceOs().ordinal())"),
            @Mapping(target = "bugReport", expression = "java(reportVO.getBugReport()==null?0:reportVO.getBugReport().ordinal())"),
            @Mapping(target = "difficulty", expression = "java(reportVO.getDifficulty()==null?null:reportVO.getDifficulty().ordinal())"),


    })
    Report vo2po(ReportVO reportVO);
}
