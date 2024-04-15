package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.po.BugTable;
import com.example.backendcollect.vo.bug.BugTableVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BugTableConverterMapper {
    BugTableConverterMapper INSTANCE = Mappers.getMapper(BugTableConverterMapper.class);

    @Mappings({
    })
    BugTableVO po2vo(BugTable bugTable);

    @Mappings({
    })
    BugTable vo2po(BugTableVO bugTableVO);
}
