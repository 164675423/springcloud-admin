package com.zh.am.concurrent.thread;

/**
 * 两个线程交替打印奇数偶数
 *
 * @author zh
 * @date 2020/11/5
 */
public class OddEven2 {
  private static volatile boolean a = true;
  private static volatile boolean b = true;
  //两个线程交替执行，不用考虑原子性
  private static volatile int i = 1;

  public static void main(String[] args) throws InterruptedException {
    a = false;
    Thread threadA = new Thread(() -> {
      while (true) {
        while (a) {
        }
        System.out.println(Thread.currentThread().getName() + i);
        i++;
        if (i >= 100) {
          break;
        }
        a = true;
        b = false;
      }
    }, "奇数线程");
    threadA.start();

    Thread threadB = new Thread(() -> {
      while (true) {
        while (b) {
        }
        System.out.println(Thread.currentThread().getName() + i);
        i++;
        if (i >= 100) {
          break;
        }
        b = true;
        a = false;
      }
    }, "偶数线程");
    threadB.start();

    threadA.join();
    threadB.join();
  }

}
