package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.po.Criticism;
import com.example.backendcollect.vo.report.CriticismVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CriticismConvertMapper {
    CriticismConvertMapper INSTANCE = Mappers.getMapper(CriticismConvertMapper.class);

    @Mappings({
    })
    CriticismVO po2Vo(Criticism criticism);

    @Mappings({
    })
    Criticism vo2po(CriticismVO criticismVO);
}
