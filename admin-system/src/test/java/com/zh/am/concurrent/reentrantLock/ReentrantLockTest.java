package com.zh.am.concurrent.reentrantLock;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ReentrantLockTest {
  private static final ExecutorService fixedThreadPool;
  private static ReentrantLock lock;
  private static MyLock myLock;

  static {
    myLock = new MyLock();
    lock = new ReentrantLock(false);
    fixedThreadPool = Executors.newFixedThreadPool(20);
  }

  public static void main(String[] args) throws InterruptedException {
    //非公平锁，可能刚释放的锁立马又获取到锁。只要state=0就有获取到锁的机会
    for (int i = 1; i <= 20; i++) {
      fixedThreadPool.execute(() -> {
        myLock.lock();
        System.out.println("currentThread lock :" + Thread.currentThread().getId());
        Collection<Thread> queuedThreads = myLock.getQueuedThreads();
        List<Long> collect = queuedThreads.stream().map(r -> r.getId()).collect(Collectors.toList());
        System.out.println("队列 :" + collect.toString());
        myLock.unlock();
        System.out.println("currentThread unlock :" + Thread.currentThread().getId());

        myLock.lock();
        System.out.println("currentThread lock again:" + Thread.currentThread().getId());
        Collection<Thread> queuedThreads2 = myLock.getQueuedThreads();
        List<Long> collect2 = queuedThreads2.stream().map(r -> r.getId()).collect(Collectors.toList());
        System.out.println("队列 :" + collect2.toString());
        myLock.unlock();
        System.out.println("currentThread unlock again:" + Thread.currentThread().getId());

      });
    }

    Thread.sleep(5000);
  }

  static class MyLock extends ReentrantLock {
    @Override
    protected Collection<Thread> getQueuedThreads() {
      return super.getQueuedThreads();
    }
  }
}
