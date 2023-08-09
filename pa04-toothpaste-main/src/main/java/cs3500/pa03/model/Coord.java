package cs3500.pa03.model;

/**
 * To represent a coordinate, which has an x and y value
 */
public class Coord {
  private final int coordinateX;
  private final int coordinateY;

  public Coord(int x, int y) {
    this.coordinateX = x;
    this.coordinateY = y;
  }

  /**
   * Gets the x coordinate of this coordinate
   *
   * @return the x coordinate
   */
  public int getX() {
    return this.coordinateX;
  }

  /**
   * Gets the y coordinate of this coordinate
   *
   * @return the y coordinate
   */
  public int getY() {
    return this.coordinateY;
  }

  /**
   * Determines whether this Coord and the given object are equal
   *
   * @param obj the object being compared
   * @return if this and the given object are equal
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Coord)) {
      return false;
    }
    Coord other = (Coord) obj;
    return this.coordinateX == other.coordinateX && this.coordinateY == other.coordinateY;
  }
}
