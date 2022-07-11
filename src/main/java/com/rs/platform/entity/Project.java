package com.rs.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @author : hongbo
 * @create 2022-05-06-20:05
 **/
@TableName("project")
@Data
public class Project {
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String title;
    private String img;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
