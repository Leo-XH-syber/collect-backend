package com.example.backendcollect.po;

import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.CoReportConvertMapper;
import com.example.backendcollect.vo.report.CoReportVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class CoReport {
    private Integer id;

    private Integer workerId;

    private Integer beCollaboratedReportId;

    private Integer taskId;

    private Date createTime;

    private Integer deviceOs;

    private Integer scoreFromAuthor;

    private String deviceType;

    private String description;

    private String recoveryStep;

    private String screenshot;

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    private Integer difficulty;

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

    public Integer getBeCollaboratedReportId() {
        return beCollaboratedReportId;
    }

    public void setBeCollaboratedReportId(Integer beCollaboratedReportId) {
        this.beCollaboratedReportId = beCollaboratedReportId;
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

    public Integer getScoreFromAuthor() {
        return scoreFromAuthor;
    }

    public void setScoreFromAuthor(Integer scoreFromAuthor) {
        this.scoreFromAuthor = scoreFromAuthor;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
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

    public CoReport(CoReport coReport) {
        BeanCopierWithCacheUtil.copy(coReport, this);
    }

    public CoReport() {
    }

    public CoReport(CoReportVO coReportVO) {
        this(CoReportConvertMapper.INSTANCE.vo2po(coReportVO));
    }
}
