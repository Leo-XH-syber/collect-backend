package com.example.backendcollect.serviceimpl;

import com.example.backendcollect.enums.errorcode.Impl.DefaultStatusCode;
import com.example.backendcollect.enums.errorcode.Impl.ReportStatusCode;
import com.example.backendcollect.exception.ServiceException;
import com.example.backendcollect.mapperservice.CoReportMapper;
import com.example.backendcollect.po.CoReport;
import com.example.backendcollect.service.CoReportService;
import com.example.backendcollect.vo.report.CoReportVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoReportServiceImpl implements CoReportService {

    @Resource
    CoReportMapper coReportMapper;


    @Override
    public CoReportVO submitReport(CoReportVO coReport) {
        CoReport coReportPO = new CoReport(coReport);
        Integer workerId = coReport.getWorkerId();
        // 更新 因为创建了索引所以直接通过 workerId 和 taskId 更新, 而不是先查到 id 再更新
        /* 为什么这样做呢? 因为 order 的状态有进行, 完成, 逾期, 0->1, 0->2 都可以, 1->2 就没必要了.
        xml 的 update 代码中判断了 order 的状态为零才做更新(此处是完成, 逾期在 mq) 否则抛错
        不允许对已经完成或者过期的订单进行更新
         */
//        if (orderMapper.updateOrderState(taskId, workerId, OrderState.FINISHED.getCode()) == 0) {
//            // TODO 应该更加精细地报错: 没有这个订单, 没有这个人, 订单已经完成, 订单已经过期
//            throw new ServiceException(TaskStatusCode.ORDER_STATUS_CANNOT_CHANGE);
//        }

        if (coReportMapper.insert(coReportPO) > 0)
            return new CoReportVO(coReportPO);
        throw new ServiceException(DefaultStatusCode.REQUEST_FAIL);
    }

    @Override
    public CoReportVO getAReport(Integer coReportId) {
        CoReport coReport = coReportMapper.selectByPrimaryKey(coReportId);
        if (coReport == null) {
            throw new ServiceException(ReportStatusCode.REPORT_NOT_EXIST);
        }
        return new CoReportVO(coReport);
    }

    @Override
    public List<CoReportVO> getCoReportsOfReport(Integer originReportId) {
        List<CoReport> reportList = coReportMapper.selectByOriginReport(originReportId);
        List<CoReportVO> ret = new ArrayList<>();
        for (CoReport coReport : reportList) {
            ret.add(new CoReportVO(coReport));
        }
        return ret;
    }

    @Override
    public CoReportVO getCoReportByUserAndOriginalReport(Integer workerId, Integer originalReportId) {

        CoReport coReport = coReportMapper.selectByWorkerAndOriginalReport(workerId, originalReportId);
        if (coReport != null) {
            return new CoReportVO(coReport);
        }
        throw new ServiceException(ReportStatusCode.REPORT_NOT_EXIST);
    }

    @Override
    public CoReportVO criticismByAuthor(Integer coReportId, Integer score) {
        if (coReportMapper.criticismByAuthor(coReportId, score) != 0) {
            CoReport coReport = coReportMapper.selectByPrimaryKey(coReportId);
            if (coReport != null) {
                return new CoReportVO(coReport);
            }
        }
        throw new ServiceException(ReportStatusCode.REPORT_NOT_EXIST);
    }
}
