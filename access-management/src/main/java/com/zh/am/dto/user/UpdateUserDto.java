package com.zh.am.dto.user;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserDto {
  @NotNull
  private String name;
  private List<String> roles = new ArrayList<>();

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
