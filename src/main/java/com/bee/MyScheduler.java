package com.bee;

import java.util.concurrent.Semaphore;

public class MyScheduler {
  Semaphore semaphore;

  public MyScheduler(Semaphore semaphore) {
    this.semaphore = semaphore;
  }


}
