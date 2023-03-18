package com.functional.programming.in.java.chapter;


import com.functional.programming.in.java.resource.chapter.four.Asset;
import com.functional.programming.in.java.resource.chapter.four.Asset.AssetType;
import com.functional.programming.in.java.resource.chapter.four.CalculateNAV;
import com.functional.programming.in.java.resource.chapter.four.Camera;
import com.functional.programming.in.java.resource.chapter.four.FluentMailer;
import com.functional.programming.in.java.resource.chapter.four.MailBuilder;
import com.functional.programming.in.java.resource.chapter.four.Mailer;
import com.functional.programming.in.java.resource.chapter.four.SeaPlane;
import com.functional.programming.in.java.resource.chapter.four.YahooFinance;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 4 章　ラムダ式で設計する
 */
public class ChapterFour {

  public static int totalAssetValues(final List<Asset> assets) {
    /*
      下記３つの問題が絡み合っている
      ・どのようにイテレーションを行うか
      ・何を合計するか → メソッドから切り離す候補となる → 関心の分離
      ・どのように合計するか
     */
    return assets.stream().mapToInt(Asset::getValue).sum();
  }

  public static int totalBondValues(final List<Asset> assets) {
    return assets.stream()
        .mapToInt(asset -> asset.getType() == AssetType.BOND ? asset.getValue() : 0).sum();
  }

  public static int totalStockValues(final List<Asset> assets) {
    return assets.stream()
        .mapToInt(asset -> asset.getType() == AssetType.STOCK ? asset.getValue() : 0).sum();
  }

  /*
      上記の書き方だと、資産タイプが増えるたびにメソッド追加が必要になるだけでなく、
      ロジックが変わったときに全てのメソッドに修正が必要になる。
      ＝ 拡張に対して開いていて（Open）、修正に対して閉じている（Closed）コードになっていない。
   */

  public static int totalAssetValues(final List<Asset> assets,
      final Predicate<Asset> assetSelector) {
    // SOLID原則の Open-ClosedPrinciple: 開放閉鎖の原則 を使ったリファクタリング
    return assets.stream().filter(assetSelector).mapToInt(Asset::getValue).sum();
  }

  final List<Asset> assets = Arrays.asList(new Asset(AssetType.BOND, 1000),
      new Asset(AssetType.BOND, 2000), new Asset(AssetType.STOCK, 3000),
      new Asset(AssetType.STOCK, 4000));

  /**
   * 4.1 ラムダ式を使った関心の分離
   */
  public void before_1() {
    System.out.println("Total of all assets: " + totalAssetValues(assets));
  }

  /**
   * 4.1 ラムダ式を使った関心の分離
   */
  public void after_1() {
    // 全資産の合計算出
    System.out.println("Total of all assets: " + totalAssetValues(assets, asset -> true));
    // 債権のみの合計算出
    System.out.println(
        "Total of bonds: " + totalAssetValues(assets, getAssetPredicate(AssetType.BOND)));
    // 株式のみの合計算出
    System.out.println(
        "Total of stocks: " + totalAssetValues(assets, getAssetPredicate(AssetType.STOCK)));
  }

  private static Predicate<Asset> getAssetPredicate(AssetType type) {
    return asset -> asset.getType() == type;
  }

  /**
   * 4.2 ラムダ式を使った委譲：TODO 要復習
   */
  public void after_2() {
    final CalculateNAV calculateNav = new CalculateNAV(YahooFinance::getPrice);
    System.out.printf("100 shares of Google worth: $%.2f%n",
        calculateNav.computeStockWorth("GOOG", 100));
  }

  /**
   * 4.3 ラムダ式を使ったデコレーション：TODO 要復習
   */
  public void after_3() {
    final Camera camera = new Camera();
    final Consumer<String> printCaptured = (filterInfo) -> System.out.printf("with %s: %s%n",
        filterInfo, camera.capture(new Color(200, 100, 200)));

    camera.setFilters(Color::brighter);
    printCaptured.accept("brighter filter");

    camera.setFilters(Color::darker);
    printCaptured.accept("darker filter");

    // 複数のフィルタを追加
    camera.setFilters(Color::brighter, Color::darker);
    printCaptured.accept("brighter & darker filter");
  }

  /**
   * 4.4 default メソッドを覗く
   */
  public void after_4() {
    SeaPlane seaPlane = new SeaPlane();
    seaPlane.takeOff();
    seaPlane.turn();
    seaPlane.cruise();
    seaPlane.land();
  }

  /**
   * 4.5 ラムダ式を使った流暢なインタフェース
   */
  public void before_5() {
    Mailer mailer = new Mailer();
    mailer.from("build@agiledeveloper.com");
    mailer.to("venkats@agiledeveloper.com");
    mailer.subject("build notification");
    mailer.body("...your code sucks...");
    mailer.send();

    /*
      mailer参照の繰り返しと、不明瞭なオブジェクト生存期間が問題
     */

  }

  /**
   * 4.5 ラムダ式を使った流暢なインタフェース
   */
  public void after_5() {

    // メソッドチェーン、カスケードパターン
    new MailBuilder().from("build@agiledeveloper.com").to("venkats@agiledeveloper.com")
        .subject("build notification").body("...your code sucks...").send();
    /*
      newキーワードが出てきて、APIの可読性と流暢さを低減させてしまいます。
      この設計はnewからの参照を保持することや、その参照からチェーンを続けることを阻止しません。
      →　どういうこと？

      このコードではオブジェクト生存期間の問題や、異常系の問題がまだ残っている
     */

    FluentMailer.send(
        fluentMailer -> fluentMailer.from("build@agiledeveloper.com")
            .to("venkats@agiledeveloper.com")
            .subject("build notification").body("...your code sucks...")
    );

  }

}
