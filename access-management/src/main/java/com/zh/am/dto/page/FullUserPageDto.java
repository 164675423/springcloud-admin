package com.zh.am.dto.page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class FullUserPageDto {
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
  private List<FullUserPageDto> parents = new ArrayList<>();

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

  public List<FullUserPageDto> getParents() {
    return parents;
  }

  public void setParents(List<FullUserPageDto> parents) {
    this.parents = parents;
  }
}
