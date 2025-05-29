package com.honsoft.shopmall.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRoleAssignRequestDto {
    private Long userId;
    private Long roleId;
}

