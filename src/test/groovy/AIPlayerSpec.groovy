import com.miloslavfritz.tictac.player.AIPlayerMinimax
import com.miloslavfritz.tictac.Board
import com.miloslavfritz.tictac.State
import spock.lang.Specification

/**
 * Specification of AI minimax implementation.
 * This is the test of simple scenarios to check if AI works for these.
 */
class AIPlayerSpec extends Specification {


    def "Player X will win with a vertical line"() {
        given:
        def board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[1][0].content = State.CROSS
        board.cells[1][1].content = State.NOUGHT
        board.cells[2][2].content = State.NOUGHT
//        board.cells[2][0].content = State.CROSS
        def ai = new AIPlayerMinimax(board)
        ai.seed = State.CROSS

        expect:
        ai.move() == [2,0] as int[]
    }

    def "Player O will win with a horizontal line"() {
        given:
        def board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[1][2].content = State.CROSS
        board.cells[1][0].content = State.NOUGHT
//        board.cells[1][1].content = State.NOUGHT
        board.cells[1][2].content = State.NOUGHT
        board.cells[2][0].content = State.CROSS
        def ai = new AIPlayerMinimax(board)
        ai.seed = State.NOUGHT

        expect:
        ai.move() == [1,1] as int[]
    }

    def "Player X will win with a diagonal line"() {
        given:
        def board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[1][1].content = State.CROSS
//        board.cells[2][2].content = State.CROSS
        board.cells[1][0].content = State.NOUGHT
        board.cells[2][0].content = State.NOUGHT
        def ai = new AIPlayerMinimax(board)
        ai.seed = State.CROSS

        expect:
        ai.move() == [2,2] as int[]
    }

    def "try how AI will react to draw situation"() {
        given:
        def board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[0][1].content = State.NOUGHT
        board.cells[0][2].content = State.CROSS
        board.cells[1][0].content = State.NOUGHT
        board.cells[1][1].content = State.NOUGHT
        board.cells[1][2].content = State.CROSS
//        board.cells[2][0].content = State.CROSS
        board.cells[2][1].content = State.CROSS
        board.cells[2][2].content = State.NOUGHT
        def ai = new AIPlayerMinimax(board)
        ai.seed = State.CROSS

        expect:
        ai.move() == [2,0] as int[]
    }

    def "reaction to invalid board"() {
        given:
        def board = new Board()
        board.cells[0][0].content = State.CROSS
        board.cells[0][1].content = State.CROSS
        board.cells[0][2].content = State.CROSS
        board.cells[1][0].content = State.CROSS
        board.cells[1][1].content = State.CROSS
        board.cells[1][2].content = State.CROSS
        board.cells[2][0].content = State.CROSS
        board.cells[2][1].content = State.CROSS
        board.cells[2][2].content = State.CROSS
        def ai = new AIPlayerMinimax(board)
        ai.seed = State.CROSS

        expect:
        ai.move() == [-1,-1] as int[]
    }

}