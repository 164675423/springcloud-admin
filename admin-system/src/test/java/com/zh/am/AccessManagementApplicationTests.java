package com.zh.am;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zh.am.domain.dto.page.GetPageOutput;
import com.zh.am.domain.dto.role.OperationDto;
import com.zh.am.mq.producer.IUserProducer;
import com.zh.am.service.IRedisService;
import com.zh.common.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
public class AccessManagementApplicationTests {
  private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);
  @Autowired
  private StringRedisTemplate stringRedisTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private IRedisService redisService;
  @Autowired
  private IUserProducer userProducer;

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

  @Test
  public void test111() {
    String a = "/a.png";
    boolean b3 = a.matches("(^//.|^/|^[a-zA-Z])?:?/.+(/$)?");

    boolean b = a.matches("(^//.|^/)/.+(/$)?");
    boolean b2 = a.matches("^/?/.+(/$)?");

    System.out.println(b);
    System.out.println(b2);

    String ad = "fast://group/a.jpg";
    System.out.println(ad.replace("fast://", ""));
  }


  @Test
  public void testKafka() throws InterruptedException {
    BigDecimal money = BigDecimal.valueOf(19440);
    BigDecimal lixi = BigDecimal.valueOf(27.6);
    BigDecimal divide = lixi.divide(money, 5, BigDecimal.ROUND_HALF_DOWN);

    BigDecimal money2 = BigDecimal.valueOf(6800);
    BigDecimal value = BigDecimal.valueOf(0);

    BigDecimal month = money2.divide(BigDecimal.valueOf(24), 5, BigDecimal.ROUND_HALF_DOWN);
    for (int i = 1; i <= 24; i++) {
      BigDecimal subtract = money2.subtract(month);
      value = value.add(money2.multiply(divide));
      money2 = subtract;
      log.info("第{}月,还{},本金剩{},利息总额{}", i, month, money2, value);
    }
  }

  @Test
  public void stringRedisTemplate() throws IOException {
    //reference object
    GetPageOutput getPageOutput = new GetPageOutput();
    getPageOutput.setName("page1");
    OperationDto operationDto = new OperationDto();
    operationDto.setCode("add");
    getPageOutput.setOperations(Arrays.asList(operationDto));
    redisService.set("reference object", JacksonUtils.parse(getPageOutput), 60 * 10);
    String s3 = redisService.get("reference object");
    GetPageOutput output = JacksonUtils.parse(s3, GetPageOutput.class);
    //list
    List<User> users = new ArrayList<>();
    users.add(new User("一", "aph", 20));
    users.add(new User("二", "bob", 10));
    users.add(new User("二", "cold", 40));
    users.add(new User("三", "fri", 22));
    redisService.set("users", JacksonUtils.parse(users), 60 * 10);
    String s2 = redisService.get("users");
    List<User> userList = JacksonUtils.parseList(s2, User.class);
    userList.forEach(System.out::println);
  }

  @Test
  public void lambda() throws InterruptedException {
    List<User> users = new ArrayList<>();
    users.add(new User("一", "aph", 20));
    users.add(new User("二", "bob", 10));
    users.add(new User("二", "cold", 40));
    users.add(new User("三", "fri", 22));
    Map<String, List<User>> group = users.stream().collect(Collectors.groupingBy(User::getGrade));
    System.out.println(group);
    List<User> grade2 = group.getOrDefault("is", new ArrayList<>());
    System.out.println(grade2);
    String us = users.stream().reduce(0, (a, b) -> {
      System.out.println("a:" + a + " b:" + b.getAge());
      return a + b.getAge();
    }, (a, b) -> null).toString();
    System.out.println(us);
    double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
    System.out.println(minValue);
  }

  static class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private String grade;
    private String name;
    private Integer age;

    public User() {
    }

    public User(String name, Integer age) {
      this.name = name;
      this.age = age;
    }

    public User(String grade, String name, Integer age) {
      this.grade = grade;
      this.name = name;
      this.age = age;
    }

    public String getGrade() {
      return grade;
    }

    public void setGrade(String grade) {
      this.grade = grade;
    }

    @Override
    public String toString() {
      return "user{" +
          "grade='" + grade + '\'' +
          ", name='" + name + '\'' +
          ", age=" + age +
          '}';
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Integer getAge() {
      return age;
    }

    public void setAge(Integer age) {
      this.age = age;
    }
  }
}
