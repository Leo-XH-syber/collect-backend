package com.example.backendcollect.utils.Converter.POVO;

import com.example.backendcollect.po.Criticism;
import com.example.backendcollect.vo.report.CriticismVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CriticismConvertMapperTest {

    @Test
    void test() {
        Criticism criticism = Criticism.builder()
                .id(1)
                .reportId(1)
                .workerId(1)
                .createTime(new Date())
                .comments("xxxx")
                .score(5)
                .build();

        CriticismVO criticismVO = new CriticismVO(criticism);
        Criticism newPO = new Criticism(criticismVO);

        Assertions.assertEquals(newPO.getId(), criticismVO.getId());
        Assertions.assertEquals(newPO.getCreateTime(), criticismVO.getCreateTime());
    }

}