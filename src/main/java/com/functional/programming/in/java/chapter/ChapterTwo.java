package com.functional.programming.in.java.chapter;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 第２章：コレクションの使用
 */
public class ChapterTwo {

  final List<String> friends =
      Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
  final List<String> editors =
      Arrays.asList("Brian", "Jackie", "John", "Mike");
  final List<String> comrades =
      Arrays.asList("Kate", "Ken", "Nick", "Paula", "Zach");


  /**
   * 2.1 リストをイテレート（before）
   */
  public void before_1() {
    /*
      ● forループは本質的にシーケンシャルであり、並列化が極めて難しい。
      ● このようなループはポリモーフィックではなく、命令した通りのことが実行
      される。コレクションに対して（ポリモーフィックな処理を行う）メソッド
      を呼び出すのではなく、forループに渡している。
      ● 設計レベルで、コードは「伝えろ。聞くな。」†という原則を破っている。for
      ループでは、イテレーションの詳細をライブラリに任せるのではなく、特定
      のイテレーション処理を実行するよう要求している。
     */
    for (String name : friends) {
      System.out.println(name);
    }
  }

  /**
   * 2.1 リストをイテレート（after）
   */
  public void after_1() {
    /*
        friends.forEach(new Consumer<String>() {
          public void accept(final String name) {
            System.out.println(name);
          }
        });
    */
    /*
        friends.forEach((final String name) -> System.out.println(name));
    */
    /*
        friends.forEach(name -> System.out.println(name));
    */
    friends.forEach(System.out::println);
  }

  /**
   * 2.2 リストの変換（before）
   */
  public void before_2() {
    final List<String> uppercaseNames = new ArrayList<String>();
    for (String name : friends) {
      uppercaseNames.add(name.toUpperCase());
    }
  }

  /**
   * 2.2 リストの変換（after）
   */
  public void after_2() {
    /*
        final List<String> uppercaseNames = new ArrayList<String>();
        friends.forEach(name -> uppercaseNames.add(name.toUpperCase())); System.out.
            println(uppercaseNames);
    */
    /*
        // ラムダ式を使う
        friends.stream()
            .map(name -> name.toUpperCase())
            .forEach(name -> System.out.print(name + " "));
    */

    /*
      メソッド参照を利用
      ● ラムダ式がただ引数を渡しているだけのときはメソッド参照に入れ替え可能
      ● 下記の場合はメソッド参照は利用できない
        ・パラメータを引数として渡す前に処理を行う場合や
        ・呼び出しの結果を返す前にその内容を修正しなければならない場合
     */
    friends.stream()
        .map(String::toUpperCase)
        .forEach(System.out::println);
  }

  /**
   * 要素の検索（before）
   */
  public void before_3() {
    final List<String> startsWithN = new ArrayList<String>();
    for (String name : friends) {
      if (name.startsWith("N")) {
        startsWithN.add(name);
      }
    }
    System.out.printf("Found %d names%n", startsWithN.size());
  }

  /**
   * 2.3 要素の検索（after）
   */
  public void after_3() {
    final List<String> startsWithN = friends.stream()
        .filter(name -> name.startsWith("N"))
        .toList();
    System.out.printf("Found %d names%n", startsWithN.size());
  }

  /**
   * 2.4 ラムダ式の再利用（before）
   */
  public void before_4() {
    final long countFriendsStartN =
        friends.stream()
            .filter(name -> name.startsWith("N")).count();
    final long countEditorsStartN =
        editors.stream()
            .filter(name -> name.startsWith("N")).count();
    final long countComradesStartN =
        comrades.stream()
            .filter(name -> name.startsWith("N")).count();
  }

  /**
   * 2.4 ラムダ式の再利用（after）
   */
  public void after_4() {
    /*
       Predicate<T>は T型を引数に取り、関数が行う検査の結果として
       booleanを返す。これは候補値の取捨選択を行う際に使用できる。
    */
    final Predicate<String> startsWithN = name -> name.startsWith("N");
    final long countFriendsStartN =
        friends.stream()
            .filter(startsWithN).count();
    final long countEditorsStartN =
        editors.stream()
            .filter(startsWithN).count();
    final long countComradesStartN =
        comrades.stream()
            .filter(startsWithN).count();
  }

  /**
   * 2.5 静的スコープとクロージャ（before）
   */
  public void before_5() {
    final Predicate<String> startsWithN = name -> name.startsWith("N");
    final Predicate<String> startsWithB = name -> name.startsWith("B");
    final long countFriendsStartN =
        friends.stream()
            .filter(startsWithN)
            .count();
    final long countFriendsStartB =
        friends.stream()
            .filter(startsWithB)
            .count();
  }

  /**
   * 関数を返却する高階関数
   *  ● staticメソッドでクラスを汚染したくない 　
   *  ● 関数のスコープを最小限に抑えたい
   *
   * @param letter 文字
   * @return Predicate型の関数
   */
  private static Predicate<String> checkIfStartsWith(final String letter) {
    return name -> name.startsWith(letter);
  }

  /**
   * 2.5 静的スコープとクロージャ（after）
   */
  public void after_5() {
    /*
        final long countFriendsStartN =
            friends.stream()
                .filter(checkIfStartsWith("N"))
                .count();
        final long countFriendsStartB =
            friends.stream()
                .filter(checkIfStartsWith("B"))
                .count();
    */

    /*
        checkIfStartsWithメソッドを置換
        Function<T,R>は T型の引数を取り、R型の結果を返す関数
     */

    final Function<String, Predicate<String>> startsWithLetter =
        letter -> name -> name.startsWith(letter);

    final long countFriendsStartN =
        friends.stream()
            .filter(startsWithLetter.apply("N"))
            .count();
    final long countFriendsStartB =
        friends.stream()
            .filter(startsWithLetter.apply("B"))
            .count();
  }

  /**
   * 2.6 要素を 1 つ選択（before）
   */
  public void before_6(final List<String> names, final String startingLetter) {
    String foundName = null;
    for (String name : names) {
      if (name.startsWith(startingLetter)) {
        foundName = name;
        break;
      }
    }
    System.out.print(String.format("A name starting with %s: ", startingLetter));
    if (foundName != null) {
      System.out.println(foundName);
    } else {
      System.out.println("No name found");
    }
  }

  /**
   * 2.6 要素を 1 つ選択（after）
   */
  public void after_6(final List<String> names, final String startingLetter) {
    final Optional<String> foundName = names.stream()
        .filter(name -> name.startsWith(startingLetter))
        .findFirst();
    System.out.printf("A name starting with %s: %s%n",
        startingLetter, foundName.orElse("No name found"));

    //値が存在する場合にのみ処理を実行する
    foundName.ifPresent(name -> System.out.println("Hello " + name));
  }

  /**
   * 2.7 コレクションを単一の値に集約（before）
   */
  public void before_7() {
    System.out.println("Total number of characters in all names: " +
        friends.stream()
            .mapToInt(name -> name.length())
            .sum());
  }

  /**
   * 2.7 コレクションを単一の値に集約（after）
   */
  public void after_7() {
    // reduce()メソッドの戻り値が Optionalである理由はreduce()が呼び出されるリストが空である可能性があるため
    final Optional<String> aLongName = friends.stream()
        .reduce((source, target) ->
            source.length() >= target.length() ? source : target);
    aLongName.ifPresent(name ->
        System.out.printf("A longest name: %s%n", name));

    // 比較元の初期値を設定することも可能
    // この場合は初期値が設定されておりreduce()の返却値が空になる可能性がないためOptionalにならない
    final String steveOrLonger = friends.stream()
        .reduce("Steve", (name1, name2) ->
            name1.length() >= name2.length() ? name1 : name2);
  }

  /**
   * 2.8 要素の結合（before）
   */
  public void before_8() {
    for (int i = 0; i < friends.size() - 1; i++) {
      System.out.print(friends.get(i) + ", ");
    }
    if (friends.size() > 0) {
      System.out.println(friends.get(friends.size() - 1));
    }
  }

  /**
   * 2.8 要素の結合（after）
   */
  public void after_8() {
    System.out.println(
        friends.stream()
            .map(String::toUpperCase)
            .collect(joining(", ")));
  }

}
