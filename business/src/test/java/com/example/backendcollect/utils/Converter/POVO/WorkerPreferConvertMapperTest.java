package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.po.WorkerProperty;
import com.example.backendcollect.vo.user.WorkerPropertyVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WorkerPreferConvertMapperTest {

    @Test
    void test() {
        WorkerProperty workerPrefer = WorkerProperty.builder()
                .workerId(1)
                .activity(10)
                .ability(5.0)
                .defaultOs(OSKind.HarmonyOS.getCode())
                .preference("1;2")
                .build();

        WorkerPropertyVO workerPreferVO = new WorkerPropertyVO(workerPrefer);
        WorkerProperty newPO = new WorkerProperty(workerPreferVO);

        TaskType[] taskTypes = workerPreferVO.getPreference();
        StringBuilder preference = new StringBuilder();
        for (TaskType taskType : taskTypes) {
            preference.append(taskType.getCode()).append(";");
        }
        preference = new StringBuilder(preference.substring(0, preference.length() - 1));


        Assertions.assertEquals(workerPreferVO.getDefaultOs().getCode(), newPO.getDefaultOs());
        Assertions.assertEquals(preference.toString(), newPO.getPreference());

    }
}
