package com.functional.programming.in.java.resource.chapter.four;

public interface Sail {

  default void cruise() {
    System.out.println("Sail::cruise");
  }

  default void turn() {
    System.out.println("Sail::turn");
  }
}