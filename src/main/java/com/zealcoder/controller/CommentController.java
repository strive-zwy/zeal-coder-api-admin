package com.zealcoder.controller;

import com.zealcoder.dao.intf.CommentDao;
import com.zealcoder.dto.CommentDTO;
import com.zealcoder.dto.CommentPostDTO;
import com.zealcoder.entity.Comment;
import com.zealcoder.entity.User;
import com.zealcoder.exception.BizException;
import com.zealcoder.exception.ResultBody;
import com.zealcoder.mapper.CommentMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/29 15:37
 * @Description : comment api 接口
 */

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Qualifier("commentMapper")
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentDao commentDao;

    @GetMapping(value = "/getCommentByBid")
    public ResultBody getCommentByBid(@Validated Long bid){
        List<CommentDTO> list = commentDao.getCommentByBid(bid);
        return ResultBody.success(list);
    }

    @RequiresRoles({"jwtToken"})
    @PostMapping(value = "/insert")
    public ResultBody insert(@RequestBody CommentPostDTO commentPostDTO){
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Comment comment = new Comment();
        comment.setCommentator(commentPostDTO.getCommentator())
            .setContent(commentPostDTO.getContent())
            .setBlogId(commentPostDTO.getBlogId())
            .setGmtCreate(System.currentTimeMillis())
            .setGmtModified(System.currentTimeMillis())
            .setParentId(commentPostDTO.getParentId())
            .setType(commentPostDTO.getType());
        int count = commentMapper.insert(comment);
        if (count < 1) {
            throw  new BizException("-1","comment 数据新增失败");
        }
        return ResultBody.success();
    }

}
