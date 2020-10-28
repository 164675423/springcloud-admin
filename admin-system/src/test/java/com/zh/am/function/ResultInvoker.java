package com.zh.am.function;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultInvoker<T> {
  private String description;
  private AfterFunc<T> afterFunc;

  public ResultInvoker description(String description) {
    this.description = description;
    return this;
  }

  public ResultInvoker afterFunc(AfterFunc afterFunc) {
    this.afterFunc = afterFunc;
    return this;
  }

  public InvokeFunc<T> build(RequestFunc<T> func) {
    System.out.println(description);
    return (p1, p2) -> DefaultExecutor.run(func, afterFunc, p1, p2);
  }
}
