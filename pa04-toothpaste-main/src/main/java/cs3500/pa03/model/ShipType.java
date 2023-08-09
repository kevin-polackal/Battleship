package cs3500.pa03.model;

/**
 * To represent the four different ship types in BattleSalvo
 */
public enum ShipType {
  CARRIER(6, "C"), BATTLESHIP(5, "B"), DESTROYER(4, "D"), SUBMARINE(3, "S");
  public final int shipLength;
  public final String representation;

  ShipType(int length, String representation) {
    this.shipLength = length;
    this.representation = representation;
  }
}
