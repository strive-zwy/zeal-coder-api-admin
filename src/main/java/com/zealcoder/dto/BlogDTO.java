package com.zealcoder.dto;

import lombok.Data;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/20 18:44
 * @Description : TODO
 */
@Data
public class BlogDTO {

    private Long id;
    private String title;
    private String tag;
    private String explain;
    private String cover;
    private String description;
}
