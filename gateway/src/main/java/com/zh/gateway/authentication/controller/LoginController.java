package com.zh.gateway.authentication.controller;

import com.zh.gateway.authentication.auth.DefaultTokenService;
import com.zh.gateway.authentication.auth.LoginUser;
import com.zh.gateway.authentication.auth.ResponseBodyWrapper;
import com.zh.gateway.authentication.dto.LoginDto;
import com.zh.gateway.authentication.entity.User;
import com.zh.gateway.authentication.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录、注销操作
 *
 * @author zh
 * @date 2020/2/18
 */
@RestController
@RequestMapping(value = "api/v1")
public class LoginController {
  private DefaultTokenService tokenService;
  @Value("${security.oauth2.resource.jwt.expire-minutes}")
  private Long expireMinutes;
  private IAuthenticationService authenticationService;
  private PasswordEncoder passwordEncoder;

  public LoginController(DefaultTokenService tokenService, IAuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
    this.tokenService = tokenService;
    this.authenticationService = authenticationService;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * 根据用户信息登录,登录成功返回包含jwt的cookie
   *
   * @param loginDto
   * @return
   */
  @PostMapping(value = "login")
  public ResponseEntity login(@RequestBody @Validated LoginDto loginDto) {
    User user = authenticationService.login(loginDto.getUsername().toLowerCase());
    if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseBodyWrapper.from("login failed 用户名或密码错误"));
    }
    List<String> roles = authenticationService.getRoles(user.getId());
    LoginUser loginUser = new LoginUser(user.getId(), user.getName(), user.getUsername());
    loginUser.setRoles(roles);
    //生成jwt
    String jwt = tokenService.createJwt(user.getId(), expireMinutes);
    ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.SET_COOKIE, tokenService.getAuthCookie(jwt, 60 * expireMinutes).toString())
        .body(ResponseBodyWrapper.from("login success", loginUser));
    return responseEntity;
  }

  /**
   * 注销并返回空的cookie
   *
   * @return
   */
  @RequestMapping(value = "logout")
  public ResponseEntity logout() {
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.SET_COOKIE, tokenService.getAuthCookie("", -1).toString())
        .build();
  }

  @RequestMapping(value = "hello")
  public ResponseEntity hello() {
    return ResponseEntity.ok("hello");
  }

}
