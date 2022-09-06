package modules.knight.model;

import core.model.game.BoardPosition;

/**
 *
 * @author Bernat Galmés Rubert
 */
public class BoardDefinition {
    public static final int INI_BOARD_SIZE = 8;
    
    private BoardPosition initialPosition;
    private int boardSize = INI_BOARD_SIZE;
    
    public BoardDefinition() {
        initialPosition = new BoardPosition(2, 2);
    }

    public BoardPosition getInitialPosition() {
        return initialPosition;
    }

    public void setInitialPosition(BoardPosition initialPosition) {
        this.initialPosition = initialPosition;
    }
    
    
    /**
     * @return the boardSize
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * @param boardSize the boardSize to set
     */
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }


}
