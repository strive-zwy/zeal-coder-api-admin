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
 * User: 数据映射实体定义
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
    table = "user",
    suffix = ""
)
public class User extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   */
  @TableId("id")
  private Long id;

  /**
   * 账号
   */
  @TableField("account")
  private String account;

  /**
   * 头像
   */
  @TableField("avatar")
  private String avatar;

  /**
   * 用户邮箱，一个邮箱仅限一个用户注册
   */
  @TableField("email")
  private String email;

  /**
   */
  @TableField("gmt_create")
  private Long gmtCreate;

  /**
   */
  @TableField("gmt_modified")
  private Long gmtModified;

  /**
   * 选项凭证
   */
  @TableField("jwtToken")
  private String jwtToken;

  /**
   * 密码或Github-token
   */
  @TableField("secret")
  private String secret;

  /**
   * 用户类型 0-GitHub 1-普通用户
   */
  @TableField("type")
  private Integer type;

  @Override
  public Serializable findPk() {
    return this.id;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return User.class;
  }

  @Override
  public final User changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final User changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
