package cs3500.pa03.model;

import cs3500.pa04.model.ShipOrientation;
import java.util.List;

/**
 * To represent a ship in a game of BattleShip
 */
public class Ship {
  private List<Coord> coords;

  public Ship(List<Coord> coords) {
    this.coords = coords;
  }

  /**
   * Determines whether this ship has been sunk
   *
   * @param playerBoard the board this ship is on
   * @return whether this ship has been sunk
   */
  public boolean isSunk(Cell[][] playerBoard) {
    for (Coord c : coords) {
      for (int i = 0; i < playerBoard[0].length; i++) {
        for (int j = 0; j < playerBoard.length; j++) {
          if (playerBoard[j][i].getCoord().equals(c)) {
            if (!playerBoard[j][i].isHit()) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * Gets the list of coordinates representing this ship
   *
   * @return the list of coordinates representing this ship
   */
  public List<Coord> getCoords() {
    return this.coords;
  }

  public Coord getFirstCoord() {
    return this.coords.get(0);
  }

  public int getShipLength() {
    return this.coords.size();
  }

  /**
   * Gets the orientation of the ship
   *
   * @return whether the ship lies horizontally or vertically
   */
  public ShipOrientation getOrientation() {
    if (coords.get(0).getX() - coords.get(coords.size() - 1).getX() != 0) {
      return ShipOrientation.HORIZONTAL;
    }
    return ShipOrientation.VERTICAL;
  }


  /**
   * Determines whether the given coordinate is part of this ships list of coordinates
   *
   * @param coord a Coord
   * @return whether the given coord is part of the list of coordinates that define this ship
   */
  public boolean contains(Coord coord) {
    for (Coord c : coords) {
      if (c.equals(coord)) {
        return true;
      }
    }
    return false;
  }
}
