package com.zh.am.authentication;

import com.zh.am.aop.annotation.AllowAnonymous;
import com.zh.am.domain.dao.UserMapper;
import com.zh.am.util.ClassUtils;
import com.zh.common.context.LoginUser;
import com.zh.common.context.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 身份认证过滤器,获取http header中的用户id,将用户信息保存至requestAttribute中
 *
 * @author zh
 * @date 2020/1/18
 */
@WebFilter(urlPatterns = "/api/*")
public class AuthenticationFilter extends OncePerRequestFilter {
  private final UserMapper userMapper;
  private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
  private final String header = "X-USER-ID";

  public AuthenticationFilter(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    String userId = httpServletRequest.getHeader(header);
    logger.info("{} {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
    logger.trace("authentication filter -> user: {}, ", userId);
    if (StringUtils.isNotBlank(userId)) {
      LoginUser loginUser = userMapper.getLoginUser(userId);
      if (loginUser == null) {
        throw new IllegalStateException("未能获取到有效的用户信息，请退出重新登录");
      } else {
        httpServletRequest.setAttribute("user", loginUser);
        //存放至ThreadLocal中
        UserContext.setLoginUser(loginUser);
      }
    } else {
      throw new IllegalStateException("未能获取到有效的用户信息，请退出重新登录");
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private boolean allowAnonymous() {
    List<Class<?>> classList = ClassUtils.getClassListByAnnotation("com.zh.am", AllowAnonymous.class);

    return true;
  }
}
