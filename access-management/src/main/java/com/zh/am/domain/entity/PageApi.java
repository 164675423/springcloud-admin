package com.zh.am.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class PageApi implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "page_id", type = IdType.NONE)
  private String pageId;

  private String apiId;

  public String getPageId() {
    return pageId;
  }

  public void setPageId(String pageId) {
    this.pageId = pageId;
  }

  public String getApiId() {
    return apiId;
  }

  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  @Override
  public String toString() {
    return "PageApi{" +
        "pageId=" + pageId +
        ", apiId=" + apiId +
        "}";
  }
}
