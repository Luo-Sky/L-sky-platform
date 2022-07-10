package com.rs.platform.controller;

import com.rs.platform.common.Result;
import com.rs.platform.entity.User;
import com.rs.platform.service.AuthService;
import com.rs.platform.service.dto.AuthUserDto;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hongbo
 * @create 2022-06-17-17:54
 **/

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 发送邮箱验证码进行注册
     */
    @PostMapping(value = "/getEmailCode")
    public Result getEmailCode(@RequestParam String mail, @RequestParam Integer type) {
        int code = authService.sendMailCode(mail, type);
        if (code == -1) {
            return Result.error("-1", "注册邮箱已存在");
        } else if (code == -2) {
            return Result.error("-2", "邮箱未注册账号");
        }
        return Result.success();
    }

    /**
     * 找回密码
     *
     * @param mail
     * @return
     */
    @GetMapping(value = "/findPassword")
    public Result findPassWord(@RequestParam String mail, @RequestParam String captcha) {
        User user = authService.findPassword(mail, captcha);
        if  (user == null){
            return Result.error("-1", "验证码错误");
        }
        return Result.success(user);
    }


    @PostMapping(value = "/register")
    public Result register(@RequestBody AuthUserDto authUserDto) {
        int code = authService.register(authUserDto);
        if (code == 0) {
            return Result.success();
        } else if (code == -1) {
            return Result.error("-1", "验证码错误");
        } else {
            return Result.error("-2", "注册失败");
        }
    }
}
