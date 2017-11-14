package com.miloslavfritz.tictac.player;

import com.miloslavfritz.tictac.State;

/**
 * Define player behaviour. At the moment player can just make moves.
 */
public interface Player {

    /**
     * Gets next move that player do.
     *
     * @return int[2] of {row, col}
     */
    int[] move();

    /**
     * Sets the side which the player will play.
     *
     * @param seed
     */
    void setSeed(State seed);
}
