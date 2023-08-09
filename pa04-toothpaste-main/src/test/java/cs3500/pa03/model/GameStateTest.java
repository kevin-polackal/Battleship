package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameStateTest {

  private GameState board;

  private List<Coord> sub1Coords;
  private List<Coord> sub2Coords;
  private List<Coord> total;

  private List<Coord> hit;

  @BeforeEach
  public void setup() {
    board = new GameState();
    board.initBoard(6, 6);
    sub1Coords = Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    sub2Coords = Arrays.asList(new Coord(3, 0), new Coord(4, 0), new Coord(5, 0));
    total = new ArrayList<>();
    total.add(new Coord(0, 0));
    total.add(new Coord(1, 0));
    total.add(new Coord(2, 0));
    total.add(new Coord(3, 0));
    total.add(new Coord(4, 0));
    total.add(new Coord(5, 0));
    total.add(new Coord(5, 5));
    hit = new ArrayList<>();
    hit.add(new Coord(0, 0));
    hit.add(new Coord(1, 0));
    hit.add(new Coord(2, 0));
  }

  /**
   * Tests that getWidth properly returns the width of the board
   */
  @Test
  public void testGetWidth() {
    assertEquals(board.getWidth(), 6);
  }

  /**
   * Tests that getHeight properly returns the height of the board
   */
  @Test
  public void testGetHeight() {
    assertEquals(board.getHeight(), 6);
  }

  /**
   * Tests that setShips properly initializes the List of Ship private variable
   */
  @Test
  public void testSetShip() {
    Ship sub1 = new Ship(sub1Coords);
    Ship sub2 = new Ship(sub2Coords);
    board.setShips(Arrays.asList(sub1, sub2));
    assertEquals(2, board.getUnsunkShipCount());
  }

  /**
   * Tests that the un-sunk ship count is true as ships are sunk
   */
  @Test
  public void getUnsunkShipCount() {
    Ship sub1 = new Ship(sub1Coords);
    Ship sub2 = new Ship(sub2Coords);
    board.setShips(Arrays.asList(sub1, sub2));
    assertEquals(2, board.getUnsunkShipCount());
    board.updateCells(sub1Coords);
    assertEquals(1, board.getUnsunkShipCount());
    board.updateCells(sub2Coords);
    assertEquals(0, board.getUnsunkShipCount());
  }

  /**
   * Tests that cellAlreadyHitInOpponentData properly determines if a given coordinate has already
   * been guessed in a previous salvo
   */
  @Test
  public void testCellAlreadyHitInOpponentData() {
    board.updateOpponentData(total, hit);
    assertTrue(board.cellAlreadyHitInOpponentData(0, 0));
    assertTrue(board.cellAlreadyHitInOpponentData(5, 0));
    assertFalse(board.cellAlreadyHitInOpponentData(3, 3));
    assertFalse(board.cellAlreadyHitInOpponentData(100, 100));
  }

  /**
   * Tests that getOpenOpponentSquares returns the proper amount of unknown squares on our view of
   * the opponents board
   */
  @Test
  public void testGetOpenOpponentSquares() {
    assertEquals(board.getOpenOpponentSquares(), 36);
    board.updateOpponentData(total, hit);
    assertEquals(board.getOpenOpponentSquares(), 29);
  }

  /**
   * Tests that updateOpponentData properly updates the known information about an opponent's board
   */
  @Test
  public void testUpdateOpponentData() {
    board.updateOpponentData(total, hit);
    assertEquals(board.getOpenOpponentSquares(), 29);

  }

  /**
   * Tests that allSunk properly determines if all the ships on a player's board have been sunk
   */
  @Test
  public void testAllSunk() {
    Ship sub1 = new Ship(sub1Coords);
    Ship sub2 = new Ship(sub2Coords);
    board.putShip(ShipType.SUBMARINE, sub1);
    board.putShip(ShipType.SUBMARINE, sub2);
    assertFalse(board.allSunk());
    board.updateCells(sub1Coords);
    board.updateCells(sub2Coords);
    assertTrue(board.allSunk());
  }

  /**
   * Tests that isShipCell properly determines if a cell with the given coordinate is a ship cell
   */
  @Test
  public void testIsShipCell() {
    Ship sub1 = new Ship(sub1Coords);
    Ship sub2 = new Ship(sub2Coords);
    board.putShip(ShipType.SUBMARINE, sub1);
    board.putShip(ShipType.SUBMARINE, sub2);
    assertTrue(board.isShipCell(sub1Coords.get(0)));
    assertFalse(board.isShipCell(new Coord(5, 5)));
    assertFalse(board.isShipCell(new Coord(100, 100)));
  }

  /**
   * Tests that putShips properly places a ship onto the player's board
   */
  @Test
  public void testPutShips() {
    Ship sub1 = new Ship(sub1Coords);
    assertFalse(board.isShipCell(sub1Coords.get(0)));
    board.putShip(ShipType.SUBMARINE, sub1);
    assertTrue(board.isShipCell(sub1Coords.get(0)));
  }

  /**
   * Tests that updateCells properly updates the player's board with salvo shots
   */
  @Test
  public void testUpdateCells() {
    Ship sub1 = new Ship(sub1Coords);
    Ship sub2 = new Ship(sub2Coords);
    board.putShip(ShipType.SUBMARINE, sub1);
    board.putShip(ShipType.SUBMARINE, sub2);
    board.updateCells(sub1Coords);
    String boardRep =
        "H H H S S S \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n";
    assertEquals(boardRep, board.toString());
  }

  /**
   * Tests that toString presents the proper string representation of a board
   */
  @Test
  public void testToString() {
    Ship sub1 = new Ship(sub1Coords);
    Ship sub2 = new Ship(sub2Coords);
    board.putShip(ShipType.SUBMARINE, sub1);
    String boardRep =
        "S S S ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n";
    assertEquals(boardRep, board.toString());
    board.putShip(ShipType.SUBMARINE, sub2);
    boardRep =
        "S S S S S S \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n";
    assertEquals(boardRep, board.toString());
    board.updateCells(total);
    boardRep =
        "H H H H H H \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? M \n";
    assertEquals(boardRep, board.toString());
  }

  /**
   * Tests that opponentDataToString properly represents the known data of the opponent's board
   */
  @Test
  public void testOpponentDataToString() {
    String oppBoardRep =
        "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n";
    assertEquals(oppBoardRep, board.toStringOpponentData());
    board.updateOpponentData(total, hit);
    oppBoardRep =
        "H H H M M M \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? M \n";
    assertEquals(oppBoardRep, board.toStringOpponentData());
  }
}