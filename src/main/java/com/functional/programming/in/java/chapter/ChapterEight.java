package com.functional.programming.in.java.chapter;

import static java.util.stream.Collectors.joining;

import com.functional.programming.in.java.resource.chapter.eight.StockInfo;
import com.functional.programming.in.java.resource.chapter.eight.StockUtil;
import com.functional.programming.in.java.resource.chapter.four.YahooFinance;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 第8章 ラムダ式で合成
 */
public class ChapterEight {

  public static final List<String> symbols = Arrays.asList(
      "AMD", "HPQ", "IBM", "TXN", "VMW", "XRX", "AAPL", "ADBE",
      "AMZN", "CRAY", "CSCO", "SNE", "GOOG", "INTC", "INTU",
      "MSFT", "ORCL", "TIBX", "VRSN", "YHOO");

  /**
   * 8.1 関数合成の利用
   */
  public void after_1() {
    final BigDecimal HUNDRED = new BigDecimal("100");
    System.out.println("Stocks priced over $100 are " +
        symbols
            .stream()
            .filter(
                symbol -> YahooFinance.getPrice(symbol).compareTo(HUNDRED) > 0)
            .sorted()
            .collect(joining(", ")));
  }

  /**
   * 8.2 MapReduce の使用
   */
  public void before_2() {
    final List<StockInfo> stocks = new ArrayList<>();
    for (String symbol : symbols) {
      stocks.add(StockUtil.getPrice(symbol));
    }
    final List<StockInfo> stocksPricedUnder500 = new ArrayList<>();
    final Predicate<StockInfo> isPriceLessThan500 = StockUtil.isPriceLessThan(500);
    for (StockInfo stock : stocks) {
      if (isPriceLessThan500.test(stock)) {
        stocksPricedUnder500.add(stock);
      }
    }
    StockInfo highPriced = new StockInfo("", BigDecimal.ZERO);
    for (StockInfo stock : stocksPricedUnder500) {
      highPriced = StockUtil.pickHigh(highPriced, stock);
    }
    System.out.println("High priced under $500 is " + highPriced);
  }

  /**
   * 8.2 MapReduce の使用
   */
  public void after_2_step1() {
    StockInfo highPriced = new StockInfo("", BigDecimal.ZERO);
    final Predicate<StockInfo> isPriceLessThan500 = StockUtil.isPriceLessThan(500);
    for (String symbol : symbols) {
      StockInfo stockInfo = StockUtil.getPrice(symbol);
      if (isPriceLessThan500.test(stockInfo)) {
        highPriced = StockUtil.pickHigh(highPriced, stockInfo);
      }
    }
    System.out.println("High priced under $500 is " + highPriced);
  }

  /**
   * 8.2 MapReduce の使用
   */
  public void after_2_step2() {
    StockInfo highPriced =
        symbols.stream().map(StockUtil::getPrice)
            .filter(StockUtil.isPriceLessThan(500))
            .reduce(StockUtil::pickHigh).orElse(new StockInfo("", BigDecimal.ZERO));
    System.out.println("High priced under $500 is " + highPriced);
  }

  /**
   * 8.3 並列化への飛躍
   */
  public void after_3() {
    StockInfo highPriced =
        symbols.parallelStream().map(StockUtil::getPrice)
            .filter(StockUtil.isPriceLessThan(500))
            .reduce(StockUtil::pickHigh).orElse(new StockInfo("", BigDecimal.ZERO));
    System.out.println("High priced under $500 is " + highPriced);

  }


}
