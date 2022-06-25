package com.rs.platform.entity;

import lombok.Data;

/**
 * @author : hongbo
 * @create 2022-06-22-11:29
 **/

@Data
public class HistoryConfig {
    private String confidence;
    private Integer minPixel;
    private Integer top;
    private Integer left;
    private Integer bottom;
    private Integer right;
}