package com.example.backendcollect.vo.user;

import com.example.backendcollect.enums.types.OSKind;
import com.example.backendcollect.enums.types.TaskType;
import com.example.backendcollect.enums.types.UserRole;
import com.example.backendcollect.utils.BeanCopierWithCacheUtil;
import com.example.backendcollect.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAllInfoVO {
    private Integer uid;
    @NotNull(message = "EMPTY_USERNAME")
    private String uname;
    @NotNull(message = "EMPTY_EMAIL")
    @Email(message = "EMAIL_FORMAT_ERROR")
    private String email;
    @NotNull(message = "EMPTY_PASSWORD")
    @Size(min = 6, max = 16, message = "PASSWORD_FORMAT_ERROR")
    private String password;
    private UserRole userRole;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private Integer workerId;
    private Double ability = 0.0;
    private Integer activity = 0;
    private OSKind defaultOs;
    private TaskType[] preference;

    public UserAllInfoVO(UserAllInfoVO userProfileVO) {
        BeanCopierWithCacheUtil.copy(userProfileVO, this);
    }

    public UserAllInfoVO(UserVO userVO) {
        this.uid = userVO.getId();
        this.uname = userVO.getUname();
        this.email = userVO.getEmail();
        this.password = null;
        this.userRole = userVO.getUserRole();
        this.createTime = userVO.getCreateTime();
    }

    public UserAllInfoVO(UserVO userVO, WorkerPropertyVO workerPreferVO) {
        this.uid = userVO.getId();
        this.uname = userVO.getUname();
        this.email = userVO.getEmail();
        this.password = null;
        this.userRole = userVO.getUserRole();
        this.createTime = userVO.getCreateTime();
        if (workerPreferVO != null) {
            this.workerId = workerPreferVO.getWorkerId();
            this.ability = workerPreferVO.getAbility();
            this.activity = workerPreferVO.getActivity();
            this.defaultOs = workerPreferVO.getDefaultOs();
            this.preference = workerPreferVO.getPreference();
            if (this.activity != null) {
                if(this.createTime!=null){
                    int daydiff = DateUtil.getDayDiffer(this.createTime, new Date());
                    this.activity = (this.activity / (daydiff == 0 ? 1 : daydiff) * 10);
                }
            } else
                this.activity = 0;
        }
    }

    public UserVO generateUserVO() {
        return new UserVO(uid, uname, email, password, userRole, createTime);
    }

    public WorkerPropertyVO generateWorkerPreferVO() {
        return new WorkerPropertyVO(uid, ability, activity, defaultOs, preference);
    }
}
