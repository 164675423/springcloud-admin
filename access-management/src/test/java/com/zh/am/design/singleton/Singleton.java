package com.zh.am.design.singleton;

public class Singleton {
  private static volatile Singleton singleton;

  private Singleton() {
  }

  public static Singleton getInstance() {
    if (singleton == null) {
      synchronized (Singleton.class) {
        if (singleton == null) {
          singleton = new Singleton();
        }
      }
    }
    return singleton;
  }

  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      new Thread(() -> {
        System.out.println(Singleton.getInstance().hashCode());
      }, "thread" + i).start();
    }
  }
}
