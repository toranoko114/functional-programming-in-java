package com.functional.programming.in.java.resource.chapter.four;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class HandleException {

  public static void main(String[] args) throws IOException {
    Stream.of("/usr", "/tmp")
        .map(path -> {
          try {
            return new File(path).getCanonicalPath();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        })
        .forEach(System.out::println);

    /*
        マルチスレッドで並列実行されている場合には注意が必要
        並列実行時にはラムダ式内部で発生する例外は自動的に呼び出し元のスレッドに渡される
        下記2つの問題がある。
        ・例外は他のスレッドで走っているラムダ式を終了させたり妨害したりすることはない。
        ・並列実行中の複数のスレッドで例外が発生する場合、その中の1つだけがcatchブロックに報告される。
        　→ 例外の内容が重要であれば、ラムダ式内部で例外を捕捉しておき、結果の一部としてメインスレッドに返す
     */
  }

  @FunctionalInterface
  public interface UseInstance<T, X extends Throwable> {
    void accept(T instance) throws X;
  }

}
