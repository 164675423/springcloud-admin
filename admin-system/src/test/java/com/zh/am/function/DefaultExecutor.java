package com.zh.am.function;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultExecutor {
  public static <T> T run(RequestFunc<T> runFunc, AfterFunc<T> afterFunc, String param1, String param2) {
    T result = runFunc.run();
    T after = afterFunc.doAfter(result);
    System.out.println("param1+param2 is " + param1.concat(param2));
    return after;
  }

}
