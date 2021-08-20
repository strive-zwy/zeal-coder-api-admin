package com.zealcoder.dto;

import lombok.Data;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/20 20:01
 * @Description : TODO
 */
@Data
public class SearchDTO {

    private Integer page = 0;
    private Integer size = 5;
    private String search;
    private String search2;


}
