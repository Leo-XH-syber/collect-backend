package com.example.backendcollect.vo.bug;

import com.example.backendcollect.po.BugTable;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.BugTableConverterMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class BugTableVO {
    private Integer id;

    private Integer taskId;

    private String bugName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName == null ? null : bugName.trim();
    }

    public BugTableVO() {
    }

    public BugTableVO(BugTableVO bugTableVO) {
        BeanCopierWithCacheUtil.copy(bugTableVO, this);
    }

    public BugTableVO(@NonNull BugTable bugTable) {
        this(BugTableConverterMapper.INSTANCE.po2vo(bugTable));
    }
}
