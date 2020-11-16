package com.zh.am.concurrent.thread;


import java.util.concurrent.atomic.AtomicInteger;

public class Add {
  private static volatile AtomicInteger i = new AtomicInteger(0);
  private static volatile AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {
    final Object object = new Object();
    Thread thread1 = new Thread(() -> {
      while (count.intValue() <= 100) {
        synchronized (object) {
          i.incrementAndGet();
          count.incrementAndGet();
          System.out.println(Thread.currentThread().getName() + i);
          try {
            object.notify();
            object.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }, "加数线程1---->");

    Thread thread2 = new Thread(() -> {
      while (count.intValue() <= 100) {
        synchronized (object) {
          i.incrementAndGet();
          count.incrementAndGet();
          System.out.println(Thread.currentThread().getName() + i);
          try {
            object.notify();
            object.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }, "加数线程2---->");

    Thread thread3 = new Thread(() -> {
      while (count.intValue() <= 100) {
        synchronized (object) {
          i.decrementAndGet();
          count.incrementAndGet();
          System.out.println(Thread.currentThread().getName() + i);
          try {
            object.notify();
            object.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }, "减数线程1====>");

    Thread thread4 = new Thread(() -> {
      while (count.intValue() <= 100) {
        synchronized (object) {
          i.decrementAndGet();
          count.incrementAndGet();
          System.out.println(Thread.currentThread().getName() + i);
          try {
            object.notify();
            object.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }, "减数线程2====>");
    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
    System.out.println("main stop");
  }

}
