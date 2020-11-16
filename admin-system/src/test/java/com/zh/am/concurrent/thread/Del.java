package com.zh.am.concurrent.thread;

public class Del {
  public static void main(String[] args) throws InterruptedException {
    Thread thread = new Thread(() -> {
      while (true) {
        System.out.println(Thread.currentThread().getName());
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        break;
      }
    }, "子线程");
    thread.start();
    thread.join();
    System.out.println("main stop");
  }
}
