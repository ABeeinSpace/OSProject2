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

  synchronized public void add(T element) {
    while (getNumElements() == maxNumElements) {
      try {
        wait();
      } catch (Exception e) {
        System.out.println("Thread is too impatient!!");
      }
    }
    semaphore.tryAcquire();
    elementsQueue.offer(element);
    semaphore.release();
    notify(); // Notify waiting threads that an element has been added to the queue 
  }

  synchronized public T remove() {
    while (elementsQueue.size() == 0) {
      try {
        wait();
      } catch (Exception e) {
        System.out.println("Thread is too impatient!!");
      }
    }
    semaphore.tryAcquire();
    T elementRemoved = elementsQueue.remove();
    semaphore.release();
    notify();
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
