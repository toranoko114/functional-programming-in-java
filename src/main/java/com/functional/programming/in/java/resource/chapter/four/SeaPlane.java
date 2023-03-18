package com.functional.programming.in.java.resource.chapter.four;

public class SeaPlane extends Vehicle implements FastFly, Sail {

  private int altitude;

  // cruise()を定義しないとdefault実装が衝突してコンパイルエラーになる
  // FastFly（Flyから継承）と Sailの各インタフェースがcruise()を持っているため
  public void cruise() {
    System.out.print("SeaPlane::cruise currently cruise like: ");
    if (altitude > 0) {
      // superは必要
      // インタフェースの defaultメソッドを参照しようとしていることを明示的に示すため
      // superを使用しない場合は staticメソッドが参照される
      FastFly.super.cruise();
    } else {
      Sail.super.cruise();
    }
  }
}
