package com.zh.gateway.authentication.service;

import com.zh.gateway.authentication.entity.User;

import java.util.List;

public interface AuthenticationService {
  User login(String username);

  List<String> getRoles(String userId);
}
