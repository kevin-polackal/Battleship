package cs3500.pa03.model;

/**
 * To represent the three possible states of a cell in relation to if its been hit
 */
public enum Shot {
  HIT("H"), MISS("M"), NONE("?");
  public final String representation;

  Shot(String s) {
    this.representation = s;
  }
}
