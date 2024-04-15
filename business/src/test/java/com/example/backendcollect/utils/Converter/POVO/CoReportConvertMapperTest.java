package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.po.CoReport;
import com.example.backendcollect.vo.report.CoReportVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class CoReportConvertMapperTest {

    @Test
    void test() {
        CoReport coReport = CoReport.builder()
                .id(1)
                .workerId(1)
                .beCollaboratedReportId(1)
                .taskId(1)
                .createTime(new Date())
                .deviceOs(OSKind.Linux.getCode())
                .deviceType("Manjaro")
                .description("xxxxxxx")
                .recoveryStep("1.2.3.")
                .screenshot("123.jpg")
                .build();

        CoReportVO coReportVO = new CoReportVO(coReport);
        CoReport newCoReport = new CoReport(coReportVO);

        Assertions.assertEquals(newCoReport.getId(), coReportVO.getId());
        Assertions.assertEquals(newCoReport.getCreateTime(), coReportVO.getCreateTime());

    }
}