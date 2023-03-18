package com.functional.programming.in.java.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.functional.programming.in.java.resource.chapter.four.CalculateNAV;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class CalculateNAVTest {

  @Test
  public void computeStockWorth() {
    final CalculateNAV calculateNAV =
        new CalculateNAV(ticker -> new BigDecimal("6.01"));
    BigDecimal expected = new BigDecimal("6010.00");
    assertEquals(0,
        calculateNAV.computeStockWorth("GOOG", 1000).compareTo(expected),
        0.001);
  }
}

