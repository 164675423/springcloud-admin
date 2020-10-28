package com.zh.am.mq.producer;

import com.zh.am.domain.entity.User;

public interface IUserProducer {
  void addOrUpdateUser(User user);
}
