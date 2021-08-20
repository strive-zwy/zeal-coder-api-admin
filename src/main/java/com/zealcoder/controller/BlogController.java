package com.zealcoder.controller;

import com.zealcoder.dto.BlogDTO;
import com.zealcoder.dto.PageDTO;
import com.zealcoder.dto.SearchDTO;
import com.zealcoder.entity.Blog;
import com.zealcoder.exception.BizException;
import com.zealcoder.exception.ResultBody;
import com.zealcoder.mapper.BlogMapper;
import com.zealcoder.wrapper.BlogQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/20 17:26
 * @Description : blog api 接口
 */

@RestController
@RequestMapping(value = "/blog")
public class BlogController {

    @Qualifier("blogMapper")
    @Autowired
    private BlogMapper blogMapper;

    @GetMapping(value = "/getAllBlog")
    public ResultBody getAllBlog(@RequestBody SearchDTO search){
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.limit(search.getPage(),search.getSize())
                .orderBy.gmtCreate().desc().end();
        if (StringUtils.isNotBlank(search.getSearch())){
            blogQuery.where.or.title().like(search.getSearch()).end();
        }
        if (StringUtils.isNotBlank(search.getSearch2())){
            blogQuery.where.or.tag().like("、"+search.getSearch2()+"、");
        }
        int total = blogMapper.count(blogQuery);
        List<Blog> list = blogMapper.listEntity(blogQuery);
        PageDTO<Blog> pageDTO = new PageDTO<>();
        pageDTO.setList(list);
        pageDTO.setPagination(total,search.getPage(),search.getSize());
        return ResultBody.success(pageDTO);
    }

    @PostMapping(value = "/insert")
    public ResultBody insert(@RequestBody BlogDTO blogDTO){
        Blog blog = new Blog();
        blog.setTitle(blogDTO.getTitle())
                .setTag(blogDTO.getTag())
                .setDescription(blogDTO.getDescription());
        int c = blogMapper.insert(blog);
        if (c < 1) {
            throw  new BizException("-1","blog 数据新增失败");
        }
        return ResultBody.success();
    }

    @PutMapping(value = "/update")
    public ResultBody update(@RequestBody BlogDTO blogDTO){
        Blog blog = new Blog();
        blog.setId(blogDTO.getId())
                .setTitle(blogDTO.getTitle())
                .setTag(blogDTO.getTag())
                .setDescription(blogDTO.getDescription());
        int c = blogMapper.updateById(blog);
        if (c < 1) {
            throw  new BizException("-2","blog 数据更新失败");
        }
        return ResultBody.success();
    }

    @PutMapping(value = "/delete")
    public ResultBody delete(@RequestBody BlogDTO blog){
        int c = blogMapper.logicDeleteById(blog.getId());
        if (c < 1) {
            throw  new BizException("-2","blog 数据删除失败");
        }
        return ResultBody.success();
    }


}
