package com.functional.programming.in.java.chapter;

import com.functional.programming.in.java.chapter.ChapterSix.Primes;
import org.junit.jupiter.api.Test;

public class ChapterSixTest {

  static class PrimesTest {

    @Test
    void fromOneCountTen() {
      var actual = Primes.primes(1, 10);
      System.out.println(actual);
    }

    @Test
    void fromFiveCountHundred() {
      var actual = Primes.primes(100, 5);
      System.out.println(actual);
    }
  }

}
