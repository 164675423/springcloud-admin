package com.zh.am.mq;


import lombok.Data;

import java.util.UUID;

@Data
public class Message {
  private String key = UUID.randomUUID().toString();

}
