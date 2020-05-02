package com.zh.am.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class UserRole implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "user_id", type = IdType.NONE)
  private String userId;

  private String roleId;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  @Override
  public String toString() {
    return "UserRole{" +
        "userId=" + userId +
        ", roleId=" + roleId +
        "}";
  }
}
