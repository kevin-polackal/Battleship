package cs3500.pa04.model;

/**
 * To represent how a ships lies on the board: horizontal or vertical
 */
public enum ShipOrientation {
  HORIZONTAL("HORIZONTAL"),
  VERTICAL("VERTICAL");
  public final String representation;

  ShipOrientation(String rep) {
    this.representation = rep;
  }
}
