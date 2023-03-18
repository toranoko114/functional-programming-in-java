package com.functional.programming.in.java.resource.chapter.seven;

import static com.functional.programming.in.java.resource.chapter.seven.Memoizer.callMemoized;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 7.2 メモ化でスピードアップ
 */
public class RodCutterBasic {

  final List<Integer> prices;

  public RodCutterBasic(final List<Integer> pricesForLength) {
    prices = pricesForLength;
  }

  // 最適化前
  public int maxProfitBefore(final int length) {
    int profit = (length <= prices.size()) ? prices.get(length - 1) : 0;
    for (int i = 1; i < length; i++) {
      int priceWhenCut = maxProfitBefore(i) + maxProfitBefore(length - i);
      if (profit < priceWhenCut) {
        profit = priceWhenCut;
      }
    }
    return profit;
  }

  public int maxProfit(final int rodLength) {
    BiFunction<Function<Integer, Integer>, Integer, Integer> compute =
        (func, length) -> {
          int profit = (length <= prices.size()) ? prices.get(length - 1) : 0;
          for (int i = 1; i < length; i++) {
            int priceWhenCut = func.apply(i) + func.apply(length - i);
            if (profit < priceWhenCut) {
              profit = priceWhenCut;
            }
          }
          return profit;
        };
    return callMemoized(compute, rodLength);
  }
}