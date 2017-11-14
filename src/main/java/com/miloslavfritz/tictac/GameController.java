package com.miloslavfritz.tictac;

import com.miloslavfritz.tictac.player.Player;

import java.io.PrintStream;

/**
 * Main controller for the game of tic tac toe.
 */
public class GameController {

    // here the gameController write the output
    private PrintStream outputStream;

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

    /**
     * Creates the gameCOntrollert
     *
     * @param outputStream printStream to write output to
     */
    public GameController(PrintStream outputStream) {
        this.outputStream = outputStream;
        this.board = new Board();

        // initialize stuff
        board.init();
        currentSide = State.CROSS;       // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
    }

    public void setPlayerX(Player playerX) {
        this.playerX = playerX;
    }

    public void setPlayerO(Player playerO) {
        this.playerO = playerO;
    }

    public Board getBoard() {
        return board;
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


    /**
     * Executes the main game loop.
     *
     * @param addDelay true if delay should be added after each round.
     */
    public void mainGameLoop(boolean addDelay) {
        do {
            int[] move;
            if (currentSide == State.CROSS) {
                move = playerX.move();
                outputStream.println("'X' moves to : " + (move[0] + 1) + " " + (move[1] + 1));
            } else {
                move = playerO.move();
                outputStream.println("'O' moves to : " + (move[0] + 1) + " " + (move[1] + 1));
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
                outputStream.println("'X' player won the game");
            } else if (currentState == GameState.NOUGHT_WON) {
                outputStream.println("'O' player won the game");
            } else if (currentState == GameState.DRAW) {
                outputStream.println("Draw.");
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
}
