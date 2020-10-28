package com.zh.am.concurrent.lock;

import java.util.concurrent.TimeUnit;

public class Lock1 implements AbsLock {
  @Override
  public boolean tryLock(String s, long timeout, TimeUnit timeUnit) {
    return false;
  }

  @Override
  public boolean lock(String s) {
    return false;
  }

  @Override
  public boolean unlock(String s) {
    return false;
  }
}
