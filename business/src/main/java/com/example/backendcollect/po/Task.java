package com.example.backendcollect.po;

import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.TaskConvertMapper;
import com.example.backendcollect.vo.task.TaskVO;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;
import java.util.Date;

@Builder
@AllArgsConstructor
public class Task implements Serializable {
    private Integer id;

    private Integer employerId;

    private String taskName;

    private Date startTime;

    private Date endTime;

    private Integer taskKind;

    private Integer needWorkers;

    private Integer hasWorkers;

    private Integer taskState;

    private String testApp;

    private String testDoc;

    private String introduction;

    private Integer OS;
    private Integer difficulty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Integer employerId) {
        this.employerId = employerId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTaskKind() {
        return taskKind;
    }

    public void setTaskKind(Integer taskKind) {
        this.taskKind = taskKind;
    }

    public Integer getNeedWorkers() {
        return needWorkers;
    }

    public void setNeedWorkers(Integer needWorkers) {
        this.needWorkers = needWorkers;
    }

    public Integer getHasWorkers() {
        return hasWorkers;
    }

    public void setHasWorkers(Integer hasWorkers) {
        this.hasWorkers = hasWorkers;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public String getTestApp() {
        return testApp;
    }

    public void setTestApp(String testApp) {
        this.testApp = testApp == null ? null : testApp.trim();
    }

    public String getTestDoc() {
        return testDoc;
    }

    public void setTestDoc(String testDoc) {
        this.testDoc = testDoc == null ? null : testDoc.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Integer getOS() {
        return OS;
    }

    public void setOS(Integer OS) {
        this.OS = OS;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Task(Task task) {
        BeanCopierWithCacheUtil.copy(task, this);
    }

    public Task() {
    }

    public Task(TaskVO taskVO) {
        this(TaskConvertMapper.INSTANCE.vo2po(taskVO));
    }
}