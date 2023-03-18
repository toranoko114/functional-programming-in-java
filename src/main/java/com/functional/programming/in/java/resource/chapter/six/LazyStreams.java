package com.functional.programming.in.java.resource.chapter.six;

public class LazyStreams {

  public static int length(final String name) {
    System.out.println("getting length for " + name);
    return name.length();
  }

  public static String toUpper(final String name) {
    System.out.println("converting to uppercase: " + name);
    return name.toUpperCase();
  }


}
