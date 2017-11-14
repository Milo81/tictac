package com.miloslavfritz.tictac;

import com.miloslavfritz.tictac.player.AIPlayerMinimax;
import com.miloslavfritz.tictac.player.HumanPlayer;
import com.miloslavfritz.tictac.player.Player;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Entry point of the application.
 */
public class Main {

   /**
    * Ask for the mode of game, AI vs AI, AI vs Human, Human vs Human.
    */
   private static GameMode askMode() {
      do {
         Scanner s = new Scanner(System.in);
         System.out.println("Please choose game mode:");
         System.out.println("1 - AI versus AI");
         System.out.println("2 - AI versus Human");
         System.out.println("3 - Human versus Human");
         try {
            int i = s.nextInt();
            if (i >= 1 && i <= 3) {
               switch (i) {
                  case 1:
                     return GameMode.AI_VS_AI;
                  case 2:
                     return GameMode.AI_VS_HUMAN;
                  case 3:
                     return GameMode.HUMAN_VS_HUMAN;
               }
            }
         } catch (NoSuchElementException ignored) { }


         System.err.println("Invalid input, please select 1, 2 or 3 !");
         try {
            Thread.sleep(100);
         } catch (InterruptedException ignored) { }
      } while (true);
   }


   public static void main(String[] args) {

      // ask for game mode
      GameMode mode = askMode();

      System.out.println("Game Starting ...");
      GameController gameController = new GameController(System.out);
      System.out.println(gameController.getBoard());

      Player playerX;
      Player playerO;

      // create players
      switch (mode) {
         case AI_VS_AI:
            playerX = new AIPlayerMinimax(gameController.getBoard(), State.CROSS);
            playerO = new AIPlayerMinimax(gameController.getBoard(), State.NOUGHT);
            break;
         case AI_VS_HUMAN:
            playerX = new HumanPlayer(gameController.getBoard(), State.CROSS);
            playerO = new AIPlayerMinimax(gameController.getBoard(), State.NOUGHT);
            break;
         case HUMAN_VS_HUMAN:
            playerX = new HumanPlayer(gameController.getBoard(), State.CROSS);
            playerO = new HumanPlayer(gameController.getBoard(), State.NOUGHT);
            break;
         default:
            throw new IllegalStateException("Illegal game mode");
      }

      // assign players to controller
      gameController.setPlayerX(playerX);
      gameController.setPlayerO(playerO);

      // and execute main loop
      gameController.mainGameLoop(mode == GameMode.AI_VS_AI);
   }
}