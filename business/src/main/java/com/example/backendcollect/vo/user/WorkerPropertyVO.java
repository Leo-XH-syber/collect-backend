package com.example.backendcollect.vo.user;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.po.WorkerProperty;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.WorkerPreferConvertMapper;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerPropertyVO {
    private Integer workerId;

    private Double ability = 0.0;

    private Integer activity = 0;

    private OSKind defaultOs;

    private TaskType[] preference;

    private Double cooperationAbility = 0.0;

    private Double criticismAbility = 0.0;

    private Double reportAvgCriticism = 0.0;

    private Double avgRepeatability = 0.0;

    private Integer taskCount = 0;

    private Integer historyBugCount = 0;

    private Double bugReportProportion = 0.0;

    private Double duplicateIndex = 0.0;

    public Double getSyntheticalScore() {
        return syntheticalScore;
    }

    public void setSyntheticalScore(Double syntheticalScore) {
        this.syntheticalScore = syntheticalScore;
    }

    private Double syntheticalScore=0.0;

    public WorkerPropertyVO(WorkerPropertyVO workerPreferVO) {
        BeanCopierWithCacheUtil.copy(workerPreferVO, this);
    }

    public WorkerPropertyVO(@NonNull WorkerProperty workerPrefer) {
        this(WorkerPreferConvertMapper.INSTANCE.po2vo(workerPrefer));
    }

    public WorkerPropertyVO(Integer uid, Double ability, Integer activity, OSKind defaultOs, TaskType[] preference) {
        this.workerId = uid;
        this.ability = ability;
        this.activity = activity;
        this.defaultOs = defaultOs;
        this.preference = preference;
    }
}
