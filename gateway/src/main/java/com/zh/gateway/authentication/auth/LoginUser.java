package com.zh.gateway.authentication.auth;

import java.util.List;

public class LoginUser {
  private String id;//用户id
  private String name;//姓名
  private String username;//用户名
  private List<String> roles;//角色

  public LoginUser(String id, String name, String username) {
    this.id = id;
    this.name = name;
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
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
}
