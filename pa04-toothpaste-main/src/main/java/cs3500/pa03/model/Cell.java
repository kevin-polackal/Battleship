package cs3500.pa03.model;

/**
 * To represent a singular grid piece in a BattleShip board
 */
public class Cell {
  private boolean isHit;

  private boolean isShip;
  private ShipType shipType;

  private Coord coord;

  /**
   * Constructor for Cell
   *
   * @param hit Whether this cell has been hit by a missile
   * @param ship Whether this cell is a ship cell
   * @param coord the coordinates of this cell
   */
  public Cell(boolean hit, boolean ship, Coord coord) {
    this.isHit = hit;
    this.isShip = ship;
    this.coord = coord;
  }

  /**
   * sets this cell as a cell containing a part of a ship
   */
  public void setAsShip() {
    this.isShip = true;
  }

  /**
   * sets this cell as a cell that has been guessed in a salvo
   */
  public void setAsHit() {
    this.isHit = true;
  }

  /**
   * determines if this cell contains a part of a ship
   *
   * @return if this is a ship cell
   */
  public boolean isShip() {
    return isShip;
  }

  /**
   * determines if this cell has been previously guessed in a salvo
   *
   * @return if this cell has already been hit by a missile
   */
  public boolean isHit() {
    return isHit;
  }

  /**
   * gets the coordinate of this cell
   *
   * @return the coordinate of this cell
   */
  public Coord getCoord() {
    return this.coord;
  }

  /**
   * sets this cell to the given coordinate
   *
   * @param coord the coordinate this cell is set to
   */
  public void setCoord(Coord coord) {
    this.coord = coord;
  }

  /**
   * sets this cell to the given ship-type
   *
   * @param shipType the ship-type this cell is set to
   */
  public void setShipType(ShipType shipType) {
    this.shipType = shipType;
  }

  /**
   * gets the ship-type of this cell
   *
   * @return the ship-type of this cell
   */
  public ShipType getShipType() {
    return this.shipType;
  }


}
