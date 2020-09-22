package com.zh.am.authentication;

import com.zh.am.common.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

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
