package cs3500.pa03.view;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa04.MockAppendable;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;

/**
 * Tests for the view class
 */
class ViewTest {
  View view;

  /**
   * Tests that the view's input and output are properly functioning
   */
  @Test
  public void testInputAndOutput() {
    StringWriter writer = new StringWriter();
    try {
      view =
          new View(new FileReader("src/main/resources/viewInput.sr"), writer);
    } catch (IOException e) {
      fail();
    }
    view.output(view.input());
    assertEquals(writer.toString(), "Hello World!\n");
  }

  /**
   * Mock Test to simulate an error when trying to write
   */
  @Test
  public void testError() {
    try {
      view = new View(new FileReader("src/main/resources/viewInput.sr"), new MockAppendable());
    } catch (IOException e) {
      fail();
    }

    Exception exc =
        assertThrows(RuntimeException.class, () -> view.output("Test"), "Mock throwing an error");
    assertEquals("java.io.IOException: Mock throwing an error", exc.getMessage());

  }
}