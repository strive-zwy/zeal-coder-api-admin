package com.zealcoder.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import java.io.Serializable;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Message: 数据映射实体定义
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
    table = "message",
    suffix = ""
)
public class Message extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   */
  @TableId("id")
  private Long id;

  /**
   * 留言内容
   */
  @TableField("content")
  private String content;

  /**
   * 留言人-用户Id
   */
  @TableField("creator")
  private Long creator;

  /**
   */
  @TableField("gmt_create")
  private Long gmtCreate;

  /**
   */
  @TableField("gmt_modified")
  private Long gmtModified;

  @Override
  public Serializable findPk() {
    return this.id;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return Message.class;
  }

  @Override
  public final Message changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final Message changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
