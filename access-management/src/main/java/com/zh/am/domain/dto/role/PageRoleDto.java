package com.zh.am.domain.dto.role;

import java.util.List;

public class PageRoleDto {
  private String id;
  private String name;
  private List<PageRoleDto> items;
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

  public List<PageRoleDto> getItems() {
    return items;
  }

  public void setItems(List<PageRoleDto> items) {
    this.items = items;
  }

  public List<OperationDto> getOperations() {
    return operations;
  }

  public void setOperations(List<OperationDto> operations) {
    this.operations = operations;
  }
}
