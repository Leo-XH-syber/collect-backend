package com.example.backendcollect.po;

import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.ReportConvertMapper;
import com.example.backendcollect.vo.report.ReportVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Report {
    private Integer id;

    private Integer workerId;

    private Integer taskId;

    private Date createTime;

    private Integer deviceOs;

    private String deviceType;

    private Integer bugReport;

    private Integer bugId;

    private String description;

    private String recoveryStep;

    private String screenshot;

    private Double avg_score;
    private Integer difficulty;

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }


    public Report() {
    }

    public Report(ReportVO reportVO) {
        this(ReportConvertMapper.INSTANCE.vo2po(reportVO));
    }

    public Report(Report report) {
        BeanCopierWithCacheUtil.copy(report, this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeviceOs() {
        return deviceOs;
    }

    public void setDeviceOs(Integer deviceOs) {
        this.deviceOs = deviceOs;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public Integer getBugReport() {
        return bugReport;
    }

    public void setBugReport(Integer bugReport) {
        this.bugReport = bugReport;
    }

    public Integer getBugId() {
        return bugId;
    }

    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getRecoveryStep() {
        return recoveryStep;
    }

    public void setRecoveryStep(String recoveryStep) {
        this.recoveryStep = recoveryStep == null ? null : recoveryStep.trim();
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot == null ? null : screenshot.trim();
    }

    public Double getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(Double avg_score) {
        this.avg_score = avg_score;
    }
}
