package com.zh.am.domain.dto.page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class UserPageDto {
  @JsonIgnore
  private String id;
  private String code;
  private String name;
  private String url;
  @JsonIgnore
  private Integer level;
  @JsonIgnore
  private Integer weight;
  @JsonIgnore
  private String parentId;
  private String icon;
  private List<UserPageDto> children = new ArrayList<>();

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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    this.level = level;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public List<UserPageDto> getChildren() {
    return children;
  }

  public void setChildren(List<UserPageDto> children) {
    this.children = children;
  }
}
