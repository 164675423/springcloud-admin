package com.zh.am.domain.dto.page;

import com.zh.am.domain.dto.role.OperationDto;

import java.util.List;

public class PageDetailsVo {
  private String id;
  private String name;
  private List<PageDetailsVo> items;
  private List<OperationDto> operations;

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

  public List<PageDetailsVo> getItems() {
    return items;
  }

  public void setItems(List<PageDetailsVo> items) {
    this.items = items;
  }

  public List<OperationDto> getOperations() {
    return operations;
  }

  public void setOperations(List<OperationDto> operations) {
    this.operations = operations;
  }
}
