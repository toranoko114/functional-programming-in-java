package com.functional.programming.in.java.resource.chapter.four;

import java.util.function.Consumer;

public class FluentMailer {

  // コンストラクタを privateに設定し、直接のオブジェクト生成を禁止
  private FluentMailer() {
  }

  public FluentMailer from(final String address) {
    return this;
  }

  public FluentMailer to(final String address) {
    return this;
  }

  public FluentMailer subject(final String line) {
    return this;
  }

  public FluentMailer body(final String message) {
    return this;
  }

  /*
     ・オブジェクトのスコープはブロック内に制限
     ・send()メソッドの実行が終了すると参照は終了
     ・呼び出し側でnewキーワードを使う必要がない
   */
  public static void send(final Consumer<FluentMailer> block) {
    final FluentMailer mailer = new FluentMailer();
    block.accept(mailer);
    System.out.println("sending...");
  }
}