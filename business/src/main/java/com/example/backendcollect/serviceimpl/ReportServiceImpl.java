package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.auth.annotation.Authentication;
import com.example.backendcollect.enums.errorcode.Impl.CriticismStatusCode;
import com.example.backendcollect.enums.errorcode.Impl.DefaultStatusCode;
import com.example.backendcollect.enums.errorcode.Impl.ReportStatusCode;
import com.example.backendcollect.enums.errorcode.Impl.TaskStatusCode;
import com.example.backendcollect.enums.types.OrderState;
import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.mapperservice.CriticismMapper;
import com.example.backendcollect.mapperservice.OrderMapper;
import com.example.backendcollect.mapperservice.ReportMapper;
import com.example.backendcollect.mapperservice.TaskMapper;
import com.example.backendcollect.mapperservice.mongo.ReportSimilarityMapper;
import com.example.backendcollect.po.Criticism;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.po.mongo.ReportSimilarity;
import com.example.backendcollect.service.ReportService;
import com.example.backendcollect.utils.Constant;
import com.example.backendcollect.utils.PageInfoUtil;
import com.example.backendcollect.utils.ResultHelper;
import com.example.backendcollect.vo.report.CriticismVO;
import com.example.backendcollect.vo.report.ReportClusterVO;
import com.example.backendcollect.vo.report.ReportVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    ReportMapper reportMapper;

    @Resource
    TaskMapper taskMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    CriticismMapper criticismMapper;

    @Resource
    ReportSimilarityMapper reportSimilarityMapper;

    private ResultHelper resultHelper;

    @Override
    public List<ReportVO> getAllReportOfTask(int taskId) {
        List<ReportVO> ret = new ArrayList<>();
        List<Report> reportList = reportMapper.selectByTask(taskId);

        for (Report report : reportList) {
            ReportVO reportVO = new ReportVO(report);
            ret.add(reportVO);
        }

        return ret;
    }

    @Override
    public ReportVO getReportByWorkerAndTask(int workerId, int taskId) {
        return new ReportVO(reportMapper.selectByUserAndTask(workerId, taskId));
    }

    @Override
    public ReportVO getAReport(Integer reportId) {
        Report report = reportMapper.selectByPrimaryKey(reportId);
        if (report == null) {
            throw new ServiceException(ReportStatusCode.REPORT_NOT_EXIST);
        }
        return new ReportVO(report);
    }

    @Override
    public ReportVO submitReport(ReportVO report) {
        Integer taskId = report.getTaskId();
        Report reportPO = new Report(report);
        Integer workerId = reportPO.getWorkerId();
        // 更新 因为创建了索引所以直接通过 workerId 和 taskId 更新, 而不是先查到 id 再更新
        /* 为什么这样做呢? 因为 order 的状态有进行, 完成, 逾期, 0->1, 0->2 都可以, 1->2 就没必要了.
        xml 的 update 代码中判断了 order 的状态为零才做更新(此处是完成, 逾期在 mq) 否则抛错
        不允许对已经完成或者过期的订单进行更新
         */
        if (orderMapper.updateOrderState(taskId, workerId, OrderState.FINISHED.getCode()) == 0) {
            // TODO 应该更加精细地报错: 没有这个订单, 没有这个人, 订单已经完成, 订单已经过期
            throw new ServiceException(TaskStatusCode.ORDER_STATUS_CANNOT_CHANGE);
        }

        reportMapper.insert(reportPO);
        return new ReportVO(reportPO);
    }

    @Authentication
    @Override
    public List<ReportVO> getAllReportByWorker(int workerId) {
        List<ReportVO> ret = new ArrayList<>();
        List<Report> reportList = reportMapper.selectByWorker(workerId);

        for (Report report : reportList) {
            ret.add(new ReportVO(report));
        }

        return ret;
    }

    @Override
    public CriticismVO criticizeReport(CriticismVO criticismVO) {
        Criticism criticism = new Criticism(criticismVO);
        if (criticismMapper.insert(criticism) > 0) {
            return criticismVO;
        }
        throw new ServiceException(DefaultStatusCode.REQUEST_FAIL);
    }

    @Override
    public CriticismVO getUserCriticismOfReport(int uid, int reportId) {
        Criticism criticism = criticismMapper.selectByUserAndReport(uid, reportId);
        if (criticism != null) {
            return new CriticismVO(criticism);
        }
        throw new ServiceException(CriticismStatusCode.CRITICISM_NOT_EXISTS);
    }

    @Override
    public List<CriticismVO> getAllCriticismsOfAReport(int reportId) {
        List<Criticism> allCriticisms = criticismMapper.selectByReport(reportId);
        List<CriticismVO> allCriticismVOs = new ArrayList<>();
        for (Criticism criticism : allCriticisms) {
            allCriticismVOs.add(new CriticismVO(criticism));
        }
        return allCriticismVOs;
    }

    @Override
    public List<Integer> getAllScoresOfAReport(int reportId) {
        List<Criticism> allCriticisms = criticismMapper.selectByReport(reportId);
        List<Integer> scores = new ArrayList<>();
        for (Criticism criticism : allCriticisms) {
            scores.add(criticism.getScore());
        }
        return scores;
    }

    @Override
    public PageInfo<ReportVO> getReportsOfTaskByScore(Integer taskId, boolean reverse, Integer page) {
        if (page == null || page < 1) page = 1;
        PageHelper.startPage(page, Constant.PAGE_SIZE);
        //TODO
        List<Report> reports;
        if (reverse) {
            reports = reportMapper.selectByTaskOrderByScoreDesc(taskId);
        } else {
            reports = reportMapper.selectByTaskOrderByScoreAsc(taskId);
        }
        PageInfo<Report> pos = new PageInfo<>(reports);
        return PageInfoUtil.convert(pos, ReportVO.class);
    }

    @Override
    public List<ReportSimilarity.Reports> getSimilarReports(Integer reportId, Integer num) {
        List<ReportSimilarity.Reports> ret = new ArrayList<>();

        List<ReportSimilarity.Reports> list = reportSimilarityMapper.selectByReportId(reportId);
        list.sort((o1, o2) -> o2.getTextSimilarity().compareTo(o1.getTextSimilarity()));

        for (int i = 0; i < num && i < list.size(); i++) {
            ret.add(list.get(i));
        }

        return ret;
    }

    @Override
    public Double getSimilarityOfTwo(Integer report1, Integer report2) {

        return reportSimilarityMapper.selectBy2Report(report1, report2);
    }

    // 可调试度为0, 不过也不会出什么问题
    @Override
    public List<ReportClusterVO> getReportClusters(Integer taskId) {
        List<Report> reports = reportMapper.selectByTask(taskId);
        List<ReportSimilarity> reports1 = reportSimilarityMapper.selectByReportIds(reports.stream().map(Report::getId).collect(Collectors.toList()));
        List<ReportClusterVO> reportClusterVOS = new ArrayList<>();
        // 根据类别分组
        Map<Integer, List<ReportSimilarity>> collect1 = reports1.stream().collect(Collectors.groupingBy(ReportSimilarity::getCategory));
        collect1.forEach((integer, reportSimilarities) ->
                {
                    List<List<ReportSimilarity.WordTF>> collect = reports1.stream().map(ReportSimilarity::getWords).collect(Collectors.toList());
                    List<ReportSimilarity.WordTF> collect2 = collect.stream().flatMap(Collection::stream).collect(Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ReportSimilarity.WordTF::getWord))), ArrayList::new)).stream().sorted(Comparator.comparing(ReportSimilarity.WordTF::getRating).reversed()).collect(Collectors.toList());
                    List<Integer> collect3 = reportSimilarities.stream().map(ReportSimilarity::getReportId).collect(Collectors.toList());
                    reportClusterVOS.add(

                            new ReportClusterVO(
                                    integer,
                                    reports.stream().filter(report -> collect3.contains(report.getId())).map(ReportVO::new).collect(Collectors.toList()),
                                    collect2.stream().map(wordTF -> new ReportClusterVO.WordCount(wordTF.getWord(), wordTF.getRating())).collect(Collectors.toList())
                            )
                    );
                }
        );

        return reportClusterVOS;
    }


}
