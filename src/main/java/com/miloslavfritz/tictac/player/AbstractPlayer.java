package com.miloslavfritz.tictac.player;

import com.miloslavfritz.tictac.Board;
import com.miloslavfritz.tictac.Cell;
import com.miloslavfritz.tictac.State;

/**
 * Abstract superclass for all players.
 * To construct an player:
 * <ul>
 *     <li>Construct an instance (of its subclass) with the game Board</li>
 *     <li>Call setSeed() to set the computer's seed</li>
 *     <li>Call move() which returns the next move in an int[2] array of {row, col}.</li>
 * </ul>
 *
 * The implementation subclasses need to implement method move().
 * They shall not modify Cell[][], i.e., no side effect expected.
 * Assume that next move is available, i.e., not game-over yet.
 */
public abstract class AbstractPlayer implements Player {
   protected int ROWS = 3;  // number of rows
   protected int COLS = 3;  // number of columns
 
   protected Cell[][] cells; // the board's ROWS-by-COLS array of Cells
   protected State mySeed;    // computer's seed
   protected State oppSeed;   // opponent's seed
 
   /** Constructor with reference to game board */
   AbstractPlayer(Board board) {
      cells = board.cells;
   }
 
   /** Set/change the seed used by computer and opponent */
   public void setSeed(State seed) {
      this.mySeed = seed;
      oppSeed = (mySeed == State.CROSS) ? State.NOUGHT : State.CROSS;
   }

}