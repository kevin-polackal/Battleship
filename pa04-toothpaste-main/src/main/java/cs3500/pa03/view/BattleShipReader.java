package cs3500.pa03.view;

import java.util.Objects;
import java.util.Scanner;

/**
 * Custom implementation of the Reader interface
 */
public class BattleShipReader implements Reader {
  private final Scanner scanner;

  public BattleShipReader(Readable readable) {
    Readable readable1 = Objects.requireNonNull(readable);
    scanner = new Scanner(readable1);
  }

  /**
   * Handles reading in data from a Readable
   *
   * @return the string representation of the next line from the readable
   */
  @Override
  public String read() {
    return scanner.nextLine();
  }
}
