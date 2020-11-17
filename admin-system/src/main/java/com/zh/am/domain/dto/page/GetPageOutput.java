package com.zh.am.domain.dto.page;

import com.zh.am.domain.dto.role.OperationDto;

import java.util.ArrayList;
import java.util.List;

public class GetPageOutput {
  private String id;
  private String name;
  private String title;
  private String url;
  private String icon;
  private Integer weight;
  private Integer level;
  private String parentId;
  private List<GetPageOutput> items;
  private List<OperationDto> operations;
  private Integer maxLevel;

  /**
   * 添加节点集合.
   *
   * @param pageDto com.zh.commom.base.page
   */
  public void add(GetPageOutput pageDto) {
    if (items == null) {
      items = new ArrayList<>();
    }
    items.add(pageDto);
  }

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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
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

  public List<GetPageOutput> getItems() {
    return items;
  }

  public void setItems(List<GetPageOutput> items) {
    this.items = items;
  }

  public List<OperationDto> getOperations() {
    return operations;
  }

  public void setOperations(List<OperationDto> operations) {
    this.operations = operations;
  }

  public Integer getMaxLevel() {
    return maxLevel;
  }

  public void setMaxLevel(Integer maxLevel) {
    this.maxLevel = maxLevel;
  }
}
