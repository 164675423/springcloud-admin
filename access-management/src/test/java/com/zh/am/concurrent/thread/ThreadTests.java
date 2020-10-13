package com.zh.am.concurrent.thread;

import com.zh.am.domain.dao.UserMapper;
import com.zh.common.context.LoginUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ThreadTests {
  @Resource
  private UserMapper userMapper;


  @Test
  public void test() throws InterruptedException {
    List<String> list = new ArrayList<>();
    long start = System.currentTimeMillis();
    list.add(userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d").getUserName() + LocalDateTime.now().getSecond());
    list.add(userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d").getUserName() + LocalDateTime.now().getSecond());
    list.add(userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d").getUserName() + LocalDateTime.now().getSecond());
    list.add(userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d").getUserName() + LocalDateTime.now().getSecond());
    list.add(userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d").getUserName() + LocalDateTime.now().getSecond());
    list.add(userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d").getUserName() + LocalDateTime.now().getSecond());
    Thread.sleep(1200);
    System.out.println(list.toString());
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }

  @Test
  public void test1() throws InterruptedException {
    List<String> list = Collections.synchronizedList(new ArrayList<>());
    long start = System.currentTimeMillis();
    Thread thread1 = new Thread(() -> {
      LoginUser loginUser = userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d");
      list.add(loginUser.getUserName() + LocalDateTime.now().getSecond());
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread thread2 = new Thread(() -> {
      LoginUser loginUser = userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d");
      list.add(loginUser.getUserName() + LocalDateTime.now().getSecond());
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread thread3 = new Thread(() -> {
      LoginUser loginUser = userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d");
      list.add(loginUser.getUserName() + LocalDateTime.now().getSecond());
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread thread4 = new Thread(() -> {
      LoginUser loginUser = userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d");
      list.add(loginUser.getUserName() + LocalDateTime.now().getSecond());
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread thread5 = new Thread(() -> {
      LoginUser loginUser = userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d");
      list.add(loginUser.getUserName() + LocalDateTime.now().getSecond());
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread thread6 = new Thread(() -> {
      LoginUser loginUser = userMapper.getLoginUser("17fe33b8-d828-47bd-9e9e-a61fa8d5277d");
      list.add(loginUser.getUserName() + LocalDateTime.now().getSecond());
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
    thread5.start();
    thread6.start();

    thread1.join();
    thread2.join();
    thread3.join();
    thread4.join();
    thread5.join();
    thread6.join();

    System.out.println(list.toString());
    System.out.println((System.currentTimeMillis() - start) + "ms");
  }
}
