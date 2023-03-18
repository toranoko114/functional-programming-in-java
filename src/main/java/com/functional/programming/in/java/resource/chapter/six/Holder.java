package com.functional.programming.in.java.resource.chapter.six;

import java.util.function.Supplier;

public class Holder {

  // 間接処理を 1 レベル追加する
  // ここでは間接処理は Supplier<T>クラス を加える
  private Supplier<Heavy> heavy = this::createAndCacheHeavy;

  public Holder() {
    System.out.println("Holder created");
  }

  public Heavy getHeavy() {
    return heavy.get();
  }

  private synchronized Heavy createAndCacheHeavy() {
    class HeavyFactory implements Supplier<Heavy> {

      private final Heavy heavyInstance = new Heavy();

      public Heavy get() {
        return heavyInstance;
      }
    }
    /*
       Supplier参照の heavyを直接のサプライヤである HeavyFactoryに変換している

     */
    if (!HeavyFactory.class.isInstance(heavy)) {
      heavy = new HeavyFactory();
    }
    return heavy.get();
  }
}
