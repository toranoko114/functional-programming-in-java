package com.functional.programming.in.java.chapter;

import static com.functional.programming.in.java.resource.chapter.six.Evaluation.eagerEvaluator;
import static com.functional.programming.in.java.resource.chapter.six.Evaluation.evaluate;
import static com.functional.programming.in.java.resource.chapter.six.Evaluation.lazyEvaluator;
import static com.functional.programming.in.java.resource.chapter.six.LazyStreams.length;

import com.functional.programming.in.java.resource.chapter.six.Holder;
import com.functional.programming.in.java.resource.chapter.six.HolderNaive;
import com.functional.programming.in.java.resource.chapter.six.LazyStreams;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 第6章 「遅延させる」ということ
 */
public class ChapterSix {

  /**
   * 6.1　初期化の遅延
   */
  public void before_1() {
    final HolderNaive holder = new HolderNaive();
    System.out.println("deferring heavy creation...");
    System.out.println(holder.
        getHeavy());
    System.out.println(holder.getHeavy());
  }

  /**
   * 6.1　初期化の遅延
   */
  public void after_1() {
    final Holder holder = new Holder();
    System.out.println(holder.getHeavy());
    System.out.println(holder.getHeavy());
  }

  /**
   * 6.2 遅延評価
   */
  public void before_2() {
    /*
      Java は論理演算の評価を遅延もしくは正規順序で行う一方、
      メソッドの引数を評価する際は先行または作用的順序で行う。
      つまり、メソッドに渡された引数はメソッドが呼び出される前にすべて評価される。
      メソッドがすべての引数を使用しない場合は、その評価に費やした時間とリソースが無駄になります。
     */
    eagerEvaluator(evaluate(1), evaluate(2));

  }

  /**
   * 6.2 遅延評価
   */
  public void after_2() {
    /*
      ラムダ式により任意の引数の評価を遅らせることが可能
      引数は lazyEvaluator()に入る前に評価されない。
      また1つ目のevaluate()でfalseであるため、falseが確定し、2つ目のevaluate()は実行されない。
     */
    lazyEvaluator(() -> evaluate(1), () -> evaluate(2));
  }

  /**
   * 6.3 Stream の遅延処理を活用
   */
  public void after_3() {
    List<String> names = Arrays.asList("Brad", "Kate", "Kim", "Jack", "Joe",
        "Mike", "Susan", "George", "Robert", "Julia", "Parker", "Benson");
    final String firstNameWith3Letters =
        names.stream()
            .filter(name -> length(name) == 3)
            .map(LazyStreams::toUpper)
            .findFirst()
            .get();
    System.out.println(firstNameWith3Letters);
  }

  // 素数判定
  public static boolean isPrime(final int number) {
    return number > 1 &&
        IntStream.rangeClosed(2, (int) Math.sqrt(number))
            .noneMatch(divisor -> number % divisor == 0);
  }

  /**
   * 6.4 無限の「遅い」コレクションを生成
   */
  public static class Primes {

    private static int primeAfter(final int number) {
      if (isPrime(number + 1)) {
        return number + 1;
      } else {
        return primeAfter(number + 1);
      }
    }

    public static List<Integer> primes(final int fromNumber, final int count) {
      return Stream.iterate(primeAfter(fromNumber - 1), Primes::primeAfter)
          .limit(count)
          .collect(Collectors.<Integer>toList());
    }
  }


}
