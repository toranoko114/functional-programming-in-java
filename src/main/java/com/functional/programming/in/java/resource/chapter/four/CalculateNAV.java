package com.functional.programming.in.java.resource.chapter.four;

import java.math.BigDecimal;
import java.util.function.Function;

public class CalculateNAV {

  private final Function<String, BigDecimal> priceFinder;

  public CalculateNAV(Function<String, BigDecimal> aPriceFinder) {
    priceFinder = aPriceFinder;
  }

  public BigDecimal computeStockWorth(
      final String ticker, final int shares) {
    return priceFinder.apply(ticker).multiply(BigDecimal.valueOf(shares));
  }

}
