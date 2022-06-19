package com.rs.platform.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rs.platform.common.MailUtils;
import com.rs.platform.common.Result;
import com.rs.platform.entity.User;
import com.rs.platform.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hongbo
 * @create 2022-04-15-10:26
 **/
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        User res = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()).eq(User::getPassword, user.getPassword()));
        //User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()).eq(User::getPassword, user.getPassword()));
        if(res == null){
            res = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getMail, user.getUserName()).eq(User::getPassword, user.getPassword()));
            if(res == null) {
                return Result.error("-1", "用户名或密码错误");
            }
        }
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        //User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,user.getUserName()));
        User res = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUserName,user.getUserName()));
        if(res != null){
            return Result.error("-1","用户名重复");
        }
        if(user.getPassword() == null){
            user.setPassword("123456");
        }
        userService.save(user);
        return Result.success();
    }

//    @PostMapping("/mail")
//    public Result<?> sendMail(@RequestParam String mail) throws Exception {
//        //使用随机数生成简易的验证码
//        double ma=Math.random()*100;
//        int ma1=(int) ma;
//        String yzm=""+ma1;
//        MailUtils.sendMail(mail,ma1); //收件人邮箱和验证码
//        return Result.success();
//    }


//    @PostMapping
//    public Result<?> save(@RequestBody User user){
//        if(user.getPassword() == null){
//            user.setPassword("123456");
//        }
////        userMapper.insert(user);
//        if(userService.save(user)){
//            return Result.success();
//        }
//        else{
//            return Result.error("-1","保存失败");
//        }
//    }

    @PutMapping
    public Result<?> update(@RequestBody User user){
//        userMapper.updateById(user);
        if(userService.updateById(user)){
            return Result.success();
        }else{
            return Result.error("-1","更新失败");
        }
    }
}
