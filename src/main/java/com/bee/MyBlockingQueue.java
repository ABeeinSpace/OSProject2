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
  private Semaphore addSemaphore;
  private Semaphore removeSemaphore;
  private Semaphore fullBlocker;
  private Semaphore emptyBlocker;
  private int maxNumElements;
  private Queue<T> elementsQueue;

  /**
   * @param maxNum The maximum number of elements that can be inserted into the
   *               BlockingQueue.
   */
  public MyBlockingQueue(int maxNum) {
    this.maxNumElements = maxNum;
    this.elementsQueue = new LinkedList<>();
    this.addSemaphore = new Semaphore(1);
    this.fullBlocker = new Semaphore(maxNum);
    this.removeSemaphore = new Semaphore(1);
    this.emptyBlocker = new Semaphore(0);
  }

  /**
   * @param element The element to add to the BlockingQueue. Element is generic,
   *                so any type can be inserted into the queue.
   */
  public void add(T element) {
    fullBlocker.acquireUninterruptibly();
    addSemaphore.acquireUninterruptibly();

    elementsQueue.offer(element);

    addSemaphore.release();
    emptyBlocker.release();
    // notify(); // Notify waiting threads that an element has been added to the
    // queue
  }

  /**
   * @return The element removed from the BlockingQueue.
   */
  public T remove() {
    emptyBlocker.acquireUninterruptibly();
    removeSemaphore.acquireUninterruptibly();
    T elementRemoved = elementsQueue
        .remove(); // We probably could just return here, but we'd end up
                   // having to notify() before the element is actually
                   // removed. That smeeells like a bad time to me
    removeSemaphore.release();
    fullBlocker.release();
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
