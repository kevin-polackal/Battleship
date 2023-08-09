package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/***To represent an A.I player in battleship, who autonomously chooses its salvos
 */
public class AiPlayer extends AbstractPlayer {
  public AiPlayer(String name, GameState board, boolean beingTested) {
    super(name, board, beingTested);
  }

  /***Generates a salvo based upon the number of its ships still afloat
   *
   * @return a list of coordinates representing this A.I player's salvo
   */
  @Override
  public List<Coord> takeShots() {
    List<Coord> coords = new ArrayList<>();
    int shipCount = Math.min(board.getUnsunkShipCount(), board.getOpenOpponentSquares());
    Random randCoords = new Random();

    while (shipCount > 0 && hitsThisRound.size() > 0) {
      Coord c = hitsThisRound.remove(0);
      if (Validation.aiSalvoCoordinateValidation(c.getX() - 1, c.getY(), board, coords)) {
        coords.add(new Coord(c.getX() - 1, c.getY()));
        shipCount--;
      }
      if (Validation.aiSalvoCoordinateValidation(c.getX() + 1, c.getY(), board, coords)
          && shipCount > 0) {
        coords.add(new Coord(c.getX() + 1, c.getY()));
        shipCount--;
      }
      if (Validation.aiSalvoCoordinateValidation(c.getX(), c.getY() + 1, board, coords)
          && shipCount > 0) {
        coords.add(new Coord(c.getX(), c.getY() + 1));
        shipCount--;
      }
      if (Validation.aiSalvoCoordinateValidation(c.getX(), c.getY() - 1, board, coords)
          && shipCount > 0) {
        coords.add(new Coord(c.getX(), c.getY() - 1));
        shipCount--;
      }
    }

    while (shipCount > 0) {
      int x = randCoords.nextInt(0, board.getWidth());
      int y = randCoords.nextInt(0, board.getHeight());
      if (Validation.aiSalvoCoordinateValidation(x, y, board, coords)) {
        coords.add(new Coord(x, y));
        shipCount--;
      }
    }
    opponentShots = coords;
    return coords;
  }
}
