package cs3500.pa03.view;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * Test for read in the BattleShipReader
 */
class BattleShipReaderTest {
  /**
   * Tests the read functionality by feeding in data from a given file
   */
  @Test
  public void testRead() {
    try {
      BattleShipReader reader =
          new BattleShipReader(new FileReader("src/main/resources/viewInput.sr"));
      assertEquals(reader.read(), "Hello World!");
    } catch (IOException e) {
      fail();
    }

  }

}