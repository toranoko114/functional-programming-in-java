package com.functional.programming.in.java.resource.chapter.three;

import lombok.Data;

@Data
public class Person {

  private final String name;
  private final int age;

  public int ageDifference(final Person other) {
    return age - other.age;
  }

  public String toString() {
    return String.format("%s - %d", name, age);
  }


}
