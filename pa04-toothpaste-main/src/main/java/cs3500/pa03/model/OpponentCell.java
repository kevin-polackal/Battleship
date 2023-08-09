package cs3500.pa03.model;

/**
 * To represent information about an opponent's cell on a board of BattleShip, which only consists
 * of knowing if a given cell when fired upon is either missed or hit (a ship was there)
 */
public class OpponentCell {
  private boolean isHit;
  private boolean isMiss;

  private final Coord coord;

  /**
   * Constructor for an OpponentCell
   *
   * @param coord The coordinate of this OpponentCell
   */
  public OpponentCell(Coord coord) {
    this.isHit = false;
    this.isMiss = false;
    this.coord = coord;
  }

  /**
   * Sets this cell as being hit
   */
  public void setHit() {
    this.isHit = true;
  }

  /**
   * Sets this cell as being a miss
   */
  public void setMiss() {
    this.isMiss = true;
  }

  /**
   * Determines whether this cell is a successfully hit ship
   *
   * @return whether this cell is a hit cell
   */
  public boolean getHit() {
    return this.isHit;
  }

  /**
   * Determines whether this cell is a targeted cell that ended up being missed
   *
   * @return whether this cell is a missed cell
   */
  public boolean getMiss() {
    return this.isMiss;
  }

  /**
   * Gets the coordinates of this cell
   *
   * @return the coordinates of this cell
   */
  public Coord getCoord() {
    return this.coord;
  }

}
