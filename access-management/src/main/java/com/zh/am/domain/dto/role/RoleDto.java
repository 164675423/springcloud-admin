package com.zh.am.domain.dto.role;

public class RoleDto {
  private String id;
  private String name;
  private Integer status;
  private Boolean readOnly;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
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

  public Boolean getReadOnly() {
    return readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }
}
