package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.vo.report.ReportVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;


/**
 * @author: Rikka
 * @date: 2022/2/27 下午5:54
 * @description
 */

class ReportConvertMapperTest {
    @Test
    public void testConverter() {
        Report report = Report.builder()
                .createTime(new Date())
                .description("baka")
                .id(1)
                .recoveryStep("1.2.3.")
                .screenshot("123.jpg")
                .taskId(1)
                .workerId(1)
                .bugReport(1)
                .bugId(1)
                .deviceType("Manjaro")
                .avg_score(1.1)
                .deviceOs(OSKind.Linux.getCode())
                .build();


        ReportVO reportVO = new ReportVO(report);
        Report newReport = new Report(reportVO);

        Assertions.assertEquals(newReport.getId(), report.getId());
        Assertions.assertEquals(newReport.getCreateTime(), report.getCreateTime());

    }

}
