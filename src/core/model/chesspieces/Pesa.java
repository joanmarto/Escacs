package core.model.chesspieces;

import core.view.CellDrawable;

public abstract class Pesa implements CellDrawable {

    // jugador de la pesa 1 (blanc) o 2 (negre)
    protected int jugador;


    public int getJugador() {
        return jugador;
    }

    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    ///
    ///Funcions estàtiques de Pesa
    ///

    public static Pesa getPesa(String nomPesa) throws badSelection {
        Pesa pesa;
        switch (nomPesa) {
            case "bishop" ->
                pesa = new Alfil();
            case "knight", "kight" ->
                pesa = new Cavall();
            case "pawnn" ->
                pesa = new Peo();
            case "king" ->
                pesa = new Rei();
            case "queen" ->
                pesa = new Reina();
            case "rook" ->
                pesa = new Torre();
            case "superKing" ->
                pesa = new SuperRei();
            case "superQueen" ->
                pesa = new SuperReina();
            default ->
                throw new badSelection();
        }

        return pesa;
    }

    public static Pesa getPesa(String nomPesa, int jugador) throws badSelection {
        Pesa pesa = getPesa(nomPesa);

        pesa.setJugador(jugador);
        return pesa;
    }

    public static class badSelection extends Exception {

    }
}
