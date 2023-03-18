package com.functional.programming.in.java.resource.chapter.seven;

import java.math.BigInteger;

public class BigFactorial {

  public static BigInteger decrement(final BigInteger number) {
    return number.subtract(BigInteger.ONE);
  }

  public static BigInteger multiply(
      final BigInteger first, final BigInteger second) {
    return first.multiply(second);
  }
}