package com.example.backendcollect.po;

import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.WorkerPreferConvertMapper;
import com.example.backendcollect.vo.user.WorkerPropertyVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class WorkerProperty {
    private Integer workerId;

    private Double ability = 0.0;

    private Integer activity = 0;

    private Integer defaultOs;

    private String preference;

    private Double cooperationAbility = 0.0;

    private Double criticismAbility = 0.0;

    private Double reportAvgCriticism = 0.0;

    private Double avgRepeatability = 0.0;

    private Integer taskCount = 0;

    private Integer historyBugCount = 0;

    private Double bugReportProportion = 0.0;

    private Double duplicateIndex = 0.0;

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Double getAbility() {
        return ability;
    }

    public void setAbility(Double ability) {
        this.ability = ability;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getDefaultOs() {
        return defaultOs;
    }

    public void setDefaultOs(Integer defaultOs) {
        this.defaultOs = defaultOs;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference == null ? null : preference.trim();
    }

    public Double getCooperationAbility() {
        return cooperationAbility;
    }

    public void setCooperationAbility(Double cooperationAbility) {
        this.cooperationAbility = cooperationAbility;
    }

    public Double getCriticismAbility() {
        return criticismAbility;
    }

    public void setCriticismAbility(Double criticismAbility) {
        this.criticismAbility = criticismAbility;
    }

    public Double getReportAvgCriticism() {
        return reportAvgCriticism;
    }

    public void setReportAvgCriticism(Double reportAvgCriticism) {
        this.reportAvgCriticism = reportAvgCriticism;
    }

    public Double getAvgRepeatability() {
        return avgRepeatability;
    }

    public void setAvgRepeatability(Double avgRepeatability) {
        this.avgRepeatability = avgRepeatability;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public Integer getHistoryBugCount() {
        return historyBugCount;
    }

    public void setHistoryBugCount(Integer historyBugCount) {
        this.historyBugCount = historyBugCount;
    }

    public Double getBugReportProportion() {
        return bugReportProportion;
    }

    public void setBugReportProportion(Double bugReportProportion) {
        this.bugReportProportion = bugReportProportion;
    }

    public Double getDuplicateIndex() {
        return duplicateIndex;
    }

    public void setDuplicateIndex(Double duplicateIndex) {
        this.duplicateIndex = duplicateIndex;
    }

    public WorkerProperty(WorkerProperty workerProperty) {
        BeanCopierWithCacheUtil.copy(workerProperty, this);
    }

    public WorkerProperty() {
    }

    public WorkerProperty(WorkerPropertyVO workerPropertyVO) {
        this(WorkerPreferConvertMapper.INSTANCE.vo2po(workerPropertyVO));
    }
}
