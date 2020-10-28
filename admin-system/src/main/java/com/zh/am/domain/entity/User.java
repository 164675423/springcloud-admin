package com.zh.am.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.NONE)
  private String id;

  private String name;

  /**
   * 用户名
   */
  private String username;

  private String password;

  private String creatorId;

  private String creatorName;

  private Date createTime;

  private String modifierId;

  private String modifierName;

  private Date modifyTime;

  /**
   * 2:有效;
   * 0:冻结;
   */
  private Integer status;

  /**
   * 默认false
   */
  private Boolean readonly;

  @Version
  private Timestamp rowVersion;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(String creatorId) {
    this.creatorId = creatorId;
  }

  public String getCreatorName() {
    return creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getModifierId() {
    return modifierId;
  }

  public void setModifierId(String modifierId) {
    this.modifierId = modifierId;
  }

  public String getModifierName() {
    return modifierName;
  }

  public void setModifierName(String modifierName) {
    this.modifierName = modifierName;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Boolean getReadonly() {
    return readonly;
  }

  public void setReadonly(Boolean readonly) {
    this.readonly = readonly;
  }

  public Timestamp getRowVersion() {
    return rowVersion;
  }

  public void setRowVersion(Timestamp rowVersion) {
    this.rowVersion = rowVersion;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name=" + name +
        ", username=" + username +
        ", password=" + password +
        ", creatorId=" + creatorId +
        ", creatorName=" + creatorName +
        ", createTime=" + createTime +
        ", modifierId=" + modifierId +
        ", modifierName=" + modifierName +
        ", modifyTime=" + modifyTime +
        ", status=" + status +
        ", readonly=" + readonly +
        ", rowVersion=" + rowVersion +
        "}";
  }
}
