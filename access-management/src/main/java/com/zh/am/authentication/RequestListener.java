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
  private final Logger logger = LoggerFactory.getLogger(RequestListener.class);

  @Override
  public void requestDestroyed(ServletRequestEvent sre) {
    HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
    logger.info("当前线程 --- [{}] --- 销毁请求：{} 移除loginUser", Thread.currentThread().getId(), request.getRequestURI());
    UserContext.removeLoginUser();
  }

}
