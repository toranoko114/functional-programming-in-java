package com.functional.programming.in.java.chapter;

import static com.functional.programming.in.java.resource.chapter.seven.BigFactorial.decrement;
import static com.functional.programming.in.java.resource.chapter.seven.BigFactorial.multiply;
import static com.functional.programming.in.java.resource.chapter.seven.TailCalls.call;
import static com.functional.programming.in.java.resource.chapter.seven.TailCalls.done;

import com.functional.programming.in.java.resource.chapter.seven.RodCutterBasic;
import com.functional.programming.in.java.resource.chapter.seven.TailCall;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

/**
 * 第7章 再帰の最適化
 */
public class ChapterSeven {

  // 最適化前
  static class Factorial {

    // 大きい数字を与えるとスタックオーバーフローになる
    // 再帰の完了を待つ間、部分的な計算結果をすべて保存しているため
    public static int factorialRec(final int number) {
      if (number == 1) {
        return number;
      } else {
        return number * factorialRec(number - 1);
      }
    }
  }

  /**
   * 7.1 末尾呼び出し最適化（tail-call optimization、TCO）を使う
   * <p>
   * ＝ 最後の処理が自身の呼び出しとなるような再帰呼び出し
   * <p>
   * 通常の再帰は自身への呼び出しの後にその結果を使って処理を続ける
   */
  private static TailCall<Integer> factorialTailRec(
      final int factorial, final int number) {
    if (number == 1) {
      return done(factorial);
    } else {
      return call(() -> factorialTailRec(factorial * number, number - 1));
    }
    /*
      2つの引数を与えないといけなかったり、
      最初の引数に1を与えないと動作しない
     */
  }

  // 間接レイヤーを1つ加える
  public static int factorial(final int number) {
    return factorialTailRec(1, number).invoke();
  }

  // これでもまだ算術オーバーフローが発生するため
  // intをBigIntegerに変更する
  private static TailCall<BigInteger> factorialTailRec(
      final BigInteger factorial, final BigInteger number) {
    if (number.equals(BigInteger.ONE)) {
      return done(factorial);
    } else {
      return call(() ->
          factorialTailRec(multiply(factorial, number), decrement(number)));
    }
  }

  public static BigInteger factorial(final BigInteger number) {
    return factorialTailRec(BigInteger.ONE, number).invoke();
  }
}
