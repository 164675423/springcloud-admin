package com.zh.am.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class Page implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.NONE)
  private String id;

  private String code;

  private String name;

  private String pinyin;

  private String initial;

  private String url;

  private Integer level;

  private String parentId;

  private Integer weight;

  private String icon;

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

  public String getPinyin() {
    return pinyin;
  }

  public void setPinyin(String pinyin) {
    this.pinyin = pinyin;
  }

  public String getInitial() {
    return initial;
  }

  public void setInitial(String initial) {
    this.initial = initial;
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

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  @Override
  public String toString() {
    return "Page{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", pinyin=" + pinyin +
        ", initial=" + initial +
        ", url=" + url +
        ", level=" + level +
        ", parentId=" + parentId +
        ", weight=" + weight +
        ", icon=" + icon +
        "}";
  }
}
