package com.zh.am.domain.dto.page;

import com.zh.am.domain.dto.role.OperationDto;

import java.util.List;

public class PageVo {
  private String id;
  private String name;
  private Integer itemCount;
  private Integer operationCount;
  private List<PageVo> items;
  private List<OperationDto> operations;
  private Integer level;
  private Integer maxLevel;


  public void setMaxLevel(Integer maxLevel) {
    this.maxLevel = maxLevel;
  }

  public void setLevel(Integer level) {
    this.level = level;
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

  public Integer getItemCount() {
    return items != null ? items.size() : null;
  }

  public void setItemCount(Integer itemCount) {
    this.itemCount = itemCount;
  }

  public Integer getOperationCount() {
    return operations != null ? operations.size() : null;
  }

  public void setOperationCount(Integer operationCount) {
    this.operationCount = operationCount;
  }

  /**
   * 根据最大层级 控制 items 的显示.
   *
   * @return list pageVo
   */
  public List<PageVo> getItems() {
    if (this.maxLevel != null) {
      return this.level < this.maxLevel ? items : null;
    }
    return items;
  }

  public void setItems(List<PageVo> items) {
    this.items = items;
  }

  public void setOperations(List<OperationDto> operations) {
    this.operations = operations;
  }
}
