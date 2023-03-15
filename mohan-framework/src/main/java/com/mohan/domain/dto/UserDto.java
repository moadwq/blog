package com.mohan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String email;
    private Long id;
    private String nickName;
    private String status;
    private String userName;
    private List<Long> roleIds;
    //用户性别（0男，1女，2未知）
    private String sex;

}
