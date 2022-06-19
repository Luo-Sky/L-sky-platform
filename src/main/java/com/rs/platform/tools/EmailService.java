package com.rs.platform.tools;

import com.rs.platform.tools.dto.EmailDto;

/**
 *  邮箱服务接口
 * @author : hongbo
 * @create 2022-06-17-17:00
 **/
public interface EmailService {
    /**
     * 发送邮件
     * @param emailDto 邮箱列表
     */
    void send(EmailDto emailDto);
}
