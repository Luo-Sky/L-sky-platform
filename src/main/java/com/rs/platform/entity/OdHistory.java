package com.rs.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
    private Date startTime;
    private Date endTime;
    private String sourceImg;
    private String resultImg;
    private String result;
}

