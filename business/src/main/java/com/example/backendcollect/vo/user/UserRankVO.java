package com.example.backendcollect.vo.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRankVO {
    private Integer uid;
    private String uname;
    private Double final_score;
}
