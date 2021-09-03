package com.zealcoder.dto;

import lombok.Data;

import java.util.List;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/29 15:48
 * @Description : TODO
 */
@Data
public class CommentDTO {

    private Long id;
    private Long gmtCreate;
    private UserDTO commentator;
    private Integer type;
    private String content;
    private List<CommentDTO> commentSons;//评论的评论
}
