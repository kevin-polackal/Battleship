package cs3500.pa03.model;

/**
 * To represent the result of a BattleShip game
 */
public enum GameResult {
  WIN("You beat your opponent!"), LOSS("You lost to your opponent!"),
  DRAW("You and your opponent drew!");
  public String reason;

  GameResult(String r) {
    this.reason = r;
  }
}
