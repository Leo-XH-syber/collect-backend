package com.example.backendcollect.serviceimpl.utils;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskDifficulty;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.mapperservice.*;
import com.example.backendcollect.mapperservice.mongo.ReportSimilarityMapper;
import com.example.backendcollect.po.CoReport;
import com.example.backendcollect.po.Criticism;
import com.example.backendcollect.po.Report;
import com.example.backendcollect.po.WorkerProperty;
import com.example.backendcollect.po.mongo.ReportSimilarity;
import com.example.backendcollect.utils.Constant;
import com.example.backendcollect.utils.Converter.IntegerToEnumConverterFactory;
import com.example.backendcollect.utils.StatsWeight;
import com.example.backendcollect.vo.user.WorkerPropertyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;

@Component
public class WorkerStatsCalculator {
    @Resource
    ReportMapper reportMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    CoReportMapper coReportMapper;
    @Resource
    CriticismMapper criticismMapper;
    @Resource
    WorkerPropertyMapper workerPropertyMapper;
    @Resource
    ReportSimilarityMapper reportSimilarityMapper;
    @Autowired
    IntegerToEnumConverterFactory integerToEnumConverterFactory;

    public WorkerPropertyVO getWorkerAllProperty(WorkerProperty workerProperty) {
        Integer workerId = workerProperty.getWorkerId();
        WorkerPropertyVO workerPropertyVO = new WorkerPropertyVO(workerProperty);

//        workerPropertyVO.setAbility(this.getWorkerProfessionalScore(workerId));
        if (orderMapper.selectAllByWorkerId(workerId).size() > Constant.ORDER_NUM_THRESHOLD_WHEN_RECALCULATE_PREFERENCES) {
            workerPropertyVO.setDefaultOs(this.getWorkerPreferOS(workerId));
            List<TaskType> preferences = this.getWorkerTaskPreference(workerId);
            workerPropertyVO.setPreference(preferences.toArray(new TaskType[preferences.size()]));
        }
        workerPropertyVO.setSyntheticalScore(this.calFinalScore(workerProperty));
//        workerPropertyVO.setCooperationAbility(this.getWorkerCooperatingScore(workerId));
//        workerPropertyVO.setCriticismAbility(this.getWorkerExamScore(workerId));
//        workerPropertyVO.setReportAvgCriticism(this.getWorkerBeCriticizedDegree(workerId));
//        workerPropertyVO.setAvgRepeatability(this.getDuplicatedDegreeOfWorker(workerId));
//        workerPropertyVO.setBugReportProportion(this.getBugReportPercentage(workerId));
//        workerPropertyVO.setDuplicateIndex(this.getDuplicateIndexOfWorker(workerId));
//        写回数据库
//        workerProperty=new WorkerProperty(workerPropertyVO);
//        workerPropertyMapper.updateByPrimaryKey(workerProperty);
        return workerPropertyVO;
    }

    public Double getBugReportPercentage(Integer workerId) {
        return reportMapper.getBugReportPercentageByWorkerId(workerId);
    }

    public List<TaskType> getWorkerTaskPreference(Integer workerId) {
        List<TaskType> preferences = orderMapper.getWorkerTaskKindsByWorkerId(workerId)
                .stream()
                .map(a -> integerToEnumConverterFactory.getConverter(TaskType.class).convert(a))
                .collect(Collectors.toList());
        return preferences;
    }

    public OSKind getWorkerPreferOS(Integer workerId) {
        List<OSKind> preferences = orderMapper.getWorkerOSKindsByWorkerId(workerId)
                .stream()
                .map(a -> integerToEnumConverterFactory.getConverter(OSKind.class).convert(a))
                .collect(Collectors.toList());
        return preferences.size() > 0 ? preferences.get(0) : null;
    }

    public Double getWorkerCooperatingScore(Integer workerId) {
        List<CoReport> coReports = coReportMapper.selectByCoWorkerId(workerId);
        Double score = 0.0;
        int weightSum = 0;
        for (CoReport coReport : coReports) {
            TaskDifficulty difficulty = integerToEnumConverterFactory.getConverter(TaskDifficulty.class).convert(coReport.getDifficulty());
            if (difficulty.equals(TaskDifficulty.EASY)) {
                weightSum += StatsWeight.EASY_TASK_SCORE_WEIGHT;
                score += StatsWeight.EASY_TASK_SCORE_WEIGHT * coReport.getScoreFromAuthor();
            } else if (difficulty.equals(TaskDifficulty.MEDIUM)) {
                weightSum += StatsWeight.MEDIUM_TASK_SCORE_WEIGHT;
                score += StatsWeight.MEDIUM_TASK_SCORE_WEIGHT * coReport.getScoreFromAuthor();
            } else {
                weightSum += StatsWeight.HARD_TASK_SCORE_WEIGHT;
                score += StatsWeight.HARD_TASK_SCORE_WEIGHT * coReport.getScoreFromAuthor();
            }
        }
        return score / weightSum;
    }

    public Double getWorkerProfessionalScore(Integer workerId) {
        List<Report> coReports = reportMapper.selectWithScoreAndDifficultyByWorker(workerId);
        Double score = 0.0;
        int weightSum = 0;
        for (Report report : coReports) {
            TaskDifficulty difficulty = integerToEnumConverterFactory.getConverter(TaskDifficulty.class).convert(report.getDifficulty());
            if (difficulty.equals(TaskDifficulty.EASY)) {
                weightSum += StatsWeight.EASY_TASK_SCORE_WEIGHT;
                score += StatsWeight.EASY_TASK_SCORE_WEIGHT * report.getAvg_score();
            } else if (difficulty.equals(TaskDifficulty.MEDIUM)) {
                weightSum += StatsWeight.MEDIUM_TASK_SCORE_WEIGHT;
                score += StatsWeight.MEDIUM_TASK_SCORE_WEIGHT * report.getAvg_score();
            } else {
                weightSum += StatsWeight.HARD_TASK_SCORE_WEIGHT;
                score += StatsWeight.HARD_TASK_SCORE_WEIGHT * report.getAvg_score();
            }
        }
        return score / weightSum;
    }

    public Double getWorkerExamScore(Integer workerId) {
        //用户审查报告的能力：用户对报告的评分-该报告的均分
        List<Criticism> userCriticisms = criticismMapper.selectByWorkerId(workerId);
        Double score = 0.0;
        for (Criticism criticism : userCriticisms) {
            score += Math.abs(criticism.getScore() - criticismMapper.getAvgScoreByReport(criticism.getReportId()));
        }
        return Constant.REPORT_MAX_SCORE-(score / userCriticisms.size());
    }

    public Double getWorkerBeCriticizedDegree(Integer workerId) {
//        return criticismMapper.getAverageCriticismNumOfWorkersReports(workerId) == null ? 0.0 : Double.valueOf(criticismMapper.getAverageCriticismNumOfWorkersReports(workerId));
        List<Report> workersAllReports=reportMapper.selectByWorker(workerId);
        int allCriticismCount=0;
        for(Report report:workersAllReports){
            List<Criticism> criticismsOfTheReport=criticismMapper.selectByReport(report.getId());
            int criticismCount=criticismsOfTheReport==null?0:criticismsOfTheReport.size();
            allCriticismCount+=criticismCount;
        }
        return Double.valueOf(allCriticismCount/workersAllReports.size());
    }



    public Double getDuplicateIndexOfWorker(Integer workerId) {
        List<Report> bugReports = reportMapper.selectBugReportByWorkerId(workerId);
        Double sum = 0.0;
        for (Report bugReport : bugReports) {
            Integer duplicatedBugNum = reportMapper.getRepeatNumByBugId(bugReport.getBugId());
            if(duplicatedBugNum.equals(0)) sum+=2;
            else sum += (1.0 / duplicatedBugNum);
        }
        return sum.equals(NaN) ? 0 : sum;
    }

    public Double getDuplicatedDegreeOfWorker(Integer workerId) {
        List<Report> bugReports = reportMapper.selectBugReportByWorkerId(workerId);
        Double sum = 0.0;
        for (Report bugReport : bugReports) {
            Integer duplicatedBugNum = reportMapper.getRepeatNumByBugId(bugReport.getBugId());
            sum += (1.0 / duplicatedBugNum);
        }
        Double ans = sum / bugReports.size();
        return ans.equals(NaN) ? 0 : sum.equals(POSITIVE_INFINITY)?1:ans;
    }

    public Double getAverageSimilarity(Integer workerId){
        List<Report> reports=reportMapper.selectByWorker(workerId);
        int weightSum=0;
        Double similaritySum=0.0;
        for(Report report:reports){
            List<ReportSimilarity.Reports> reportSimilarities= reportSimilarityMapper.selectByReportId(report.getId());
            for(ReportSimilarity.Reports similarReport: reportSimilarities){
                similaritySum+=similarReport.getTextSimilarity()+similarReport.getImgSimilarity();
                weightSum+=2;
            }
        }
        Double ans=similaritySum/weightSum;
        return ans.isNaN()?0:ans;
    }


    public Double calFinalScore(WorkerProperty worker) {
        // 归一化处理
        double abl = worker.getAbility() / 5.0;
        double co_abl = worker.getCooperationAbility() / 5.0;
        double cr_abl = worker.getCriticismAbility() / 5.0;
        double rac = worker.getReportAvgCriticism();
        double ar = 1 - worker.getAvgRepeatability();
        double brp = worker.getBugReportProportion();

        return (abl * co_abl + co_abl * cr_abl + cr_abl * rac + rac * ar + ar * brp + brp * abl) / 6;
    }
}
