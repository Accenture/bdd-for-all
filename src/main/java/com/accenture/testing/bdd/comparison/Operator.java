package com.accenture.testing.bdd.comparison;

public enum Operator {

  /** In parameter equals match parameter. */
  only {

    @Override
    public boolean calc(int in, int match) {
      return in == match;
    }

    @Override
    public String toString() {
      return "==";
    }
  },
  /** In parameter is greater than or equals match parameter. */
  at_least {

    @Override
    public boolean calc(int in, int match) {
      return in >= match;
    }

    @Override
    public String toString() {
      return ">=";
    }
  },
  /** In parameter is less than or equals match parameter. */
  at_most {

    @Override
    public boolean calc(int in, int match) {
      return in <= match;
    }

    @Override
    public String toString() {
      return "<=";
    }
  },
  /** In parameter is greater than match parameter. */
  more_than {

    @Override
    public boolean calc(int in, int match) {
      return in > match;
    }

    @Override
    public String toString() {
      return ">";
    }
  },
  /** In parameter is less than match parameter. */
  less_than {

    @Override
    public boolean calc(int in, int match) {
      return in < match;
    }

    @Override
    public String toString() {
      return "<";
    }
  };

  /**
   * confirm the difference between two numbers.
   *
   * @param in the number to compare
   * @param match the number to compare against
   * @return the result
   */
  public boolean calc(int in, int match) {
    return false;
  }
}
