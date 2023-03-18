package com.functional.programming.in.java.resource.chapter.four;

public interface FastFly extends Fly {

  /*
      defaultメソッドの登場により、
      インターフェースが抽象クラスに進化したように見えるがそうではない
      ・抽象クラスは状態を持てるがインターフェースは持てない
      ・インターフェースは複数のインターフェースを継承（実装/implements）可能だが、抽象クラスは1つしか継承できない。
   */

  default void takeOff() {
    System.out.println("FastFly::takeOff");
  }
}
