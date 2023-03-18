package com.functional.programming.in.java.resource.chapter.six;

public class HolderNaive {

  private Heavy heavy;

  public HolderNaive() {
    System.out.println("Holder created");
  }

  /*
     2つ以上のスレッドが同時に当メソッドを呼び出す場合、
     1つのスレッドにつき1つのHeavyインスタンスが作られてしまう
     スレッドセーフになっていない
   */
  public Heavy getHeavyBefore() {
    if (heavy == null) {
      heavy = new Heavy();
    }
    return heavy;
  }

  /*
    synchronizedキーワードを与えて、確実に排他処理を行う
    2つ以上のスレッドが同時にこのメソッドを呼び出すと、
    それらのメソッド呼び出しのうちの1つだけが先に進むことができ、
    他の呼び出しはキューで自分の実行順を待つ。
   */
  public synchronized Heavy getHeavy() {
    if (heavy == null) {
      heavy = new Heavy();
    }
    return heavy;
  }

  /*
     ただし、上記コードだと今後getHeavy()メソッドへのすべての呼び出しは
     排他制御のオーバーヘッドを受け入れなくてはいけないという課題がある。
     heavy参照に値が初めて代入されるまでの非常に短い間に発生する可能性があるにすぎない。
     それにしては排他制御というアプローチはその解決策として少々重すぎる。
     heavyに値が代入されるまではスレッドセーフである必要があるが、
     その後はその参照に自由にアクセスできるようにしなければいけない。
   */


}
