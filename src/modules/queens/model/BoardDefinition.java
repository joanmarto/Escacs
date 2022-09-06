package modules.queens.model;

import core.model.chesspieces.Pesa;
import core.model.chesspieces.Reina;
import java.util.ArrayList;

/**
 *
 * @author Bernat Galmés Rubert
 */
public class BoardDefinition {
    public static final int INI_BOARD_SIZE = 8;
    
    private ArrayList<Pesa> pieces;
    private int boardSize = INI_BOARD_SIZE;
    
    public BoardDefinition() {
        this.updatePieces();
    }
    
    
    public void addPiece(Pesa p) {
        getPieces().add(p);
    }
    
    public void clearPieces() {
        this.getPieces().clear();
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
        this.updatePieces();
    }

    /**
     * @return the pieces
     */
    public ArrayList<Pesa> getPieces() {
        return pieces;
    }
    
    private void updatePieces() {
        pieces = new ArrayList<>();
        for (int i=0; i < this.boardSize; i++){
           pieces.add(new Reina());
        }
    }

}
