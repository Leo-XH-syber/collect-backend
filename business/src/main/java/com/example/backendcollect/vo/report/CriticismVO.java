package com.example.backendcollect.vo.report;

import com.example.backendcollect.po.Criticism;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.Converter.POVO.CriticismConvertMapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
public class CriticismVO {
    private Integer id;

    private Integer reportId;

    private Integer workerId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer score;

    private String comments;

    public CriticismVO(CriticismVO criticismVO) {
        BeanCopierWithCacheUtil.copy(criticismVO, this);
    }

    public CriticismVO(@NonNull Criticism criticism) {
        this(CriticismConvertMapper.INSTANCE.po2Vo(criticism));
    }
}
