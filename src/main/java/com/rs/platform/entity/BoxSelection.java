package com.rs.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author : hongbo
 * @create 2022-06-24-14:51
 **/

@TableName("boxselection")
@Data
public class BoxSelection {
    @TableId(value ="id", type = IdType.AUTO)
    private Long id;
    private Integer type;  //区别四个任务 0cd变化检测 1oc地物分类 2od目标检测 3oe目标提取
    private String task;   //四个任务下的具体的任务名称
    private Long historyId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer topRow;
    private Integer leftColumn;
    private Integer bottomRow;
    private Integer rightColumn;
    private String resultImg;
    private String resultImgName;
    private String result;
}
