package com.zh.am.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class Whitelist implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "api_id", type = IdType.NONE)
  private String apiId;

  public String getApiId() {
    return apiId;
  }

  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  @Override
  public String toString() {
    return "Whitelist{" +
        "apiId=" + apiId +
        "}";
  }
}
