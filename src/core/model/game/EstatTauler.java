package core.model.game;

import core.utils.PositionCode;
import core.model.chesspieces.Pesa;
import core.model.chesspieces.Rei;

/**
 * 
 * @author Bernat Galmés Rubert
 */
public class EstatTauler {
    private Pesa[][] tauler;
    
    public EstatTauler(int n) {
        this.tauler = new Pesa[n][n];
    }
    
    public void colocarPesa(String codiPosicio, Pesa pesa)
    {
        PositionCode pos = new PositionCode(codiPosicio);
        tauler[pos.getRow()-1][pos.getColumn()-1] = pesa;                
    }
    
    public int findGuanyador(){
        boolean j1jugant = false;
        boolean j2jugant = false;
        
        for (Pesa[] fila : tauler){
            for (Pesa p : fila) {
                if (p instanceof Rei) {
                    if (p.getJugador() == 1) {
                        j1jugant = true;
                    
                    }else {
                        j2jugant = true;
                    }
                }
            }
        }
        
        if (j1jugant && j2jugant){
            return -1;
        } else if (!j1jugant){
            return 2;
        } else{
            return 1;
        }
    }
    
    public boolean hasRei(int jugador ){
        for (Pesa[] fila : tauler){
            for (Pesa p : fila) {
                if (p instanceof Rei) {
                    if (p.getJugador() == jugador) {
                        return true;
                    
                    }
                }
            }
        }
        
        return false;
    }
    
    public Pesa[][] getTauler() {
        return tauler;
    }
    
    
}
