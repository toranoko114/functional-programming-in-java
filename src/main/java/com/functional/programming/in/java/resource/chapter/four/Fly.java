package com.functional.programming.in.java.resource.chapter.four;

public interface Fly {

  default void takeOff() {
    System.out.println("Fly::takeOff");
  }

  default void land() {
    System.out.println("Fly::land");
  }

  default void turn() {
    System.out.println("Fly::turn");
  }

  default void cruise() {
    System.out.println("Fly::cruise");
  }
}
