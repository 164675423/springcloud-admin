package com.zh.am.domain.dto.role;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 新增/修改.
 */
public class SaveRoleDto {
  @NotNull
  private String name;
  private List<String> operations;
  @NotNull
  private List<String> pages;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getOperations() {
    return operations;
  }

  public void setOperations(List<String> operations) {
    this.operations = operations;
  }

  public List<String> getPages() {
    return pages;
  }

  public void setPages(List<String> pages) {
    this.pages = pages;
  }
}
