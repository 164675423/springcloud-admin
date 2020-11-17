package com.zh.common.base.context;


public class UserContext {
  private static final ThreadLocal<LoginUser> USER_CONTEXT = new ThreadLocal<>();

  private UserContext() {
  }

  public static LoginUser getLoginUser() {
    return USER_CONTEXT.get();
  }

  public static void setLoginUser(LoginUser loginUser) {
    USER_CONTEXT.set(loginUser);
  }

  public static void removeLoginUser() {
    USER_CONTEXT.remove();
  }
}
