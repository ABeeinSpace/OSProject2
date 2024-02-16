package com.bee;

import java.util.*;
import java.util.concurrent.Semaphore;
/*
 *
 * Models a thread-safe Blocking Queue
 *
 * @author Aidan Border
 * @author Quade
 *
 */
public class MyBlockingQueue<T> {
  Semaphore semaphore;
  int maxNumElements;
  Queue<T> elementsQueue; 

  public MyBlockingQueue(int maxNum) {
    int maxNumElements = maxNum;
    elementsQueue = new LinkedList<>();
    semaphore = new Semaphore(maxNum);

  }

  public synchronized void add(T element) {
    
  }

  public synchronized T remove() {
    
  }

  @Override
  public String toString() {
    return "MyBlockingQueue []";
  }

}
