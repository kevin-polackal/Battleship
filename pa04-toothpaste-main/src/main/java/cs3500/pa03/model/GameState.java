package cs3500.pa03.model;

import java.sql.SQLOutput;
import java.util.List;

/**
 * To represent the state of a player's BattleShip board
 */
public class GameState {
  private Cell[][] playerBoard;
  private OpponentCell[][] opponentBoardData;
  private List<Ship> ships;

  /**
   * Gets the height of the player's board
   *
   * @return the height of the player's board
   */
  public int getHeight() {
    return playerBoard.length;
  }

  /**
   * Gets the width of the player's board
   *
   * @return the width of the player's board
   */
  public int getWidth() {
    return playerBoard[0].length;
  }

  /**
   * Sets this board's ships to the given list of ships
   *
   * @param ships the list of ships of this board
   */
  public void setShips(List<Ship> ships) {
    this.ships = ships;
  }

  /**
   * Gets the number of un-sunk ships on this board
   *
   * @return the number of un-sunk ships on this board
   */
  public int getUnsunkShipCount() {
    int shipCount = 0;
    for (Ship s : this.ships) {
      if (!s.isSunk(playerBoard)) {
        shipCount++;
      }
    }
    return shipCount;
  }

  /**
   * Determines if the coordinate made up of the supplied x and y have alreadu been hit by one of
   * the player's salvos
   *
   * @param x the x coordinate of the Coord
   * @param y the y coordinate of the Coord
   * @return if the coordinate has already been hit by a missile
   */
  public boolean cellAlreadyHitInOpponentData(int x, int y) {
    for (int i = 0; i < opponentBoardData.length; i++) {
      for (int j = 0; j < opponentBoardData[0].length; j++) {
        if (j == x && i == y) {
          return opponentBoardData[i][j].getHit() || opponentBoardData[i][j].getMiss();
        }
      }
    }
    return false;
  }

  /**
   * Gets the number of un-hit squares on the opponent's board
   *
   * @return the number of un-hit squares on the opponent's board
   */
  public int getOpenOpponentSquares() {
    int count = 0;
    for (int i = 0; i < opponentBoardData.length; i++) {
      for (int j = 0; j < opponentBoardData[0].length; j++) {
        if (!opponentBoardData[i][j].getMiss() && !opponentBoardData[i][j].getHit()) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Updates the data collected on the opponents board
   *
   * @param totalShots the coordinates of the total shots in the salvo most recently fired
   * @param hitShots   the coordinates of the shots that hit opponent ships in the salvo most
   *                   recently fired
   */
  public void updateOpponentData(List<Coord> totalShots, List<Coord> hitShots) {
    totalShots.removeAll(hitShots);
    for (Coord c : totalShots) {
      for (int i = 0; i < opponentBoardData.length; i++) {
        for (int j = 0; j < opponentBoardData[0].length; j++) {
          if (opponentBoardData[i][j].getCoord().equals(c)) {
            opponentBoardData[i][j].setMiss();
          }
        }
      }
    }

    for (Coord c : hitShots) {
      for (int i = 0; i < opponentBoardData.length; i++) {
        for (int j = 0; j < opponentBoardData[0].length; j++) {
          if (opponentBoardData[i][j].getCoord().equals(c)) {
            opponentBoardData[i][j].setHit();
          }
        }
      }
    }
  }

  /**
   * Determines whether all the ships on this board have sunk
   *
   * @return whether all the ships on this board have sunk
   */
  public boolean allSunk() {
    for (int i = 0; i < playerBoard.length; i++) {
      for (int j = 0; j < playerBoard[0].length; j++) {
        if (playerBoard[i][j].isShip() && !playerBoard[i][j].isHit()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * To determine whether the cell corresponding to the given coordinate is a ship cell
   *
   * @param coord the coordinate of the cell that is being checked
   * @return whether this cell is a ship cell
   */
  public boolean isShipCell(Coord coord) {
    for (int i = 0; i < playerBoard.length; i++) {
      for (int j = 0; j < playerBoard[0].length; j++) {
        if (playerBoard[i][j].getCoord().equals(coord)) {
          return playerBoard[i][j].isShip();
        }
      }
    }
    return false;
  }

  /**
   * To initialize this board and the board containing our current data on the opponent's board
   */
  public void initBoard(int height, int width) {
    this.playerBoard = new Cell[height][width];
    this.opponentBoardData = new OpponentCell[height][width];
    for (int i = 0; i < playerBoard.length; i++) {
      for (int j = 0; j < playerBoard[0].length; j++) {
        playerBoard[i][j] = new Cell(false, false, new Coord(j, i));
        opponentBoardData[i][j] = new OpponentCell(new Coord(j, i));
      }
    }
  }

  /**
   * Places the given ship onto the board
   *
   * @param shipType the type of ship being added to the board
   * @param ship     the ship to be added to the board
   */
  public void putShip(ShipType shipType, Ship ship) {
    for (Coord coord : ship.getCoords()) {
      for (int i = 0; i < playerBoard.length; i++) {
        for (int j = 0; j < playerBoard[0].length; j++) {
          if (playerBoard[i][j].getCoord().equals(coord)) {
            playerBoard[i][j].setAsShip();
            playerBoard[i][j].setShipType(shipType);
          }
        }
      }
    }
  }

  /**
   * Sets all the cells matching the coordinates in the given list to hit
   *
   * @param opponentShotsOnBoard the list of coordinates representing the opponent's salvo
   */
  public void updateCells(List<Coord> opponentShotsOnBoard) {
    for (Coord c : opponentShotsOnBoard) {
      for (int i = 0; i < playerBoard.length; i++) {
        for (int j = 0; j < playerBoard[0].length; j++) {
          if (playerBoard[i][j].getCoord().equals(c)) {
            playerBoard[i][j].setAsHit();
          }
        }
      }
    }
  }

  /**
   * To create a string representation of this board's status
   *
   * @return a string representation of this board's current state
   */
  public String toString() {
    StringBuilder end = new StringBuilder();
    for (int i = 0; i < playerBoard.length; i++) {
      for (int j = 0; j < playerBoard[0].length; j++) {
        if (playerBoard[i][j].isHit() && playerBoard[i][j].isShip()) {
          end.append(Shot.HIT.representation).append(" ");
        } else if (playerBoard[i][j].isHit()) {
          end.append(Shot.MISS.representation).append(" ");
        } else if (playerBoard[i][j].isShip()) {
          end.append(playerBoard[i][j].getShipType().representation).append(" ");
        } else {
          end.append(Shot.NONE.representation).append(" ");
        }
      }
      end.append("\n");
    }
    return end.toString();
  }

  /**
   * To create a string representation of the data collected about an opponent's board
   *
   * @return a string representation of the data collected about an opponent's board
   */
  public String toStringOpponentData() {
    StringBuilder end = new StringBuilder();
    for (int i = 0; i < opponentBoardData.length; i++) {
      for (int j = 0; j < opponentBoardData[0].length; j++) {
        if (opponentBoardData[i][j].getHit()) {
          end.append(Shot.HIT.representation).append(" ");
        } else if (opponentBoardData[i][j].getMiss()) {
          end.append(Shot.MISS.representation).append(" ");
        } else {
          end.append(Shot.NONE.representation).append(" ");
        }
      }
      end.append("\n");
    }
    return end.toString();
  }
}
