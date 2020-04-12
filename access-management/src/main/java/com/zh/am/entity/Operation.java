package com.zh.am.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class Operation implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.NONE)
  private String id;

  private String code;

  private String name;

  private String pageId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  @Override
  public String toString() {
    return "Operation{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", pageId=" + pageId +
        "}";
  }
}
