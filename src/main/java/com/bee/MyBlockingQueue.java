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
  private Semaphore semaphore;
  private int maxNumElements;
  private Queue<T> elementsQueue; 

  public MyBlockingQueue(int maxNum) {
    this.maxNumElements = maxNum;
    this.elementsQueue = new LinkedList<>();
    this.semaphore = new Semaphore(maxNum);
  }

  public synchronized void add(T element) {
    elementsQueue.add(element); 
  }

  public synchronized T remove() {
    T elementRemoved = elementsQueue.peek();
    elementsQueue.remove();
    return elementRemoved;
  }

  public int getNumElements() {
    return elementsQueue.size();
  }

  public int getFreeSpace() {
    return (maxNumElements - elementsQueue.size());
  }

  @Override
  public String toString() {
    return elementsQueue.toString();
  }

}
