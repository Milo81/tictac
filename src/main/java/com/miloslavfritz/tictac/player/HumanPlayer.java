package com.miloslavfritz.tictac.player;

import com.miloslavfritz.tictac.Board;
import com.miloslavfritz.tictac.State;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Implementation of a human player that ask for next move from console.
 */
public class HumanPlayer extends AbstractPlayer {

    public HumanPlayer(Board board) {
        super(board);
    }

    @Override
    public int[] move() {
        // we will ask player for move from stdin

        Scanner s = new Scanner(System.in);

        do {
            if (mySeed == State.CROSS) {
                System.out.print("Player X: (row[1-3] column[1-3]): ");
            } else {
                System.out.print("Player O: (row[1-3] column[1-3]): ");
            }
            try {
                int row = s.nextInt() - 1;
                int col = s.nextInt() - 1;
                if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                        && cells[row][col].content == State.EMPTY) {
                    return new int[] {row, col};
                }
            } catch (InputMismatchException ignored) { }

            System.err.println("Invalid move, please try again !");
        } while(true);

    }
}
