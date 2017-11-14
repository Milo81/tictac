package com.miloslavfritz.tictac;

import java.util.Objects;

/**
 * Represents the indivividual cell in the board.
 */
public class Cell {
    // current state of this cell (X, O or empty)
    public State content;

    public Cell() {
        content = State.EMPTY;
    }

    @Override
    public String toString() {
        switch (content) {
            case CROSS:
                return " X ";
            case NOUGHT:
                return " O ";
            case EMPTY:
                return "   ";
            default:
                throw new IllegalStateException("Unknown state");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return content == cell.content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}