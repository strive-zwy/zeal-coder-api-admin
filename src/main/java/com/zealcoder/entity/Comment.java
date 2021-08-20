package com.zealcoder.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import java.io.Serializable;
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
 * Comment: 数据映射实体定义
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
    table = "comment",
    suffix = ""
)
public class Comment extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   */
  @TableId("id")
  private Long id;

  /**
   */
  @TableField(
      value = "gmt_create",
      insert = "now()"
  )
  private Long gmtCreate;

  /**
   */
  @TableField(
      value = "gmt_modified",
      insert = "now()",
      update = "now()"
  )
  private Long gmtModified;

  /**
   * 博客id
   */
  @TableField("blog_id")
  private Long blogId;

  /**
   * 评论人
   */
  @TableField("commentator")
  private Long commentator;

  /**
   * 评论内容
   */
  @TableField("content")
  private String content;

  /**
   * 父级id
   */
  @TableField("parent_id")
  private Integer parentId;

  /**
   * 评论类型 0-博客的评论 1-评论的评论
   */
  @TableField("type")
  private Integer type;

  @Override
  public Serializable findPk() {
    return this.id;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return Comment.class;
  }

  @Override
  public final Comment changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final Comment changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
