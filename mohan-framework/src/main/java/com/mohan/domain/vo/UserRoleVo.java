package com.mohan.domain.vo;

import com.mohan.domain.entity.Role;
import com.mohan.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVo {

    private List<Long> roleIds;
    private List<Role> roles;
    private User user;

}
