package com.zh.am.mq.producer;

import com.zh.am.entity.User;

public interface IUserProducer {
  void addOrUpdateUser(User user);
}
