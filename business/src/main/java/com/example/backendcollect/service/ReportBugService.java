package com.example.backendcollect.service;

import com.example.backendcollect.vo.bug.BugTableVO;

import java.util.List;

public interface ReportBugService {
    BugTableVO submitNewBug(Integer taskId, Integer reportId, String bugName);

    BugTableVO selectOldBug(Integer taskId, Integer reportId, String bugName);

    void setNotBug(Integer reportId);

    List<BugTableVO> getBugList(Integer taskId);

    BugTableVO getBugNameById(Integer bugId);
}
