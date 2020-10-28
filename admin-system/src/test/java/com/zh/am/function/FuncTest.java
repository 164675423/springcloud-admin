package com.zh.am.function;

import com.zh.common.contract.ResponseBodyWrapper;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
public class FuncTest {

  @Test
  public void test1() {
    //后置函数
    AfterFunc<ResponseBodyWrapper<String>> afterFunc = (data) -> {
      System.out.println("do after,data is " + data);
      String s = Arrays.stream(data.getPayload().split("_")).collect(Collectors.joining("/"));
      return ResponseBodyWrapper.from("finally : " + s, data.getMessage());
    };
    //创建invoker实例
    ResultInvoker<ResponseBodyWrapper> resultInvoker = new ResultInvoker<ResponseBodyWrapper>()
        .afterFunc(afterFunc)
        .description("查询其他服务");

    //模拟入参
    String req = "req";

    //传入需要调用的函数
    ResponseBodyWrapper wrapper = resultInvoker.build(() -> getData(req)).invoke("p1", "p2");
    System.out.println(wrapper.toString());
  }

  @Test
  public void test2() {
    String phone = "182";
    ArrayList<String> strings = new ArrayList<>();
    strings.add("184");
    strings.add("183");
    strings.add("184");

    if (strings.stream().anyMatch(r -> r.equals(phone))) {
      System.out.println("11111");
    }
    if (strings.size() != strings.stream().distinct().count()) {
      System.out.println("22222");
    }
  }

  private void func(String param, Function<String, ResponseBodyWrapper> fun) {
    Function<String, ResponseBodyWrapper> function = (str) -> ResponseBodyWrapper.from("func is " + str, "");

    fun.apply(param);
  }

  /**
   * 模拟调用其他服务
   *
   * @param param
   * @return
   */
  private ResponseBodyWrapper<String> getData(String param) {
    String s = (int) Math.random() + "_" + param;
    return ResponseBodyWrapper.from(s, "success");
  }

}
