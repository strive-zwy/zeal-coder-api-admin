package com.zealcoder.dao.intf;

import cn.org.atool.fluent.mybatis.base.IBaseDao;
import com.zealcoder.dto.CommentDTO;
import com.zealcoder.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CommentDao: 数据操作接口
 *
 * 这只是一个减少手工创建的模板文件
 * 可以任意添加方法和实现, 更改作者和重定义类名
 * <p/>@author Powered By Fluent Mybatis
 */
@Service
public interface CommentDao extends IBaseDao<Comment> {

    List<CommentDTO> getCommentByBid(Long bid);
}
