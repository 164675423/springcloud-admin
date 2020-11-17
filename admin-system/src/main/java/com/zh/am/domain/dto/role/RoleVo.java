package com.zh.am.domain.dto.role;

import com.zh.common.base.constants.Enums;

import java.util.ArrayList;
import java.util.List;

public class RoleVo {
  private String id;
  private String name;
  private List<String> options;
  private int status;
  private Boolean readOnly;
  private List<PageRoleDto> permissions;
  private List<String> pages;
  private List<String> operations;

  public List<String> getPages() {
    return pages;
  }

  public void setPages(List<String> pages) {
    this.pages = pages;
  }

  public List<String> getOperations() {
    return operations;
  }

  public void setOperations(List<String> operations) {
    this.operations = operations;
  }

  public List<PageRoleDto> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<PageRoleDto> permissions) {
    this.permissions = permissions;
  }

  public void setStatus(int status) {
    this.status = status;
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

  public List<String> getOptions() {
    options = new ArrayList<String>();
    if (!this.readOnly && this.status == Enums.BaseDataStatus.有效.getIndex()) {
      options.add("update");
      options.add("abandon");
    }
    return options;
  }

  public void setOptions(List<String> options) {
    this.options = options;
  }

  public void setReadOnly(Boolean readonly) {
    this.readOnly = readonly;
  }
}
