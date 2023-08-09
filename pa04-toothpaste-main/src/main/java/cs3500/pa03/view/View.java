package cs3500.pa03.view;

import java.io.IOException;
import java.util.Objects;

/**
 * To represent user-facing functionality of the BattleSalvo program
 */
public class View {

  private final Appendable output;
  private final Reader reader;

  /**
   * Constructor for the View class, sets the input and output to the given Readable and Appendable
   *
   * @param input readable for reading in input
   * @param appendable appendable for writing output
   */
  public View(Readable input, Appendable appendable) {
    Readable input1 = Objects.requireNonNull(input);
    this.reader = new BattleShipReader(input1);
    this.output = Objects.requireNonNull(appendable);
  }

  /***Outputs the given information from the controller to the user
   *
   * @param toUser the information from the controller in string format
   */
  public void output(String toUser) {
    try {
      output.append(toUser + "\n"); // this may fail, hence the try-catch
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Takes in input from a readable
   *
   * @return a string representation of the readable input
   */
  public String input() {
    return reader.read();
  }
}
