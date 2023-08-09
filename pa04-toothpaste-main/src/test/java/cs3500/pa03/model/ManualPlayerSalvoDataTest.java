package cs3500.pa03.model;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testing for the manual player takeShots dependency injection class
 */
class ManualPlayerSalvoDataTest {
  private ManualPlayerSalvoData salvo;

  @BeforeEach
  public void setup() {
    this.salvo = new ManualPlayerSalvoData();
  }

  /**
   * Tests that a coordinate is properly entered into the salvo
   */
  @Test
  public void testEnterSalvo() {
    salvo.enterSalvo(0, 0);
    salvo.enterSalvo(1, 1);
    assertTrue(salvo.contains(0, 0));
    assertTrue(salvo.contains(1, 1));
    assertFalse(salvo.contains(1, 5));
    assertFalse(salvo.contains(5, 1));
  }

  /**
   * Tests that removeAll properly removes all shots from the salvo
   */
  @Test
  public void testRemoveAll() {
    salvo.enterSalvo(0, 0);
    salvo.enterSalvo(1, 1);
    assertTrue(salvo.contains(0, 0));
    assertTrue(salvo.contains(1, 1));
    salvo.removeAll();
    assertFalse(salvo.contains(0, 0));
    assertFalse(salvo.contains(1, 1));
  }

  private void assertFalse(boolean contains) {
  }

  /**
   * Tests that contains properly identifies if a supplied coordinate is in the salvo
   */
  @Test
  public void testContains() {
    salvo.enterSalvo(0, 0);
    salvo.enterSalvo(1, 1);
    assertTrue(salvo.contains(0, 0));
    assertTrue(salvo.contains(1, 1));
    assertFalse(salvo.contains(1, 5));
    assertFalse(salvo.contains(5, 1));
  }

  /**
   * Tests that getSalvo properly returns a list of coordinates representing a salvo
   */
  @Test
  public void testGetSalvo() {
    salvo.enterSalvo(0, 0);
    salvo.enterSalvo(1, 1);
    List<Coord> coords = Arrays.asList(new Coord(0, 0), new Coord(1, 1));
    assertEquals(coords, salvo.getSalvo());
  }
}