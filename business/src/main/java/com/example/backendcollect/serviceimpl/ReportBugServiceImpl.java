package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.enums.types.ReportBugType;
import com.example.backendcollect.mapperservice.BugTableMapper;
import com.example.backendcollect.mapperservice.ReportMapper;
import com.example.backendcollect.po.BugTable;
import com.example.backendcollect.service.ReportBugService;
import com.example.backendcollect.vo.bug.BugTableVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportBugServiceImpl implements ReportBugService {

    @Resource
    ReportMapper reportMapper;

    @Resource
    BugTableMapper bugTableMapper;

    @Override
    public BugTableVO submitNewBug(Integer taskId, Integer reportId, String bugName) {

        BugTableVO bug1 = getBugTableByName(taskId, reportId, bugName);
        if (bug1 != null) {
            return bug1;
        }

        BugTable newBug = new BugTable();
        newBug.setTaskId(taskId);
        newBug.setBugName(bugName);
        bugTableMapper.insert(newBug);

        BugTableVO bug = getBugTableByName(taskId, reportId, bugName);
        if (bug != null) {
            newBug.setId(bug.getId());
        }
        return new BugTableVO(newBug);
    }

    @Override
    public BugTableVO selectOldBug(Integer taskId, Integer reportId, String bugName) {
        List<BugTable> bugs = bugTableMapper.selectByTaskId(taskId);
        BugTable selectedBug = null;
        for (BugTable bug: bugs) {
            if (bug.getBugName().equals(bugName)){
                selectedBug = bug;
                break;
            }
        }
        if (selectedBug != null) {
            reportMapper.setBugType(reportId, ReportBugType.DUPLICATED_BUG.getCode(), selectedBug.getId());
            return new BugTableVO(selectedBug);
        }
        return null;
    }

    @Override
    public void setNotBug(Integer reportId) {
        reportMapper.setBugType(reportId, ReportBugType.NOT_BUG.getCode(), null);
    }

    @Override
    public List<BugTableVO> getBugList(Integer taskId) {
        List<BugTableVO> ret = new ArrayList<>();
        List<BugTable> list = bugTableMapper.selectByTaskId(taskId);

        for (BugTable bugTable : list) {
            ret.add(new BugTableVO(bugTable));
        }
        return ret;
    }

    @Override
    public BugTableVO getBugNameById(Integer bugId) {
        return new BugTableVO(bugTableMapper.selectByPrimaryKey(bugId));
    }


    private BugTableVO getBugTableByName(Integer taskId, Integer reportId, String bugName) {
        List<BugTable> bugList = bugTableMapper.selectByTaskId(taskId);
        for (BugTable bug : bugList) {
            if (bug.getBugName().equals(bugName)) {
                reportMapper.setBugType(reportId, ReportBugType.BUG_REPORT.getCode(), bug.getId());
                return new BugTableVO(bug);
            }
        }
        return null;
    }
}
