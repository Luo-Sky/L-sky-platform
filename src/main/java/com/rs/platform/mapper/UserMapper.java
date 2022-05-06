package com.rs.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rs.platform.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : hongbo
 * @create 2022-04-15-10:22
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
