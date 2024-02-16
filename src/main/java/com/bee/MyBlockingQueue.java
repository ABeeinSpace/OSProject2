package com.bee;

import java.util.concurrent.Semaphore;

public class MyBlockingQueue<T> {
  Semaphore semaphore;

  public MyBlockingQueue(Semaphore semaphore) {
    this.semaphore = semaphore;
  }

  public void add(Object object) {
    
  }

  public void remove(Object object) {

  }


}
