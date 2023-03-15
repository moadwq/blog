package com.mohan.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageDto {

    private Integer pageNum;
    private Integer pageSize;
    private String userName;
    private String email;
    private String status;

}
