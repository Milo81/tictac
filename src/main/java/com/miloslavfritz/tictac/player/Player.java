package com.miloslavfritz.tictac.player;


/**
 * Defines common player behaviour.
 */
public interface Player {

    /**
     * Gets next move that player do.
     *
     * @return int[2] of {row, col}
     */
    int[] move();
}
