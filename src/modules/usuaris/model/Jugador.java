/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules.usuaris.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author berna
 */
public class Jugador extends User {
    private List<Partida> partides;

    public Jugador(long id, String Nom, String mail, long numAccessos) {
        super(id, Nom, mail, numAccessos);
        partides = new ArrayList<>();
    }

    public void registrarPartida(Partida partida) {
        partides.add(partida);
    }
    
    public int nombrePartidesGuanyades() {
        int res = 0;
        for (Partida p : partides) {
            if (p.isGuanyador(this)) {
                res += 1;   
            }
        }
        
        return res;
    }
    
    public int nombrePartidesNoFinalitzades() {
        int res = 0;
        for (Partida p : partides) {
            if (p.isNoAcabada()) {
                res += 1;   
            }
        }
        
        return res;
    }
    public int nombrePartidesPerdudes() {
        int res = 0;
        for (Partida p : partides) {
            if (p.isPerdedor(this)) {
                res += 1;   
            }
        }
        
        return res;
    }
    
    
    public Partida getDarreraPartida() {
        if (this.partides == null || this.partides.size() == 0){
            return null;
        }
        return this.partides.get(0);
    }
    
}
