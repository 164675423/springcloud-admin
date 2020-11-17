package com.zh.am;

import cn.hutool.core.util.IdUtil;
import com.zh.am.domain.entity.Api;
import com.zh.am.service.ApiService;
import com.zh.am.service.RedisService;
import com.zh.common.redis.util.RedissonLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class AccessManagementApplicationTests {
  private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);

  @Autowired
  private ApiService apiService;

  @Autowired
  private RedissonLockUtil redissonLockUtil;

  @Autowired
  private RedissonClient redissonClient;

  @Autowired
  private RedisService redisService;

  @Test
  public void testInsert() throws InterruptedException {

  }

  @Test
  public void test22222() throws NoSuchAlgorithmException {
    String a = UUID.randomUUID().toString();
    System.out.println(a);
    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    messageDigest.update(a.getBytes());

    byte[] digest = messageDigest.digest();
    String md5 = new BigInteger(1, digest).toString(16);
    System.out.println(md5);

    String s = DigestUtils.md5DigestAsHex(a.getBytes());
    System.out.println(s);
  }

  @Slf4j
  public static class ThreadA implements Runnable {
    private java.util.concurrent.CountDownLatch countDownLatch;
    private ApiService apiService;

    public ThreadA(ApiService apiService, java.util.concurrent.CountDownLatch countDownLatch) {
      this.countDownLatch = countDownLatch;
      this.apiService = apiService;
    }

    @Override
    public void run() {
      ArrayList<Api> apis = new ArrayList<>();
      for (int j = 1; j <= 1000; j++) {
        Api api = new Api();
        api.setId(IdUtil.randomUUID());
        api.setName(api.getId().concat("测试api"));
        api.setHttpMethod("GET");
        api.setPathPattern("*");
        apis.add(api);
      }
      apiService.saveBatch(apis);
      log.info("insert 1000");
      countDownLatch.countDown();
    }
  }

}
