package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * A dependency injection to feed ManualPlayer information on user inputted salvos
 */
public class ManualPlayerSalvoData {
  private ArrayList<Coord> salvoCoords;

  public ManualPlayerSalvoData() {
    salvoCoords = new ArrayList<>();
  }

  /**
   * To add to the salvo
   *
   * @param x the x coordinate of the coordinate being added to the salvo
   * @param y the y coordinate of the coordinate being added to the salvo
   */
  public void enterSalvo(int x, int y) {
    salvoCoords.add(new Coord(x, y));
  }

  /**
   * Clears the entirety of the salvo
   */
  public void removeAll() {
    salvoCoords.clear();
  }

  /**
   * Determines whether there is a coordinate in the salvo with the given x and y parts
   *
   * @param x the x coordinate of a Coord
   * @param y the y coordinate of a Coord
   * @return whether there exists a Coord with the given x and y coordinates in the salvo
   */
  public boolean contains(int x, int y) {
    for (Coord c : salvoCoords) {
      if (c.getX() == x && c.getY() == y) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the manual player's salvo
   *
   * @return the list of coordinates representing a manual player's salvo
   */
  public ArrayList<Coord> getSalvo() {
    return this.salvoCoords;
  }
}
