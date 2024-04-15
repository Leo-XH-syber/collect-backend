package com.example.backendcollect.vo.report;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.ReportBugType;
import com.example.backendcollect.enums.types.TaskDifficulty;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.ReportConvertMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class ReportVO {

    private Integer id;

    //    @Uid(message = "UID_NOT_ALLOWED") // 确保前端传来的是现在登陆的人
    private Integer workerId;

    private Integer taskId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private OSKind deviceOs;

    private String deviceType;

    private String description;

    private String recoveryStep;

    private String screenshot;
    private Double avg_score;

    private ReportBugType bugReport;

    private Integer bugId;

    private TaskDifficulty difficulty;

    public ReportVO(ReportVO reportVO) {
        BeanCopierWithCacheUtil.copy(reportVO, this);
    }

    public ReportVO(Report report) {
        this(ReportConvertMapper.INSTANCE.po2Vo(report));
    }
}
