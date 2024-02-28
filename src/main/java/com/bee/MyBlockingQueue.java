package com.bee;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/*
 *
 * Models a thread-safe Blocking Queue
 *
 * @author Aidan Border
 * @author Quade Leonard
 *
 */
public class MyBlockingQueue<T> {
  private static Semaphore semaphore;
  private int maxNumElements;
  private Queue<T> elementsQueue;

  /**
   * @param maxNum The maximum number of elements that can be inserted into the BlockingQueue.
   */
  public MyBlockingQueue(int maxNum) {
    this.maxNumElements = maxNum;
    this.elementsQueue = new LinkedList<>();
    semaphore = new Semaphore(100000); // This is just an arbitrarily high number. I increased it until things started to break, then reduced until they stopped breaking. 
  }

  /**
   * @param element The element to add to the BlockingQueue. Element is generic, so any type can be inserted into the queue.
   */
  synchronized public void add(T element) {
    try {
      semaphore.acquire();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    while (getNumElements() == maxNumElements) {
      try {
        wait();
      } catch (Exception e) {
        System.out.println("Thread is too impatient!!");
      }
    }

    elementsQueue.offer(element);
    semaphore.release();
    notify(); // Notify waiting threads that an element has been added to the
              // queue
  }

  /**
   * @return The element removed from the BlockingQueue.
   */
  //TODO: Fix the race/deadlock in this function
  synchronized public T remove() {
    while (elementsQueue.isEmpty()) {
      try {
        wait();
      } catch (Exception e) {
        System.out.println("Thread is too impatient!!");
      }
    }
    try {
      semaphore.acquire();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    T elementRemoved = elementsQueue.remove();
    semaphore.release();
    notify(); // Notify the next waiting thread that there is space in the queue
    return elementRemoved;
  }

  /**
   * @param N/A
   * @return The number of elements in the BlockingQueue.
   */
  public int getNumElements() {
    return elementsQueue.size();
  }

  /**
   * @param N/A
   * @return The number of free spaces left in the BlockingQueue.
   */
  public int getFreeSpace() {
    return (maxNumElements - elementsQueue.size());
  }

  /**
   * @param N/A
   * @return A string representation of the underlying linked list.
   *
   */
  @Override
  public String toString() {
    return elementsQueue.toString();
  }
}
