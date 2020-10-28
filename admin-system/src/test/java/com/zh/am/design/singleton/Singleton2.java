package com.zh.am.design.singleton;

/**
 * 基于枚举类的单例
 *
 * @author zh
 * @date 2020/10/15
 */
public class Singleton2 {

  private static Singleton2 getSingle() {
    return SingleEnum.INSTANCE.getInstance();
  }

  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      System.out.println(Singleton2.getSingle().hashCode());
    }
  }

  enum SingleEnum {
    INSTANCE;
    private Singleton2 singleton2;

    SingleEnum() {
      singleton2 = new Singleton2();
    }

    public Singleton2 getInstance() {
      return singleton2;
    }
  }

}
