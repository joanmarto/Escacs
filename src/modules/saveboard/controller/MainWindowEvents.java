package modules.saveboard.controller;

import core.model.chesspieces.*;
import core.view.CellDrawable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modules.saveboard.model.BoardDefinition;
import modules.saveboard.view.MainWindow;

/**
 *
 * @author Bernat Galmés Rubert
 */
public class MainWindowEvents implements ActionListener, ChangeListener {

    private MainWindow view;
    private BoardDefinition model;
    private CellDrawable[][] resultBoard;
    private boolean trobada;

    public MainWindowEvents(MainWindow view, BoardDefinition model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Valor de las casillas
     *
     * 0 -> Casilla disponible
     *
     * 1 -> Casilla con alfil
     *
     * 2 -> Casilla con caballo
     *
     * 3 -> Casilla con un peón
     *
     * 4 -> Casilla con Rey
     *
     * 5 -> Casilla con Reina
     *
     * 6 -> Casilla con Torre
     *
     * 7 -> Casilla con SuperRey
     *
     * 8 -> Casilla con SuperDama
     *
     */
    public void calculo() {
        resultBoard = new CellDrawable[model.getBoardSize()][model.getBoardSize()];

        //Creamos una matriz a inicializada con ceros
        int[][] t = new int[model.getBoardSize()][model.getBoardSize()];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                t[i][j] = 0;
            }
        }

        //Creamos un array de números para representar a las piezas
        int[] p = new int[model.numPieces()];
        for (int i = 0; i < p.length; i++) {
            switch (model.getPiece(i).toString()) {
                case "Alfil":
                    p[i] = 1;
                    break;
                case "Cavall":
                    p[i] = 2;
                    break;
                case "Gat":
                    p[i] = 3;
                    break;
                case "Rei":
                    p[i] = 4;
                    break;
                case "Reina":
                    p[i] = 5;
                    break;
                case "Torre":
                    p[i] = 6;
                    break;
                case "SuperRei":
                    p[i] = 7;
                    break;
                case "SuperReina":
                    p[i] = 8;
                    break;
            }
        }
        trobada = false; //Variable que nos indicará si hemos encontrado una solución

        //Comprobamos si se han añadido piezas y llamamos al backtracking
        if (p.length > 0) {
            backtracking(p, t, 0);
        } else {
            trobada = true;
        }
    }

    /**
     *
     * @param p array con los identificadores de piezas a colocar
     * @param t matriz con los resultados
     * @param k nivel del arbol
     */
    public void backtracking(int p[], int t[][], int k) {
        int fila = 0;
        int columna = 0;
        while (fila < t.length && columna < t.length) {
            //Comprobamos que es una casilla libre
            if (t[fila][columna] == 0) {
                t[fila][columna] = p[k];

                if (k < p.length - 1 && canComplete(t)) {
                    backtracking(p, t, k + 1);
                }
                if (k < p.length - 1) {
                    t[fila][columna] = 0;
                }

                if (!trobada && k == p.length - 1 && isSolution(t)) {
                    trobada = true;
                    display(t);
                } else {
                    if (k == p.length - 1) {
                        t[fila][columna] = 0;
                    }
                }
            }

            //incrementamos la fila o la columna
            if (columna < t.length - 1) {
                columna++;
            } else {
                columna = 0;
                fila++;
            }
        }
    }

    private boolean canComplete(int t[][]) {
        //Suponemos que el backtracking puede continuar
        boolean continua = true;

        //Realizamos la comprobación
        for (int i = 0; continua && i < t.length; i++) {
            for (int j = 0; continua && j < t.length; j++) {
                switch (t[i][j]) {
                    case 1: //Alfil
                        continua = checkBishop(t, i, j);
                        break;
                    case 2: //Cavallo
                        continua = checkKnight(t, i, j);
                        break;
                    case 3: //Peón
                        continua = checkPawn(t, i, j);
                        break;
                    case 4: //Rey
                        continua = checkKing(t, i, j);
                        break;
                    case 5: //Reina
                        continua = checkQueen(t, i, j);
                        break;
                    case 6: //Torre
                        continua = checkRook(t, i, j);
                        break;
                    case 7: //SuperRey
                        continua = checkSuperKing(t, i, j);
                        break;
                    case 8: //SuperDama
                        continua = checkSuperQueen(t, i, j);
                        break;
                }
            }
        }
        return continua;
    }

    private boolean isSolution(int t[][]) {
        //Podemos reutilizar el código de canComplete(), ya que al fin y al cabo 
        //la comprobación que hacemos es la misma
        return canComplete(t);
    }

    private void display(int t[][]) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                switch (t[i][j]) {
                    case 1:
                        resultBoard[i][j] = new Alfil();
                        break;
                    case 2:
                        resultBoard[i][j] = new Cavall();
                        break;
                    case 3:
                        resultBoard[i][j] = new Peo();
                        break;
                    case 4:
                        resultBoard[i][j] = new Rei();
                        break;
                    case 5:
                        resultBoard[i][j] = new Reina();
                        break;
                    case 6:
                        resultBoard[i][j] = new Torre();
                        break;
                    case 7:
                        resultBoard[i][j] = new SuperRei();
                        break;
                    case 8:
                        resultBoard[i][j] = new SuperReina();
                        break;
                    default:
                        resultBoard[i][j] = null;
                }
            }
        }
    }

    /**
     * Comprueba si el alfil ataca alguna figura del tablero.
     *
     * @param t
     * @param fila
     * @param columna
     * @return true si no come a nadie
     */
    private boolean checkBishop(int t[][], int fila, int columna) {
        for (int k = 1; (columna - k >= 0) && (fila - k >= 0); k++) {
            if (t[fila - k][columna - k] > 0) {
                return false;
            }
        }
        for (int k = 1; (fila - k >= 0) && (columna + k < t.length); k++) {
            if (t[fila - k][columna + k] > 0) {
                return false;
            }
        }
        for (int k = 1; (fila + k < t.length) && (columna - k >= 0); k++) {
            if (t[fila + k][columna - k] > 0) {
                return false;
            }
        }
        for (int k = 1; (fila + k < t.length) && (columna + k < t.length); k++) {
            if (t[fila + k][columna + k] > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si el caballo ataca alguna figura del tablero.
     *
     * @param t
     * @param fila
     * @param columna
     * @return true si no come a nadie
     */
    private boolean checkKnight(int t[][], int fila, int columna) {
        if ((fila - 2 >= 0) && (columna - 1 >= 0) && (t[fila - 2][columna - 1] > 0)) {
            return false;
        }
        if ((fila - 2 >= 0) && (columna + 1 < t.length) && (t[fila - 2][columna + 1] > 0)) {
            return false;
        }
        if ((fila + 2 < t.length) && (columna - 1 >= 0) && (t[fila + 2][columna - 1] > 0)) {
            return false;
        }
        if ((fila + 2 < t.length) && (columna + 1 < t.length) && (t[fila + 2][columna + 1] > 0)) {
            return false;
        }
        if ((fila - 1 >= 0) && (columna - 2 >= 0) && (t[fila - 1][columna - 2] > 0)) {
            return false;
        }
        if ((fila + 1 < t.length) && (columna - 2 >= 0) && (t[fila + 1][columna - 2] > 0)) {
            return false;
        }
        if ((fila - 1 >= 0) && (columna + 2 < t.length) && (t[fila - 1][columna + 2] > 0)) {
            return false;
        }
        if ((fila + 1 < t.length) && (columna + 2 < t.length) && (t[fila + 1][columna + 2] > 0)) {
            return false;
        }
        return true;
    }

    /**
     * Comprueba si el peón ataca alguna figura del tablero.
     *
     * @param t
     * @param fila
     * @param columna
     * @return true si no come a nadie
     */
    private boolean checkPawn(int t[][], int fila, int columna) {
        if (((fila - 1 >= 0) && columna - 1 >= 0) && (t[fila - 1][columna - 1] > 0)) {
            return false;
        }
        if ((fila - 1 >= 0) && (columna + 1 < t.length) && (t[fila - 1][columna + 1] > 0)) {
            return false;
        }
        return true;
    }

    /**
     * Comprueba si el rey ataca alguna figura del tablero.
     *
     * @param t
     * @param fila
     * @param columna
     * @return true si no come a nadie
     */
    private boolean checkKing(int t[][], int fila, int columna) {
        if (((fila - 1 >= 0) && columna - 1 >= 0) && (t[fila - 1][columna - 1] > 0)) {
            return false;
        }
        if ((fila - 1 >= 0) && (t[fila - 1][columna] > 0)) {
            return false;
        }
        if ((fila - 1 >= 0) && (columna + 1 < t.length) && (t[fila - 1][columna + 1] > 0)) {
            return false;
        }

        if ((columna - 1 >= 0) && (t[fila][columna - 1] > 0)) {
            return false;
        }
        if ((columna + 1 < t.length) && (t[fila][columna + 1] > 0)) {
            return false;
        }

        if ((fila + 1 < t.length) && (columna - 1 >= 0) && (t[fila + 1][columna - 1] > 0)) {
            return false;
        }
        if ((fila + 1 < t.length) && (t[fila + 1][columna] > 0)) {
            return false;
        }
        if ((fila + 1 < t.length) && (columna + 1 < t.length) && (t[fila + 1][columna + 1] > 0)) {
            return false;
        }

        return true;
    }

    /**
     * Comprueba si la reina ataca alguna figura del tablero.
     *
     * @param t
     * @param fila
     * @param columna
     * @return true si no come a nadie
     */
    private boolean checkQueen(int t[][], int fila, int columna) {
        //Comprobamos la horizontal y la vertical
        for (int k = 0; k < t.length; k++) {
            if (t[fila][k] > 0 && k != columna) {
                return false;
            }
            if (t[k][columna] > 0 && k != fila) {
                return false;
            }
        }
        //Comprobamos las diagonales
        for (int k = 1; (columna - k >= 0) && (fila - k >= 0); k++) {
            if (t[fila - k][columna - k] > 0) {
                return false;
            }
        }
        for (int k = 1; (fila - k >= 0) && (columna + k < t.length); k++) {
            if (t[fila - k][columna + k] > 0) {
                return false;
            }
        }
        for (int k = 1; (fila + k < t.length) && (columna - k >= 0); k++) {
            if (t[fila + k][columna - k] > 0) {
                return false;
            }
        }
        for (int k = 1; (fila + k < t.length) && (columna + k < t.length); k++) {
            if (t[fila + k][columna + k] > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si la torre ataca alguna figura del tablero.
     *
     * @param t
     * @param fila
     * @param columna
     * @return true si no come a nadie
     */
    private boolean checkRook(int t[][], int fila, int columna) {
        for (int k = 0; k < t.length; k++) {
            if (t[fila][k] > 0 && k != columna) {
                return false;
            }
            if (t[k][columna] > 0 && k != fila) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si la superdama ataca alguna figura del tablero.
     *
     * @param t
     * @param fila
     * @param columna
     * @return true si no come a nadie
     */
    private boolean checkSuperQueen(int t[][], int fila, int columna) {
        return checkKnight(t, fila, columna) && checkQueen(t, fila, columna);
    }

    /**
     * Comprueba si el superrey ataca alguna figura del tablero.
     * 
     * @param t
     * @param fila
     * @param columna
     * @return true si no come a nadie
     */
    private boolean checkSuperKing(int t[][], int fila, int columna) {
        boolean aux = true;
        
        if (((fila - 2 >= 0) && columna - 2 >= 0) && (t[fila - 2][columna - 2] > 0)) {
            aux = false;
        }
        if ((fila - 2 >= 0) && (t[fila - 2][columna] > 2)) {
            aux = false;
        }
        if ((fila - 2 >= 0) && (columna + 2 < t.length) && (t[fila - 2][columna + 2] > 0)) {
            aux = false;
        }

        if ((columna - 2 >= 0) && (t[fila][columna - 2] > 0)) {
            aux = false;
        }
        if ((columna + 2 < t.length) && (t[fila][columna + 2] > 0)) {
            aux = false;
        }

        if ((fila + 2 < t.length) && (columna - 2 >= 0) && (t[fila + 2][columna - 2] > 0)) {
            aux = false;
        }
        if ((fila + 2 < t.length) && (t[fila + 2][columna] > 0)) {
            aux = false;
        }
        if ((fila + 2 < t.length) && (columna + 2 < t.length) && (t[fila + 2][columna + 2] > 0)) {
            aux = false;
        }
        return aux && checkKing(t, fila, columna)&& checkKnight(t, fila, columna);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String accio = e.getActionCommand();
            switch (accio) {
                case "addPiece" -> {
                    try {
                        this.addPiece();
                    } catch (Pesa.badSelection ex) {
                        Logger.getLogger(MainWindowEvents.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                case "compute" -> {
                    view.reset();
                    calculo();
                    if (!trobada) {
                        JOptionPane.showMessageDialog(view, "No hi ha solució");
                    }
                    view.colocaPeces(resultBoard);
                }

                case "resetPieces" -> {
                    this.resetBoard();
                }

                default ->
                    throw new badSelection();
            }

        } catch (badSelection ex) {
            Logger.getLogger(MainWindowEvents.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addPiece() throws Pesa.badSelection {
        Pesa p_sel = Pesa.getPesa(view.getCurrentPiezeSelected());
        model.addPiece(p_sel);
        view.updateView();
    }

    private void resetBoard() {
        model.clearPieces();
        view.updateView();
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        javax.swing.JSpinner sp_boardSize = (javax.swing.JSpinner) ce.getSource();
        model.setBoardSize((int) sp_boardSize.getValue());
        view.updateView();
    }

    public class badSelection extends Exception {
    }
}
