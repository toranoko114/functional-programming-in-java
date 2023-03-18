package com.functional.programming.in.java.resource.chapter.five;

import static com.functional.programming.in.java.resource.chapter.five.Locker.runLocked;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Locking {

  Lock lock = new ReentrantLock(); // またはモックアップ

  protected void setLock(final Lock mock) {
    lock = mock;
  }

  // 冗長でエラーが発生しやすく、メンテナンスが面倒
  public void doOp1() {
    lock.lock();
    try {
      // ... 必要な処理 ...
    } finally {
      lock.unlock();
    }
  }

  public void doOp2() {
    runLocked(lock, () -> {/*... 重要な処理 ... */});
  }

  public void doOp3() {
    runLocked(lock, () -> {/*... 重要な処理 ... */});
  }

  public void doOp4() {
    runLocked(lock, () -> {/*... 重要な処理 ... */});
  }

}
