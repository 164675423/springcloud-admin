package com.zh.am.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zh.am.common.constant.Enums;
import com.zh.am.domain.dto.common.IdName;

import java.util.ArrayList;
import java.util.List;

public class GetUsersOutput {
  private String id;
  private String name;
  private String username;
  @JsonIgnore
  private Boolean readonly;
  private Integer status;
  private List<IdName> roles = new ArrayList<>();
  private List<String> options = new ArrayList<>();

  public Boolean getReadonly() {
    return readonly;
  }

  public void setReadonly(Boolean readonly) {
    this.readonly = readonly;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public List<IdName> getRoles() {
    return roles;
  }

  public void setRoles(List<IdName> roles) {
    this.roles = roles;
  }

  public List<String> getOptions() {
    if (status != null && Enums.BaseDataStatus.有效.getIndex() == status) {
      if (readonly != null && readonly == false) {
        options.add("update");
        options.add("abandon");
      }
    }
    return options;
  }

}
