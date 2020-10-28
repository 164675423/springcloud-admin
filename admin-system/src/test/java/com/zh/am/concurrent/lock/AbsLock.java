package com.zh.am.concurrent.lock;

import java.util.concurrent.TimeUnit;

public interface AbsLock {

  boolean tryLock(String s, long timeout, TimeUnit timeUnit);

  boolean lock(String s);

  boolean unlock(String s);

}
