package com.rs.platform.tools.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : hongbo
 * @create 2022-06-17-16:58
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    /**
     * 发送邮箱列表
     */
    private List<String> tos;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String content;
}
