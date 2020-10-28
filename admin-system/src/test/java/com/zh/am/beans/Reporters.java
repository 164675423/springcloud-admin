package com.zh.am.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Reporters {
  @Resource
  public List<AbstractReporter> reporters;

  @Test
  public void test() {
    reporters.forEach(r -> System.out.println(r.report("")));
  }

}
