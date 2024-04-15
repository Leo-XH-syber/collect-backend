package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskDifficulty;
import com.example.backendcollect.po.CoReport;
import com.example.backendcollect.vo.report.CoReportVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = {OSKind.class, TaskDifficulty.class})
public interface CoReportConvertMapper {
    CoReportConvertMapper INSTANCE = Mappers.getMapper(CoReportConvertMapper.class);

    @Mappings({
            @Mapping(expression = "java(OSKind.values()[coReportPO.getDeviceOs()])", target = "deviceOs"),
            @Mapping(expression = "java(coReportPO.getDifficulty()==null?null:TaskDifficulty.values()[coReportPO.getDifficulty()])", target = "difficulty"),

    })
    CoReportVO po2vo(CoReport coReportPO);


    @Mappings({
            @Mapping(target = "deviceOs", expression = "java(coReportVO.getDeviceOs().ordinal())"),
            @Mapping(target = "difficulty", expression = "java(coReportVO.getDifficulty()==null?null:coReportVO.getDifficulty().ordinal())"),
            @Mapping(target = "scoreFromAuthor", expression = "java(coReportVO.getScoreFromAuthor()==null?0:coReportVO.getScoreFromAuthor())"),

    })
    CoReport vo2po(CoReportVO coReportVO);
}
