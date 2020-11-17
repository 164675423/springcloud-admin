package com.zh.am.authentication;

import com.zh.common.base.context.UserContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * request销毁时移除threadLocal中的用户信息
 *
 * @author zh
 * @date 2020/9/21
 */
@Component
public class RequestListener implements ServletRequestListener {

  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    UserContext.removeLoginUser();
  }

}
