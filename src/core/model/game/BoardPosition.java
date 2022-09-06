package core.model.game;

/**
 *
 * @author Bernat Galmés Rubert
 */
public class BoardPosition {
    public int x;
    public int y;

    public BoardPosition() {
    }

    public BoardPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public boolean equals(Object o){
        return this.equals((BoardPosition)o);
        
    }
    
    public boolean equals(BoardPosition o){
        return this.x == o.x && this.y == o.y;
    }
}
