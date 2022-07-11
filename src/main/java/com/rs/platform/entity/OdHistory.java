package com.rs.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author : hongbo
 * @create 2022-05-06-19:57
 **/

@TableName("odhistory")
@Data
public class OdHistory {
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String sourceImg;
    private String sourceImgName;
    private String resultImg;
    private String resultImgName;
    private String result;
    private Integer choose; //是否存在框选 0为否，1为存在
}

