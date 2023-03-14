package com.mohan.domain.dto;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Integer pageNum;
    private Integer pageSize;
    private String roleName;
    private String status;


}
