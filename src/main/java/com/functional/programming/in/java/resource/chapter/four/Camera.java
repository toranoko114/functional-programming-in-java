package com.functional.programming.in.java.resource.chapter.four;

import java.awt.Color;
import java.util.function.Function;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class Camera {

  private Function<Color, Color> filter;

  public void setFilters(final Function<Color, Color>... filters) {
    filter =
        Stream.of(filters)
            .reduce((filter, next) -> filter.compose(next)).orElse(color -> color);
  }

  public Camera() {
    setFilters();
  }

  public Color capture(Color inputColor) {
    return filter.apply(inputColor);
  }
}
