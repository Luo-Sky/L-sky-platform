package com.rs.platform.service.dto;

import lombok.Data;

/**
 *
 * 认证用户
 * @author : hongbo
 * @create 2022-06-17-17:19
 **/

@Data
public class AuthUserDto {

    private String userName;
    private String password;
    private String code;
    private String phone;
    private String mail;
}
