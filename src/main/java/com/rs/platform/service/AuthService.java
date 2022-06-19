package com.rs.platform.service;

import com.rs.platform.entity.User;
import com.rs.platform.service.dto.AuthUserDto;

/**
 * @author : hongbo
 * @create 2022-06-17-17:18
 **/
public interface AuthService {
    /**
     * 注册
     *
     * @param authUserDto 认证用户请求信息
     * @return 是否成功
     */
    int register(AuthUserDto authUserDto);

    /**
     *
     * @param mail
     * @return
     */
    User findPassword(String mail, String code);

    /**
     * 向指定邮箱发送验证码
     * @param email  邮箱
     * @param type  找回密码 or 注册账号
     * @return
     */
    int sendMailCode(String email, int type);
}
