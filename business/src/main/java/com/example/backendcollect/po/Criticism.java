package com.example.backendcollect.po;

import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.CriticismConvertMapper;
import com.example.backendcollect.vo.report.CriticismVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Criticism implements Serializable {
    private Integer id;

    private Integer reportId;

    private Integer workerId;

    private Date createTime;

    private Integer score;

    private String comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments == null ? null : comments.trim();
    }

    public Criticism(Criticism criticism) {
        BeanCopierWithCacheUtil.copy(criticism, this);
    }

    public Criticism() {
    }

    public Criticism(CriticismVO criticismVO) {
        this(CriticismConvertMapper.INSTANCE.vo2po(criticismVO));

    }
}