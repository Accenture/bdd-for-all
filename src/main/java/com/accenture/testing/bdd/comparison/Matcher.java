package com.accenture.testing.bdd.comparison;

import java.util.Objects;

public enum Matcher {

  /** objects equal. */
  equal {

    @Override
    public boolean match(Object in, Object match) {
      return Objects.equals(in, match);
    }

    @Override
    public String toString() {
      return "equals";
    }
  },
  /** objects don't equal. */
  not_equal {

    @Override
    public boolean match(Object in, Object match) {
      return !Objects.equals(in, match);
    }

    @Override
    public String toString() {
      return "not equals";
    }
  };

  /**
   * match the objects.
   *
   * @param in the object to match
   * @param match the object to match
   * @return if they match based on operation
   */
  public abstract boolean match(Object in, Object match);
}
