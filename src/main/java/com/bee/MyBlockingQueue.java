package com.bee; // <-- REMOVE BEFORE SUBMISSION

import java.util.*;
import java.util.concurrent.*;

/*
 *
 * Models a thread-safe Blocking Queue
 *
 * @author Aidan Border
 * @author Quade Leonard
 *
 */
public class MyBlockingQueue<T> {
  private static Semaphore outerSemaphore;
  private static Semaphore innerSemaphore;
  private int maxNumElements;
  private Queue<T> elementsQueue;

  /**
   * @param maxNum The maximum number of elements that can be inserted into the
   *               BlockingQueue.
   */
  public MyBlockingQueue(int maxNum) {
    this.maxNumElements = maxNum;
    this.elementsQueue = new LinkedList<>();
    innerSemaphore = new Semaphore(1);

    outerSemaphore = new Semaphore(1);
  }

  /**
   * @param element The element to add to the BlockingQueue. Element is generic,
   *                so any type can be inserted into the queue.
   */
  public void add(T element) {
    try {
      outerSemaphore.acquire();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    while (getNumElements() == maxNumElements) {
      try {
        innerSemaphore.acquire();
      } catch (Exception e) {
        System.out.println("Thread is too impatient!!");
      }
    }
    innerSemaphore.release();

    elementsQueue.offer(element);
    outerSemaphore.release();
    // notify(); // Notify waiting threads that an element has been added to the
    // queue
  }

  /**
   * @return The element removed from the BlockingQueue.
   */
  public T remove() {
    try {
      outerSemaphore.acquire();
    } catch (InterruptedException e) { // Catch a thrown InterruptedException.
                                       // Required for the call to acquire().
      System.err.println("Thread is too impatient!! Crashing now... ");
    }

    while (elementsQueue.isEmpty()) { // if the queue is empty, we need to block
                                      // until there are elements to remove.
      try {
        innerSemaphore.acquire(); // Wait until we're notified that there are
                                  // elements in the queue we can remove.
      } catch (InterruptedException e) { // Catch a thrown InterruptedException.
                                         // Required for the call to wait().
        System.err.println("Thread is too impatient!! Crashing now... ");
      }
    }

    T elementRemoved = elementsQueue
        .remove(); // We probably could just return here, but we'd end up
                   // having to notify() before the element is actually
                   // removed. That smeeells like a bad time to me

    innerSemaphore.release();
    outerSemaphore.release();
    // notify(); // Notify the next waiting thread that there is space in the
    // queue.
    return elementRemoved; // Return the element we just removed.
  }

  /**
   * @param N/A
   * @return The number of elements in the BlockingQueue.
   */
  public int getNumElements() {
    return elementsQueue.size(); // Go to the underlying Queue, grab its size,
                                 // and simply return that.
  }

  /**
   * @param N/A
   * @return The number of free spaces left in the BlockingQueue.
   */
  public int getFreeSpace() {
    return (maxNumElements -
        elementsQueue
            .size()); // Our free space is the maximum number of elements we can
                      // hold subtracted from what we're currently holding.
  }

  /**
   * @param N/A
   * @return A string representation of the underlying linked list.
   */
  @Override
  public String toString() {
    return elementsQueue.toString();
  }
}
