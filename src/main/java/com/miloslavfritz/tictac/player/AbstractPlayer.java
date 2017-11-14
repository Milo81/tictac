package com.miloslavfritz.tictac.player;

import com.miloslavfritz.tictac.Board;
import com.miloslavfritz.tictac.Cell;
import com.miloslavfritz.tictac.State;

/**
 * Abstract superclass for all players.
 * To construct a player:
 * <ul>
 *     <li>Construct an instance (of its subclass) with the game Board</li>
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
   protected State mySide;
   protected State oponentSide;

   /**
    * Constructor for abstract player.
    *
    * @param board   board to use for this player.
    * @param theSide side (X or O) this player will play
    */
   AbstractPlayer(Board board, State theSide) {
      cells = board.cells;
      this.mySide = theSide;
      oponentSide = (mySide == State.CROSS) ? State.NOUGHT : State.CROSS;
   }


}