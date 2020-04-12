package com.zh.gateway.authentication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class Permission implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "role_id", type = IdType.NONE)
  private String roleId;

  private String relatedId;

  /**
   * 1: 页面;
   * 2: 业务操作;
   */
  private Integer relatedType;

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public String getRelatedId() {
    return relatedId;
  }

  public void setRelatedId(String relatedId) {
    this.relatedId = relatedId;
  }

  public Integer getRelatedType() {
    return relatedType;
  }

  public void setRelatedType(Integer relatedType) {
    this.relatedType = relatedType;
  }

  @Override
  public String toString() {
    return "Permission{" +
        "roleId=" + roleId +
        ", relatedId=" + relatedId +
        ", relatedType=" + relatedType +
        "}";
  }
}
