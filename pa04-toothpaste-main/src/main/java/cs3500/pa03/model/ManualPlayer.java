package cs3500.pa03.model;

import java.util.List;

/**
 * To represent a manual player in the game of BattleShip
 */
public class ManualPlayer extends AbstractPlayer {
  private ManualPlayerSalvoData shotHolder;

  public ManualPlayer(String name, GameState board, boolean beingTested,
                      ManualPlayerSalvoData shotHolder) {
    super(name, board, beingTested);
    this.shotHolder = shotHolder;
  }

  /**
   * Returns this manual player's shots on the opponent's board.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    opponentShots = this.shotHolder.getSalvo();
    return this.shotHolder.getSalvo();
  }

}
