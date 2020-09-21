package com.zh.am.common;

import com.zh.am.authentication.LoginUser;

public class UserContext {
  private UserContext() {
  }

  private static final ThreadLocal<LoginUser> USER_CONTEXT = new ThreadLocal<>();

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
