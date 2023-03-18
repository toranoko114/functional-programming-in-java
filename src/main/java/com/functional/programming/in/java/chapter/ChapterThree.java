package com.functional.programming.in.java.chapter;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toList;

import com.functional.programming.in.java.resource.chapter.three.Person;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 第３章：文字列、コンパレータ、フィルタ
 */
public class ChapterThree {

  /**
   * 3.1 文字列のイテレーション（before）
   */
  public void before_1() {
    final String str = "w00t";
    str.chars().forEach(System.out::println);
    // chars()メソッドはCharacterのストリームではなく文字を表すIntegerのストリームを返す
  }

  /**
   * int型を文字で出力するコンビニエンスメソッド
   */
  private static void printchars(int aChar) {
    System.out.println((char) (aChar));
  }

  /**
   * 3.1 文字列のイテレーション（after）
   */
  public void after_1() {
    final String str = "w00t";
    // コンビニエンスメソッドを利用するパターン
    str.chars()
        .forEach(ChapterThree::printchars);
    // charsメソッドを実行直後に文字に変換するパターン
    str.chars().mapToObj(o -> (char) o).forEach(System.out::println);
    // 文字列から数字にのみを抽出
    str.chars().filter(Character::isDigit).forEach(ChapterThree::printchars);
  }

  final List<Person> people = Arrays.asList(
      new Person("John", 20),
      new Person("Sara", 21),
      new Person("Jane", 21),
      new Person("Greg", 35));

  public static void printPeople(final String message, final List<Person> people) {
    System.out.println(message);
    people.forEach(System.out::println);
  }

  /**
   * 3.2 Comparator インタフェースを実装（after）
   * <p>
   * 3.2.1 コンパレータを使ったソート
   */
  public void after_2_1() {
    // ListからStreamを取得し、そのsorted()メソッドを呼び出し可能
    // このメソッドは与えられた元のコレクションを変更することなくソートされたコレクションを返す
    List<Person> ascendingAge =
        people.stream()
            .sorted(Person::ageDifference)
            .collect(toList());
    printPeople("Sorted in ascending order by age: ", ascendingAge);
  }

  /**
   * Comparator インタフェースを実装（before）
   * <p>
   * 3.2.2 コンパレータの再利用
   */
  public void before_2_2() {
    // 年齢の昇順
    List<Person> ascendingAge =
        people.stream()
            .sorted(Person::ageDifference)
            .collect(toList());
    printPeople("Sorted in ascending order by age: ", ascendingAge);
    // 年齢の降順 パラメータの順番が引数受け渡しの規約に従っていないためメソッド参照使えない
    printPeople("Sorted in descending order by age: ",
        people.stream()
            .sorted((person1, person2) -> person2.ageDifference(person1))
            .collect(toList()));
  }

  /**
   * Comparator インタフェースを実装（after）
   * <p>
   * 3.2.2 コンパレータの再利用
   */
  public void after_2_2() {
    Comparator<Person> compareAscending = Person::ageDifference;
    // reversed()は高階関数でcompareAscendingには影響がなく新たな関数式を生成して返却する
    Comparator<Person> compareDescending = compareAscending.reversed();

    printPeople("Sorted in ascending order by age: ",
        people.stream()
            .sorted(compareAscending)
            .collect(toList())
    );
    printPeople("Sorted in descending order by age: ",
        people.stream()
            .sorted(compareDescending)
            .collect(toList())
    );

    // 年齢が一番若い人を取得する
    // ソートして最初の人を取得することもできるがmin()で取得可能
    people.stream()
        .min(compareAscending)
        .ifPresent(youngest -> System.out.println("Youngest: " + youngest));
    // 年齢が一番上の人を取得する
    people.stream()
        .max(compareAscending)
        .ifPresent(eldest -> System.out.println("Eldest: " + eldest));
  }

  /**
   * Comparator インタフェースを実装（before） 複数のプロパティによる流暢な比較
   */
  public void before_3() {
    printPeople("Sorted in ascending order by name: ",
        people.stream()
            .sorted((person1, person2) ->
                person1.getName().compareTo(person2.getName()))
            .collect(toList()));
  }

  /**
   * 3.3 複数のプロパティによる流暢な比較
   */
  public void after_3() {
    final Function<Person, String> byName = Person::getName;
    printPeople("Sorted in ascending order by name: ",
        people.stream()
            // comparing()メソッドは与えられたラムダ式のロジックを使用してComparatorを生成
            .sorted(comparing(byName))
            .collect(toList()));

    // 年齢と名前の2つのプロパティで昇順ソートする場合
    // リストを年齢の昇順でソートし、年齢が同じ場合は名前の昇順でソート
    final Function<Person, Integer> byAge = Person::getAge;
    printPeople("Sorted in ascending order by age and name: ",
        people.stream()
            .sorted(comparing(byAge).thenComparing(byName))
            .collect(toList()));
  }

  /**
   * 3.4 collect メソッドと Collectors クラスの使用
   */
  public void before_4() {
    List<Person> olderThan20 = new ArrayList<>();
    people.stream()
        .filter(person -> person.getAge() > 20)
        .forEach(olderThan20::add);
    System.out.println("People older than 20: " + olderThan20);

    // 課題
    // ターゲットとするコレクションに要素を 1 つずつ追加する処理は宣言型ではなく命令型のコード
    // このイテレーションを並列実行させる場合にはスレッドセーフ問題を適切に処理しなければいけない
  }

  /**
   * 3.4 collect メソッドと Collectors クラスの使用
   */
  public void after_4() {
    /*
      collect()メソッドで知っておくべき3つのこと
        ● サプライヤ：結果を収めるコンテナの生成方法（例えば ArrayList::new）
        ● アキュムレータ：結果コンテナに単一の要素を追加する方法（例えば ArrayList::add）
        ● コンバイナ：結果コンテナを他のコンテナと結合する方法（例えば ArrayList::addAll）
    */
    List<Person> olderThan20 =
        people.stream()
            .filter(person -> person.getAge() > 20)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    System.out.println("People older than 20: " + olderThan20);

    /*
      コード内で状態変更を行っていないため、イテレーションを簡単に並列化可能
      イテレーション制御を委ねているライブラリがプロセス間の調整やスレッドセーフ処理を行ってくれる
      ArrayList::addAllの部分はflatMapの処理的なこと？
     */

    // collect(ArrayList::new, ArrayList::add, ArrayList::addAll)をcollect(toList())に置き換え
    // Collectorを引数に取るCollectorは、
    // collect()メソッドに設定された3つの異なるパラメータ（サプライヤ、アキュムレータ、そしてコンバイナ）をカプセル化した、
    // より簡単で再利用可能なインタフェース
    List<Person> olderThan20_2 =
        people.stream()
            .filter(person -> person.getAge() > 20)
            .collect(toList());
    System.out.println("People older than 20: " + olderThan20_2);

    // groupingBy()メソッドを使用して、年齢グループ別に分ける
    Map<Integer, List<Person>> peopleByAge = people.stream()
        .collect(groupingBy(Person::getAge));
    System.out.println("Grouped by age: " + peopleByAge);

    // 年齢別に名前のみグルーピングする
    Map<Integer, List<String>> nameOfPeopleByAge =
        people.stream()
            .collect(groupingBy(Person::getAge, mapping(Person::getName, toList())));
    System.out.println("People grouped by age: " + nameOfPeopleByAge);

    // 名前の頭文字でグループ化し、各グループの最年長を抽出
    Comparator<Person> byAge = Comparator.comparing(Person::getAge);
    Map<Character, Optional<Person>> oldestPersonOfEachLetter =
        people.stream()
            .collect(groupingBy(person -> person.getName().charAt(0),
                reducing(BinaryOperator.maxBy(byAge))));
    System.out.println("Oldest person of each letter:");
    System.out.println(oldestPersonOfEachLetter);
  }

  /**
   * 3.5 ディレクトリの全ファイルをリスト
   */
  public void after_5() throws IOException {
    Files.list(Paths.get("."))
        .filter(Files::isDirectory)
        .forEach(System.out::println);
  }

  /**
   * 3.6 ディレクトリの特定のファイルだけをリスト
   */
  public void before_6() throws IOException {
    final String[] files =
        new File("fpij").list(new java.io.FilenameFilter() {
          public boolean accept(final File dir, final String name) {
            return name.endsWith(".java");
          }
        });
    System.out.println(files);
  }

  /**
   * 3.6 ディレクトリの特定のファイルだけをリスト
   */
  public void after_6() throws IOException {
    Files.newDirectoryStream(
            Paths.get("fpij"), path -> path.toString().endsWith(".java"))
        .forEach(System.out::println);
    final File[] files = new File(".").listFiles(File::isHidden);
  }

  /**
   * 3.7 flatMap で直下のサブディレクトリをリスト
   */
  public void before_7() {
    List<File> files = new ArrayList<>();
    File[] filesInCurrentDir = new File(".").listFiles();
    for (File file : Objects.requireNonNull(filesInCurrentDir)) {
      File[] filesInSubDir = file.listFiles();
      if (filesInSubDir != null) {
        files.addAll(Arrays.asList(filesInSubDir));
      } else {
        files.add(file);
      }
    }
    System.out.println("Count: " + files.size());
  }

  /**
   * 3.7 flatMap で直下のサブディレクトリをリスト
   */
  public void after_7() {
    List<File> files =
        Stream.of(Objects.requireNonNull(new File(".").listFiles()))
            .flatMap(file -> file.listFiles() == null ?
                Stream.of(file) : Stream.of(Objects.requireNonNull(file.listFiles())))
            .collect(toList());
    System.out.println("Count: " + files.size());
  }

}
