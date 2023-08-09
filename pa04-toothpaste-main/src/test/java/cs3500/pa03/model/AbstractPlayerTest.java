package cs3500.pa03.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the AbstractPlayer class
 */
class AbstractPlayerTest {
  private AbstractPlayer player;

  private GameState board;

  private ManualPlayerSalvoData salvo;

  private HashMap<ShipType, Integer> specifications;

  @BeforeEach
  public void setup() {
    salvo = new ManualPlayerSalvoData();
    board = new GameState();
    player = new ManualPlayer("Joe", board, true, salvo);
    specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    GameState board2 = new GameState();
    AbstractPlayer player2 = new ManualPlayer("Joe", board2, false, salvo);
    player2.setup(6, 15, specifications);
  }

  /**
   * Tests that getName properly returns the desired name
   */
  @Test
  public void testGetName() {
    assertEquals(player.name(), "Joe");
  }

  /**
   * Tests that setup produces correctly placed ships based off seeded Randoms
   */
  @Test
  public void testSetup() {
    List<Ship> shipList = player.setup(6, 6, specifications);
    List<Integer> carrierCoords = Arrays.asList(0, 1, 2, 3, 4, 5);
    List<Integer> fromSetup = new ArrayList<>();
    for (Coord c : shipList.get(0).getCoords()) {
      fromSetup.add(c.getX());
    }
    assertEquals(carrierCoords, fromSetup);
  }


  /**
   * Tests that reportDamages properly updates the state of this player's board and returns a
   * list of successful hits
   */
  @Test
  public void testReportDamage() {
    player.setup(6, 6, specifications);
    List<Coord> coords =
        Arrays.asList(new Coord(0, 4), new Coord(1, 4), new Coord(2, 4),
            new Coord(3, 4), new Coord(4, 4), new Coord(5, 4));
    player.reportDamage(coords);
    String boardRep =
        "? B B B B B \n"
            + "? ? ? ? ? ? \n"
            + "? ? D D D D \n"
            + "? ? ? S S S \n"
            + "H H H H H H \n"
            + "? ? ? ? ? ? \n";
    assertEquals(boardRep, board.toString());
    assertEquals(coords, player.reportDamage(coords));
  }

  /**
   * Tests that successfulHits properly updates the data known about our opponent's board
   */
  @Test
  public void testSuccessfulHits() {
    player.setup(6, 6, specifications);
    final List<Coord> coordsHit = Arrays.asList(new Coord(4, 0), new Coord(5, 0));
    salvo.enterSalvo(0, 0);
    salvo.enterSalvo(1, 0);
    salvo.enterSalvo(2, 0);
    salvo.enterSalvo(3, 0);
    salvo.enterSalvo(4, 0);
    salvo.enterSalvo(5, 0);
    player.takeShots();

    player.successfulHits(coordsHit);
    String oppBoardRep =
        "M M M M H H \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n"
            + "? ? ? ? ? ? \n";
    assertEquals(oppBoardRep, board.toStringOpponentData());
  }
}