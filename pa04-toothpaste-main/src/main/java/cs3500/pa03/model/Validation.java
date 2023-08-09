package cs3500.pa03.model;

import java.util.List;

/**
 * To validate data in the context of BattleSalvo and see if the inputted data from the user makes
 * sense
 */
public class Validation {

  /**
   * To validate the inputted board sizes fit the required criteria of being between 6 and 15
   * inclusive
   *
   * @param height the desired height of the board
   * @param width  the desired width of the board
   * @return whether the given dimensions fit the criteria for a valid board
   */
  public static boolean boardValidation(int height, int width) {
    return width >= 6 && width <= 15 && height >= 6 && height <= 15;
  }

  /**
   * To validate the inputted fleet meets the required criteria of being as large as the smaller
   * board dimension and having at least 1 of every ship
   *
   * @param carrier   the desired amount of carriers
   * @param battle    the desired amount of battleships
   * @param destroyer the desired amount of destroyers
   * @param sub       the desired amount of submarines
   * @param fleetSize the total fleet size
   * @return whether the given data meets the criteria for a valid fleet
   */
  public static boolean fleetValidation(int carrier, int battle, int destroyer, int sub,
                                        int fleetSize) {
    return carrier >= 1
        && battle >= 1
        && destroyer >= 1
        && sub >= 1
        && carrier + battle + destroyer + sub <= fleetSize;
  }

  /**
   * To validate an inputted salvo coordinate such that it meets the criteria of being a not
   * previously hit coordinate, not currently in the salvo, and within the board's dimensions
   *
   * @param x          the x coordinate of the desired Coord
   * @param y          the y coordinate of teh desired Coord
   * @param board      the board being played on
   * @param height     the height of the board
   * @param width      the width of the board
   * @param manualData the current manual salvo
   * @return whether the desired coordinate is a valid salvo coordinate
   */
  public static boolean salvoCoordinateValidation(int x, int y, GameState board, int height,
                                                  int width, ManualPlayerSalvoData manualData) {
    if (x >= width || x < 0) {
      return false;
    }
    if (y >= height || y < 0) {
      return false;
    }
    if (board.cellAlreadyHitInOpponentData(x, y)) {
      return false;
    }
    return !manualData.contains(x, y);
  }

  /**
   * To validate an ai salvo coordinate such that it meets the criteria of being a not
   * previously hit coordinate, and not currently in the salvo
   *
   * @param x      the x coordinate of the desired Coord
   * @param y      the y coordinate of the desired Coord
   * @param board  the board being plaued
   * @param coords the current ai salvo
   * @return whether this coord is a valid ai salvo coordinate
   */
  public static boolean aiSalvoCoordinateValidation(int x, int y, GameState board,
                                                    List<Coord> coords) {
    for (Coord c : coords) {
      if (c.getX() == x && c.getY() == y) {
        return false;
      }
    }

    if (x >= board.getWidth() || x < 0) {
      return false;
    }
    if (y >= board.getHeight() || y < 0) {
      return false;
    }
    return !board.cellAlreadyHitInOpponentData(x, y);
  }


  /**
   * Validates that desired ship to be added fits within the board limits and does not overlap with
   * other ships
   *
   * @param height the height of the board
   * @param width  the width of the board
   * @param board  the board being played on
   * @param coords the coordinates representing the desired ship to be added
   * @return whether the ship is a valid ship placement
   */
  public static boolean shipCoordinateValidation(int height, int width, GameState board,
                                                 List<Coord> coords) {

    for (Coord c : coords) {
      if (c.getX() >= width || c.getY() >= height) {
        return false;
      }
    }
    for (Coord c : coords) {
      if (board.isShipCell(c)) {
        return false;
      }
    }
    return true;
  }
}
