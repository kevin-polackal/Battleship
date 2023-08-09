package cs3500.pa03.model;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testing for the Validation class
 */
class ValidationTest {

  private AbstractPlayer player;

  private GameState board;

  private ManualPlayerSalvoData salvo;

  private HashMap<ShipType, Integer> specifications;

  @BeforeEach
  public void setup() {
    salvo = new ManualPlayerSalvoData();
    salvo.enterSalvo(0, 0);
    salvo.enterSalvo(1, 1);
    salvo.enterSalvo(2, 2);

    board = new GameState();
    player = new ManualPlayer("Joe", board, true, salvo);
    specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    ArrayList<Coord> list1 = new ArrayList<>();
    list1.add(new Coord(5, 5));
    ArrayList<Coord> list2 = new ArrayList<>();
    list2.add(new Coord(5, 5));
    list2.add(new Coord(4, 4));
    player.setup(6, 6, specifications);
    board.updateOpponentData(list2, list1);

    GameState board2 = new GameState();
    AbstractPlayer player2 = new ManualPlayer("Joe", board2, false, salvo);
    player2.setup(6, 15, specifications);
  }

  /**
   * Tests the various inputs for board validation and whether it creates a valid board
   */
  @Test
  public void testBoardValidation() {

    assertTrue(Validation.boardValidation(10, 15));
    assertFalse(Validation.boardValidation(1, 100));
    assertFalse(Validation.boardValidation(100, 1));
    assertFalse(Validation.boardValidation(1, 1));
    assertFalse(Validation.boardValidation(100, 100));
    assertFalse(Validation.boardValidation(10, 100));
    assertFalse(Validation.boardValidation(100, 10));
    assertFalse(Validation.boardValidation(1, 10));
  }

  /**
   * Tests the various inputs for a fleet and if they correspond to a valid fleet
   */
  @Test
  public void testFleetValidation() {

    assertTrue(Validation.fleetValidation(1, 1, 1, 1, 5));
    assertFalse(Validation.fleetValidation(0, 1, 1, 1, 5));
    assertFalse(Validation.fleetValidation(1, 0, 1, 1, 5));
    assertFalse(Validation.fleetValidation(1, 1, 0, 1, 5));
    assertFalse(Validation.fleetValidation(1, 1, 1, 0, 5));
    assertFalse(Validation.fleetValidation(1, 1, 1, 1, 2));
  }

  /**
   * Tests inputs for manual salvo and if they correspond to a valid manual salvo coordinate
   */
  @Test
  public void testSalvoValidation() {


    assertTrue(Validation.salvoCoordinateValidation(3, 3, board, 6, 6, salvo));
    assertFalse(Validation.salvoCoordinateValidation(7, 3, board, 6, 6, salvo));
    assertFalse(Validation.salvoCoordinateValidation(-1, 3, board, 6, 6, salvo));
    assertFalse(Validation.salvoCoordinateValidation(3, 8, board, 6, 6, salvo));
    assertFalse(Validation.salvoCoordinateValidation(3, -1, board, 6, 6, salvo));
    assertFalse(Validation.salvoCoordinateValidation(2, 2, board, 6, 6, salvo));
    assertFalse(Validation.salvoCoordinateValidation(5, 5, board, 6, 6, salvo));
  }

  /**
   * Tests inputs for AI salvo validation and if they correspond to a valid AI salvo coordinate
   */
  @Test
  public void testAiSalvoValidation() {
    List<Coord> coords = Arrays.asList(new Coord(0, 0), new Coord(1, 1));
    assertTrue(Validation.aiSalvoCoordinateValidation(2, 2, board, coords));
    assertTrue(Validation.aiSalvoCoordinateValidation(2, 1, board, coords));
    assertTrue(Validation.aiSalvoCoordinateValidation(1, 2, board, coords));
    assertFalse(Validation.aiSalvoCoordinateValidation(0, 0, board, coords));
    assertFalse(Validation.aiSalvoCoordinateValidation(5, 5, board, coords));
  }


}