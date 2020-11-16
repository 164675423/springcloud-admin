package com.zh.gateway.authentication.service.impl;

import com.zh.gateway.authentication.dao.RoleMapper;
import com.zh.gateway.authentication.dao.UserMapper;
import com.zh.gateway.authentication.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements com.zh.gateway.authentication.service.AuthenticationService {
  private final UserMapper userMapper;
  private final RoleMapper roleMapper;

  public AuthenticationServiceImpl(UserMapper userMapper, RoleMapper roleMapper) {
    this.userMapper = userMapper;
    this.roleMapper = roleMapper;
  }

  @Override
  public User login(String username) {
    User user = userMapper.login(username);
    return user;
  }

  @Override
  public List<String> getRoles(String userId) {
    return roleMapper.getRolesByUserId(userId);
  }
}
