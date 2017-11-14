package com.miloslavfritz.tictac;

import com.miloslavfritz.tictac.player.AIPlayerMinimax;
import com.miloslavfritz.tictac.player.HumanPlayer;
import com.miloslavfritz.tictac.player.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main class for the Tic-Tac-Toe
 * Keeps the current state of the game, implements main game loop.
 */
public class GameMain {
   // game board
   private Board board;
   // current state of game (win, draw, playing)
   private GameState currentState;
   // the current side playing (X or O)
   private State currentSide;

   // player playing X side
   private Player playerX;
   // player playing O side
   private Player playerO;
 

   /** Constructor to setup the game */
   private GameMain() {
      board = new Board();  // allocate game-board
 
      // Initialize the game-board and current status
      board.init();  // clear the board contents
      currentSide = State.CROSS;       // CROSS plays goes first
      currentState = GameState.PLAYING; // ready to play
   }

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
         } catch (InputMismatchException ignored) { }

         System.err.println("Invalid input, please select 1, 2 or 3 !");
      } while (true);
   }

   /**
    * Updates the game state after the player playing side "theSide" has moved
    * @param theSide side (O or X) to update game state for
    */
   private void updateGame(State theSide) {
      if (board.hasWon(theSide)) {  // check for win
         currentState = (theSide == State.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
      } else if (board.isDraw()) {  // check for draw
         currentState = GameState.DRAW;
      }
      // Otherwise, no change to current state (still GameState.PLAYING).
   }

   /** Implements the main game loop */
   private void maingGameLoop(boolean addDelay) {
      do {
         int[] move;
         if (currentSide == State.CROSS) {
            move = playerX.move();
            System.out.println("'X' moves to : " + (move[0] + 1) + " " + (move[1] + 1));
         } else {
            move = playerO.move();
            System.out.println("'O' moves to : " + (move[0] + 1) + " " + (move[1] + 1));
         }

         // update the board
         int row = move[0];
         int col = move[1];
         board.cells[row][col].content = currentSide;
         board.currentRow = row;
         board.currentCol = col;
         System.out.println(board);
         System.out.println();

         // check if somebody already won
         updateGame(currentSide);

         // Print message if game-over
         if (currentState == GameState.CROSS_WON) {
            System.out.println("'X' player won the game");
         } else if (currentState == GameState.NOUGHT_WON) {
            System.out.println("'O' player won the game");
         } else if (currentState == GameState.DRAW) {
            System.out.println("Draw.");
         }
         // change players
         currentSide = (currentSide == State.CROSS) ? State.NOUGHT : State.CROSS;

         if (addDelay) {
            try {
               Thread.sleep(2000);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      } while (currentState == GameState.PLAYING);

   }
 
   public static void main(String[] args) {
      // construct the main game object
      GameMain gm = new GameMain();

      // ask for game mode
      GameMode mode = askMode();
      System.out.println("Game Starting ...");
      System.out.println(gm.board);

      // create players
      switch (mode) {
         case AI_VS_AI:
            gm.playerX = new AIPlayerMinimax(gm.board, State.CROSS);
            gm.playerO = new AIPlayerMinimax(gm.board, State.NOUGHT);
            break;
         case AI_VS_HUMAN:
            gm.playerX = new HumanPlayer(gm.board, State.CROSS);
            gm.playerO = new AIPlayerMinimax(gm.board, State.NOUGHT);
            break;
         case HUMAN_VS_HUMAN:
            gm.playerX = new HumanPlayer(gm.board, State.CROSS);
            gm.playerO = new HumanPlayer(gm.board, State.NOUGHT);
            break;
         default:
            throw new IllegalStateException("Illegal game mode");
      }

      // and execute main loop
      gm.maingGameLoop(mode == GameMode.AI_VS_AI);
   }
}