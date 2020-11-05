package com.zh.am.concurrent.thread;

/**
 * 两个线程交替打印奇数偶数
 *
 * @author zh
 * @date 2020/11/5
 */
public class OddEven {
  private static volatile int i = 1;
  private static Object object = new Object();

  public static void main(String[] args) {
    new Thread(() -> {
      while (true) {
        synchronized (object) {
          if (i % 2 != 0) {
            continue;
          }
          System.out.println(Thread.currentThread().getName() + i);
          i++;
          if (i >= 100) {
            break;
          }
          try {
            object.notify();
            object.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }, "奇数线程").start();

    new Thread(() -> {
      while (true) {
        synchronized (object) {
          if (i % 2 == 0) {
            continue;
          }
          System.out.println(Thread.currentThread().getName() + i);
          i++;
          if (i >= 100) {
            break;
          }
          try {
            object.notify();
            object.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }, "偶数线程").start();

  }
}
