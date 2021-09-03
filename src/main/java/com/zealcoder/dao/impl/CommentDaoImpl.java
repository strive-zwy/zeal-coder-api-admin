package com.zealcoder.dao.impl;

import com.zealcoder.dao.base.CommentBaseDao;
import com.zealcoder.dao.intf.CommentDao;
import com.zealcoder.dto.CommentDTO;
import com.zealcoder.dto.UserDTO;
import com.zealcoder.entity.Comment;
import com.zealcoder.entity.User;
import com.zealcoder.enums.CommentEnums;
import com.zealcoder.mapper.CommentMapper;
import com.zealcoder.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * CommentDaoImpl: 数据操作接口实现
 *
 * 这只是一个减少手工创建的模板文件
 * 可以任意添加方法和实现, 更改作者和重定义类名
 */
@Repository
public class CommentDaoImpl extends CommentBaseDao implements CommentDao {

    @Qualifier("commentMapper")
    @Autowired
    private CommentMapper commentMapper;
    @Qualifier("userMapper")
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<CommentDTO> getCommentByBid(Long bid) {
        List<CommentDTO> commentDTOS = new ArrayList<>();
        List<Comment> list = commentMapper.listEntity(
                commentMapper.query().where.blogId().eq(bid).
                        and.type().eq(CommentEnums.COMMENT_TO_BLOG.getType()).end()
        );
        if (!list.isEmpty())
        list.forEach(comment -> {
            CommentDTO dto = getCommentDTO(comment);
            List<CommentDTO> commentSons = new ArrayList<>();
            List<Comment> sons = commentMapper.listEntity(
                    commentMapper.query().where.blogId().eq(bid)
                            .and.type().eq(CommentEnums.COMMENT_TO_COMMENT.getType())
                            .and.parentId().eq(comment.getId()).end()
            );
            if (!sons.isEmpty())
            sons.forEach(commentSon -> {
                CommentDTO dtoSon = getCommentDTO(commentSon);
                commentSons.add(dtoSon);
            });
            dto.setCommentSons(commentSons);
            commentDTOS.add(dto);
        });
        return commentDTOS;
    }

    private CommentDTO getCommentDTO(Comment commentSon) {
        CommentDTO dtoSon = new CommentDTO();
        BeanUtils.copyProperties(commentSon, dtoSon);
        User uu = userMapper.findById(commentSon.getCommentator());
        UserDTO userD = new UserDTO();
        userD.setAccount(uu.getAccount());
        userD.setAvatar(uu.getAvatar());
        userD.setId(uu.getId());
        dtoSon.setCommentator(userD);
        return dtoSon;
    }
}
