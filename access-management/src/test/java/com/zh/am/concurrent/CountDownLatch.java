package com.zh.am.concurrent;

import com.zh.am.domain.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class CountDownLatch {
  private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
  private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

  @Test
  public void test() throws InterruptedException {
    long d1 = System.currentTimeMillis();
    List<User> list = new ArrayList<>();
    list.add(new User());
    list.add(new User());
    list.add(new User());
    list.add(new User());
    list.add(new User());
    list.add(new User());

    java.util.concurrent.CountDownLatch downLatch = new java.util.concurrent.CountDownLatch(list.size() - 1);
    list.forEach(record -> {
      String call = call(record, downLatch);
    });
    downLatch.await();
    list.forEach(System.out::println);
    System.out.println(System.currentTimeMillis() - d1);
  }

  @Test
  public void test2() throws InterruptedException {
    long d1 = System.currentTimeMillis();
    List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f");
    list.forEach(record -> {
      String call2 = call2(record);
      if (StringUtils.isNotBlank(call2))
        System.out.println(call2);
    });
    System.out.println(System.currentTimeMillis() - d1);
  }


  private String call2(String s) {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
    return s + " normal";
  }

  private String call(User user, java.util.concurrent.CountDownLatch downLatch) {
    StringBuilder builder = new StringBuilder();
    fixedThreadPool.execute(() -> {
      //do something
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
      }
      user.setId(String.valueOf(Thread.currentThread().getId()));
      //finally
      downLatch.countDown();
    });
    return builder.toString();
  }

}
