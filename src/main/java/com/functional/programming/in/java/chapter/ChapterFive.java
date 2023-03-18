package com.functional.programming.in.java.chapter;

import com.functional.programming.in.java.resource.chapter.five.FileWriterARM;
import com.functional.programming.in.java.resource.chapter.five.FileWriterEAM;
import com.functional.programming.in.java.resource.chapter.five.FileWriterExample;
import java.io.IOException;

/**
 * 第5章 外部リソースを扱う
 */
public class ChapterFive {

  /*
    データベース接続、ファイルやソケット、そしてネイティブリソースといった
    外部リソースを使用する場合は、GCは開発者の責任範囲
   */

  // ラムダ式を使って execute around method（EAM）†を実装
  // どういうこと？

  /**
   * 5.1 リソースの解放
   */
  public void before_1() throws IOException {

    final FileWriterExample writerExample = new FileWriterExample("peekaboo.txt");
    writerExample.writeStuff("peek-a-boo");
    writerExample.close();

    // 上記のコードだとclose前に例外が発生しリソースの解放がされない可能性ある
    // 確実にリソースを解放するよう修正する必要がある
    final FileWriterExample writerExample_2 = new FileWriterExample("peekaboo.txt");
    try {
      writerExample_2.writeStuff("peek-a-boo");
    } finally {
      writerExample_2.close();
    }
  }

  /**
   * 5.1 リソースの解放
   */
  public void after_1() throws IOException {
    // 自動リソース管理（ARM）の使用
    // try-with-resources
    // 管理対象のリソースとして AutoCloseableインタフェースを実装したクラスを必要とする
    try (final FileWriterARM writerARM = new FileWriterARM("peekaboo.txt")) {
      writerARM.writeStuff("peek-a-boo");
      System.out.println("done with the resource...");
    }
    // 開発者が AutoCloseableの実装を確実に行い、
    // さらにtry-with-resources 構文を忘れずに使わなければいけない
  }

  /**
   * 5.2 ラムダ式でリソース解放：TODO 要復習
   */
  public void before_2() {
  }

  /**
   * 5.2 ラムダ式でリソース解放：TODO 要復習
   */
  public void after_2() throws IOException {
    FileWriterEAM.use("peekaboo.txt", writerEAM -> writerEAM.writeStuff("peek-a-boo"));
    FileWriterEAM.use("peekaboo2.txt", writerEAM -> {
      writerEAM.writeStuff("how");
      writerEAM.writeStuff("sweet");
    });
  }

  /**
   * 5.3 ロックの管理：TODO 要復習
   */
  public void before_3() {
  }

  /**
   * 5.3 ロックの管理：TODO 要復習
   */
  public void after_3() {

  }

  /**
   * 5.4 簡潔な例外テストの生成：TODO 要復習
   */
  public void before() {
  }

  /**
   * 5.4 簡潔な例外テストの生成：TODO 要復習
   */
  public void after() {

  }


}
