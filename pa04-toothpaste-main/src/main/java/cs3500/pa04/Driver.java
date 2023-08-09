package cs3500.pa04;

import cs3500.pa03.controller.BattleShipController;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.GameState;
import cs3500.pa04.controller.ProxyController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - Either can consist of two or no arguments. If given two arguments,
   *            must be a valid host and port
   */
  public static void main(String[] args) {
    Controller controller;
    int port;
    if (args.length == 2) {
      Socket server;

      try {
        port = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        System.out.println("Invalid port number!");
        return;
      }
      try {
        server = new Socket(args[0], port);
        controller =
            new ProxyController(server,
                new AiPlayer("kevin-polackal", new GameState(), false));
        controller.run();
      } catch (IOException e) {
        System.out.println("Error creating server!");
        return;
      }


    } else if (args.length == 0) {
      controller =
          new BattleShipController(new InputStreamReader(System.in), new PrintStream(System.out),
              false);
    } else {
      System.out.println("Invalid amount of command line arguments, sorry!");
      return;
    }
    controller.run();
  }
}