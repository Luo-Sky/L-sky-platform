package com.rs.platform.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rs.platform.entity.User;

/**
 * @author : hongbo
 * @create 2022-04-15-10:17
 **/
public interface IUserService extends IService<User> {
    //自己添加的方法
    IPage<User> getPage(int currentPage, int pageSize);
}
