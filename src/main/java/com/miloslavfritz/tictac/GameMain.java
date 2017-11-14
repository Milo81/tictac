package com.miloslavfritz.tictac;

import com.miloslavfritz.tictac.player.AIPlayerMinimax;
import com.miloslavfritz.tictac.player.HumanPlayer;
import com.miloslavfritz.tictac.player.Player;

import java.lang.management.PlatformLoggingMXBean;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * The main class for the Tic-Tac-Toe
 * It acts as the overall controller of the game.
 */
public class GameMain {
   private Board board;            // the game board
   private GameState currentState; // the current state of the game (of enum GameState)
   private State currentSide;     // the current side playing (X or O)

   private Player playerX;
   private Player playerO;
 

   /** Constructor to setup the game */
   private GameMain() {
      board = new Board();  // allocate game-board
 
      // Initialize the game-board and current status
      initGame();
   }

   /** Initialize the game-board contents and the current states */
   private void initGame() {
      board.init();  // clear the board contents
      currentSide = State.CROSS;       // CROSS plays first
      currentState = GameState.PLAYING; // ready to play
   }

   /**
    * Ask for the mode of game, AI vs AI, AI vs Human, Human vs Human.
    */
   private GameMode askMode() {
      System.out.println("Please choose game mode:");
      System.out.println("1 - AI versus AI");
      System.out.println("2 - AI versus Human");
      System.out.println("3 - Human versus Human");

      Scanner s = new Scanner(System.in);

      do {
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

         System.err.print("Invalid input, please select 1, 2 or 3");
      } while (true);
   }

   /**
    * Ask the user for move.
    *
    * @return {row, col} of the entered move
    */
   private int[] askMove(State player) {
      Scanner s = new Scanner(System.in);

      do {
         if (player == State.CROSS) {
            System.out.print("Player X: (row[1-3] column[1-3]): ");
         } else {
            System.out.print("Player O: (row[1-3] column[1-3]): ");
         }
         try {
            int row = s.nextInt() - 1;
            int col = s.nextInt() - 1;
            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                    && board.cells[row][col].content == State.EMPTY) {
               return new int[] {row, col};
            }
         } catch (InputMismatchException ignored) { }

         System.err.println("Invalid move, please try again !");
      } while(true);

   }

   /** Update the currentState after the player with "theSeed" has moved */
   private void updateGame(State theSeed) {
      if (board.hasWon(theSeed)) {  // check for win
         currentState = (theSeed == State.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
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
         board.cells[move[0]][move[1]].content = currentSide;
         board.currentRow = move[0];
         board.currentCol = move[1];
         System.out.println(board);

         // check if somebody already won
         updateGame(currentSide);
         // Print message if game-over
         if (currentState == GameState.CROSS_WON) {
            System.out.println("'X' won! Bye!");
         } else if (currentState == GameState.NOUGHT_WON) {
            System.out.println("'O' won! Bye!");
         } else if (currentState == GameState.DRAW) {
            System.out.println("It's Draw! Bye!");
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
      GameMode mode = gm.askMode();
      System.out.println("Game Starting ...");
      System.out.println(gm.board);

      // create players
      switch (mode) {
         case AI_VS_AI:
            gm.playerX = new AIPlayerMinimax(gm.board);
            gm.playerX.setSeed(State.CROSS);
            gm.playerO = new AIPlayerMinimax(gm.board);
            gm.playerO.setSeed(State.NOUGHT);
            break;
         case AI_VS_HUMAN:
            gm.playerX = new HumanPlayer(gm.board);
            gm.playerX.setSeed(State.CROSS);
            gm.playerO = new AIPlayerMinimax(gm.board);
            gm.playerO.setSeed(State.NOUGHT);
            break;
         case HUMAN_VS_HUMAN:
            gm.playerX = new HumanPlayer(gm.board);
            gm.playerX.setSeed(State.CROSS);
            gm.playerO = new HumanPlayer(gm.board);
            gm.playerO.setSeed(State.NOUGHT);
            break;
         default:
            throw new IllegalStateException("Illegal game mode");
      }

      // and execute main loop
      gm.maingGameLoop(mode == GameMode.AI_VS_AI);

   }
}