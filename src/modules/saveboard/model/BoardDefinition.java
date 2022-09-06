package modules.saveboard.model;

import core.model.chesspieces.Pesa;
import java.util.ArrayList;

/**
 *
 * @author Bernat Galmés Rubert
 */
public class BoardDefinition {
    public static final int INI_BOARD_SIZE = 8;
    public static final String[] T_PECES = {"bishop", "knight", "pawnn", "king", "queen", "rook", "superKing", "superQueen"};
    
    private final ArrayList<Pesa> pieces;
    private int boardSize = INI_BOARD_SIZE;
    
    public BoardDefinition() {
        pieces = new ArrayList<>();
    }
    
    public void addPiece(Pesa p) {
        getPieces().add(p);
    }
    
    public void clearPieces() {
        this.getPieces().clear();
    }
    
    public Pesa getPiece(int i) {
        return this.getPieces().get(i);
    }
    
    public int numPieces() {
        return this.getPieces().size();
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

    /**
     * @return the pieces
     */
    public ArrayList<Pesa> getPieces() {
        return pieces;
    }

}
