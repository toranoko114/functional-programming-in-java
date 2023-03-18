package com.functional.programming.in.java.chapter;

import static com.functional.programming.in.java.chapter.ChapterSeven.factorial;

import com.functional.programming.in.java.chapter.ChapterSeven.Factorial;

import com.functional.programming.in.java.resource.chapter.seven.RodCutterBasic;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChapterSevenTest {

  @DisplayName("再帰メソッド最適化前テスト")
  static class FactorialTest {

    @Test
    void 再帰メソッド最適化前テスト_正常系() {
      var actual = Factorial.factorialRec(5);
      System.out.println(actual);
    }

    @Test
    void 再帰メソッド最適化前テスト_スタックオーバーフロー() {
      try {
        var actual = Factorial.factorialRec(200000);
        System.out.println(actual);
      } catch (StackOverflowError e) {
        System.out.println(e);
      }
    }
  }

/*
  @Test
  void 再帰メソッド最適化テスト_正常系() {
    var actual = factorialTailRec(1, 5).invoke();
    System.out.println(actual);
  }

  @Test
  void 再帰メソッド最適化テスト_算術オーバーフロー() {
    var actual = factorialTailRec(1, 20000).invoke();
    System.out.println(actual);
  }
*/

  @Test
  void 再帰メソッド最適化テスト_STEP1() {
    var actual = factorial(5);
    System.out.println(actual);
  }

  @Test
  void 再帰メソッド最適化テスト_STEP1_算術オーバーフロー() {
    var actual = factorial(200000);
    System.out.println(actual);
  }

  @Test
  void 再帰メソッド最適化テスト_STEP2() {
    var actual = factorial(new BigInteger("5"));
    System.out.println(actual);
  }

  @Test
  void 再帰メソッド最適化テスト_STEP2_算術オーバーフローなし() {
    var actual = factorial(new BigInteger("20000"));
    System.out.println(actual);
  }

  @DisplayName("最大利益を算出する")
  static class RodCutterBasicTest {

    final List<Integer> priceValues =
        Arrays.asList(2, 1, 1, 2, 2, 2, 1, 8, 9, 15);
    final RodCutterBasic rodCutter = new RodCutterBasic(priceValues);

    @Test
    void 最適化前_計算重複あり() {
      var actual = rodCutter.maxProfitBefore(5);
      System.out.println(actual);
    }

    @Test
    void 最適化_計算重複なし() {
      var actual = rodCutter.maxProfit(5);
      System.out.println(actual);
    }

  }


}
