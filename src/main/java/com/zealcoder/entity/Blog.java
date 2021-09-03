package com.zealcoder.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.LogicDelete;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Blog: 数据映射实体定义
 *
 * @author Powered By Fluent Mybatis
 */
@SuppressWarnings({"unchecked"})
@Data
@Accessors(
    chain = true
)
@EqualsAndHashCode(
    callSuper = false
)
@FluentMybatis(
    table = "blog",
    suffix = ""
)
public class Blog extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   */
  @TableId("id")
  private Long id;

  /**
   * 是否已删除 0-false 1-true
   */
  @TableField(
      value = "is_deleted",
      insert = "0"
  )
  @LogicDelete
  private Boolean isDeleted;

  /**
   * 评论数量
   */
  @TableField("comment_count")
  private Integer commentCount;

  /**
   * 封面
   */
  @TableField("cover")
  private String cover;

  /**
   * 博客文章
   */
  @TableField("description")
  private String description;

  /**
   * 博客描述
   */
  @TableField("explain")
  private String explain;

  /**
   */
  @TableField("gmt_create")
  private Long gmtCreate;

  /**
   */
  @TableField("gmt_modified")
  private Long gmtModified;

  /**
   * 点赞数量
   */
  @TableField("like_count")
  private Integer likeCount;

  /**
   * 标签
   */
  @TableField("tag")
  private String tag;

  /**
   * 标题
   */
  @TableField("title")
  private String title;

  /**
   * 浏览数量
   */
  @TableField("view_count")
  private Integer viewCount;

  @Override
  public Serializable findPk() {
    return this.id;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return Blog.class;
  }

  @Override
  public final Blog changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final Blog changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
