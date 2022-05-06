package com.rs.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : hongbo
 * @create 2022-04-15-10:19
 **/

@TableName("user")
@Data
public class User implements Serializable {
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private String phone;
    private String mail;
}
