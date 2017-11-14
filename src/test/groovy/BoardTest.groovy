import com.miloslavfritz.tictac.Board
import com.miloslavfritz.tictac.State
import spock.lang.See
import spock.lang.Specification

/**
 * Specification of board states.
 */
class BoardStateSpec extends Specification {

    @See("TDD Scenario 1 - Bame board creation phase")
    def "initial game board creation"() {
        when:
        Board board = new Board()

        then: "all cells are initially empty"
        board.cells.every{row -> row.every { col -> col.content == State.EMPTY }}
    }


    @See("TDD Scenario 1 - Bame board creation phase")
    def "reinitialization of game board after end of game"() {
        given: "board is dirty"
        Board board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[0][1].content = State.CROSS
        board.cells[0][2].content = State.CROSS
        board.cells[1][0].content = State.NOUGHT
        board.cells[1][1].content = State.CROSS
        board.cells[1][2].content = State.CROSS


        when: "calling the init method"
        board.init()

        then: "all cells are empty"
        board.cells.every{row -> row.every { col -> col.content == State.EMPTY }}
    }


    @See("TDD Scenario 2 - Player X won with vertical line")
    def "Player X won with vertical line"() {
        given: "board in in state for X player to win"
        Board board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[1][0].content = State.CROSS
        board.cells[1][1].content = State.NOUGHT
        board.cells[2][2].content = State.NOUGHT
        board.cells[2][0].content = State.CROSS
        board.currentRow = 2
        board.currentCol = 0

        expect:
        board.hasWon(State.CROSS)
    }

    @See("TDD Scenario 3 - Player O won with horizontal line")
    def "Player O won with horizontal line"() {
        given: "board in in state for O player to win"
        Board board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[1][2].content = State.CROSS
        board.cells[1][0].content = State.NOUGHT
        board.cells[1][1].content = State.NOUGHT
        board.cells[1][2].content = State.NOUGHT
        board.cells[2][0].content = State.CROSS
        board.currentRow = 1
        board.currentCol = 0

        expect:
        board.hasWon(State.NOUGHT)
    }

    @See("TDD Scenario 4 - Player X won with diagonal line")
    def "Player X won with diagonal line"() {
        given: "board in in state for X player to win"
        Board board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[1][1].content = State.CROSS
        board.cells[2][2].content = State.CROSS
        board.cells[1][0].content = State.NOUGHT
        board.cells[2][0].content = State.NOUGHT
        board.currentRow = 0
        board.currentCol = 0

        expect:
        board.hasWon(State.CROSS)
    }

    @See("TDD Scenario 5 - Game ends with a draw")
    def "checking of a draw condition"() {
        given:"board in a draw condition"
        Board board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[0][1].content = State.NOUGHT
        board.cells[0][2].content = State.CROSS
        board.cells[1][0].content = State.NOUGHT
        board.cells[1][1].content = State.NOUGHT
        board.cells[1][2].content = State.CROSS
        board.cells[2][0].content = State.CROSS
        board.cells[2][1].content = State.CROSS
        board.cells[2][2].content = State.NOUGHT

        expect:
        board.isDraw()
    }

}
