package com.zh.am;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zh.am.domain.dto.page.GetPageOutput;
import com.zh.am.domain.dto.role.OperationDto;
import com.zh.am.mq.producer.IUserProducer;
import com.zh.am.service.IRedisService;
import com.zh.am.util.JacksonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccessManagementApplicationTests {
  @Autowired
  private StringRedisTemplate stringRedisTemplate;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private IRedisService redisService;
  @Autowired
  private IUserProducer userProducer;

  @Test
  public void kafka() {
    userProducer.addOrUpdateUser(new com.zh.am.domain.entity.User());
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
