package com.zh.am.authentication;

/**
 * 当前用户信息
 *
 * @author zh
 * @date 2020/1/3 16:19
 */
public class LoginUser {
  private final String id;//用户id
  private final String name;//姓名
  private final String userName;//用户名

  /**
   * 构造当前用户实例
   *
   * @param id
   * @param name
   * @param userName
   */
  public LoginUser(String id, String name, String userName) {
    this.id = id;
    this.name = name;
    this.userName = userName;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUserName() {
    return userName;
  }

}
