package com.mohan.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddRoleDto {

    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    private List<Long> menuIds;
    private String remark;

}
