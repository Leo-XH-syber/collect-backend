package com.example.backendcollect.serviceimpl.recommend;

import com.example.backendcollect.enums.errorcode.Impl.DefaultStatusCode;
import com.example.backendcollect.enums.types.RecStrategyType;
import com.example.backendcollect.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RecStrategyFactory {

    @Resource
    public ALSRec alsRec;
    @Resource
    public HistoryPopularRec historyPopularRec;
    @Resource
    public MonthPopularRec monthPopularRec;
    @Resource
    public RealTimeRec realTimeRec;

    public RecommendStrategy getRecStrategy(RecStrategyType type) {
        if (type.getCode().equals(RecStrategyType.ALS.getCode())) {
            return alsRec;
        } else if (type.getCode().equals(RecStrategyType.HISTORY_POPULAR.getCode())) {
            return historyPopularRec;
        } else if (type.getCode().equals(RecStrategyType.MONTH_POPULAR.getCode())) {
            return monthPopularRec;
        } else if (type.getCode().equals(RecStrategyType.REAL_TIME.getCode())) {
            return realTimeRec;
        }
        throw new ServiceException(DefaultStatusCode.REQUEST_FAIL);
    }
}
