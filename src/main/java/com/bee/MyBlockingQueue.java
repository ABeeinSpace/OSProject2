package com.bee;

import java.util.concurrent.Semaphore;

public class MyBlockingQueue {
  Semaphore semaphore;

  public MyBlockingQueue(Semaphore semaphore) {
    this.semaphore = semaphore;
  }


}
