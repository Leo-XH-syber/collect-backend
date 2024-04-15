package com.example.backendcollect.vo.report;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskDifficulty;
import com.example.backendcollect.po.CoReport;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.CoReportConvertMapper;
import com.example.backendcollect.utils.validation.Uid;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class CoReportVO {
    private Integer id;

    @Uid(message = "UID_NOT_ALLOWED") // 确保前端传来的是现在登陆的人
    private Integer workerId;

    private Integer beCollaboratedReportId;

    private Integer taskId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private OSKind deviceOs;

    private String deviceType;

    private String description;

    private String recoveryStep;

    private String screenshot;
    private Integer scoreFromAuthor;
    private TaskDifficulty difficulty;

    public CoReportVO(CoReportVO coReportVO) {
        BeanCopierWithCacheUtil.copy(coReportVO, this);
    }

    public CoReportVO(@NonNull CoReport coReport) {
        this(CoReportConvertMapper.INSTANCE.po2vo(coReport));
    }

}
