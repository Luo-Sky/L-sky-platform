package com.rs.platform.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rs.platform.entity.User;
import com.rs.platform.service.AuthService;
import com.rs.platform.service.IUserService;
import com.rs.platform.service.dto.AuthUserDto;
import com.rs.platform.tools.EmailService;
import com.rs.platform.tools.dto.EmailDto;
import com.rs.platform.utlis.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;

/**
 * @author : hongbo
 * @create 2022-06-17-17:20
 **/
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuthServiceImpl implements AuthService {

    @Value("${code.expiration}")
    private Long expiration;

    private final IUserService userService;
    private final RedisUtils redisUtils;
    private final EmailService emailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int register(AuthUserDto authUserDto) {
        // 通过email获取redis中的code
        Object value = redisUtils.get(authUserDto.getMail());
        if (value == null || !value.toString().equals(authUserDto.getCode())) {
            return -1;
//            throw new RuntimeException("无效验证码");
        } else {
            redisUtils.del(authUserDto.getMail());
        }

        // 如果前端没有传入用户名，则以邮箱号作为用户名进行注册
        String userName = StringUtils.isEmpty(authUserDto.getUserName()) ? authUserDto.getMail() : authUserDto.getUserName();

        User res = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,userName));
        if(res != null){
            return -3;
//            throw new RuntimeException("用户名已存在");
        }

        // 创建用户
        User sysUser = new User();
        sysUser.setUserName(userName);
        if (authUserDto.getPhone() != null) {
            sysUser.setPhone(authUserDto.getPhone());
        }
//        sysUser.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
//        try {
//            sysUser.setPassword(passwordEncoder.encode(RsaUtils.decryptByPrivateKey(privateKey, authUserDto.getPassword())));
//        } catch (Exception e) {
//            throw new RuntimeException("注册密码异常");
//        }
        if (authUserDto.getPassword() == null) {
            sysUser.setPassword("123456");
        } else {
            sysUser.setPassword(authUserDto.getPassword());
        }
        sysUser.setMail(authUserDto.getMail());
        if (userService.save(sysUser)) {
            return 0;
        } else {
            return -2;
        }
    }

    @Override
    public User findPassword(String mail, String code) {
        // 通过email获取redis中的code
        Object value = redisUtils.get(mail);
        if (value == null || !value.toString().equals(code)) {
            return null;
//            throw new RuntimeException("无效验证码");
        } else {
            redisUtils.del(mail);
        }
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getMail, mail));
        return user;
    }


    /**
     * @param email
     * @param type  1 为找回密码  0 为 注册账号时发送
     * @return
     */
    @Override
    public int sendMailCode(String email, int type) {

        User tmpUser = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getMail, email));
        if (type == 0) {
            // 查看注册邮箱是否存在
            if (tmpUser != null) {
                return -1;
//            throw new RuntimeException("注册邮箱已存在");
            }
        } else {
            if (tmpUser == null) {
                return -2;
                //找回密码邮箱要正确
            }
        }


        // 获取发送邮箱验证码的HTML模板
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template;
        if(type == 0) {
            template = engine.getTemplate("email-code.ftl");
        }
        else{
            template = engine.getTemplate("email-code-password.ftl");
        }

        // 从redis缓存中尝试获取验证码
        Object code = redisUtils.get(email);
        if (code == null) {
            // 如果在缓存中未获取到验证码，则产生6位随机数，放入缓存中
            code = RandomUtil.randomNumbers(6);
            if (!redisUtils.set(email, code, expiration)) {
                throw new RuntimeException("后台缓存服务异常");
            }
        }
        // 发送验证码
        emailService.send(new EmailDto(Collections.singletonList(email),
                "邮箱验证码", template.render(Dict.create().set("code", code))));
        return 0;
    }
}
