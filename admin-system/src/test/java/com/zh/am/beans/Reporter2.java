package com.zh.am.beans;

import org.springframework.stereotype.Component;

@Component
public class Reporter2 extends AbstractReporter {
  @Override
  protected String report(String msg) {
    return "report 2";
  }
}
