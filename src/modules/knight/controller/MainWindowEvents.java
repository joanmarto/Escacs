package modules.knight.controller;

import core.model.chesspieces.Cavall;
import core.view.CellDrawable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modules.knight.model.BoardDefinition;
import modules.knight.view.IntegerCellDrawable;
import modules.knight.view.MainWindow;

/**
 *
 * @author Bernat Galmés Rubert
 */
public class MainWindowEvents implements ActionListener, ChangeListener {

    private MainWindow view;
    private BoardDefinition model;
    private CellDrawable[][] content;
    private boolean trobada;

    public MainWindowEvents(MainWindow view, BoardDefinition model) {
        this.view = view;
        this.model = model;
    }

    public void calculo(int fila, int columna) {
        //Variable para controlar si hemos encontrado una solución
        trobada = false;
        
        //Creamos las matrices para la resolución del problema y para guardar la solución
        //si esta existe
        int t[][] = new int[model.getBoardSize()][model.getBoardSize()];
        content = new CellDrawable[model.getBoardSize()][model.getBoardSize()];

        //Inicializamos las casillas con el valor "disponible"
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                t[i][j] = -1;
            }
        }

        backtracking(t, fila, columna, 0);
    }

    /**
     * Valor de las casillas:
     * 
     * -1 -> Casilla disponible
     * 
     * 0 -> Caballo inicial
     * 
     * k -> Número de movimientos del caballo para llegar a la casilla 
     * 
     * @param t
     * @param fila
     * @param columna
     * @param k 
     */
    public void backtracking(int t[][], int fila, int columna, int k) {
        t[fila][columna] = k;
        if (!trobada) {
            //Comprobamos los posibles movimientos del caballo
            if ((fila - 2 >= 0) && (columna - 1 >= 0) && (t[fila - 2][columna - 1] < 0)) {
                backtracking(t, fila - 2, columna - 1, k + 1);
            }
            if ((fila - 2 >= 0) && (columna + 1 < t.length) && (t[fila - 2][columna + 1] < 0)) {
                backtracking(t, fila - 2, columna + 1, k + 1);
            }
            if ((fila + 2 < t.length) && (columna - 1 >= 0) && (t[fila + 2][columna - 1] < 0)) {
                backtracking(t, fila + 2, columna - 1, k + 1);
            }
            if ((fila + 2 < t.length) && (columna + 1 < t.length) && (t[fila + 2][columna + 1] < 0)) {
                backtracking(t, fila + 2, columna + 1, k + 1);
            }
            if ((fila - 1 >= 0) && (columna - 2 >= 0) && (t[fila - 1][columna - 2] < 0)) {
                backtracking(t, fila - 1, columna - 2, k + 1);
            }
            if ((fila + 1 < t.length) && (columna - 2 >= 0) && (t[fila + 1][columna - 2] < 0)) {
                backtracking(t, fila + 1, columna - 2, k + 1);
            }
            if ((fila - 1 >= 0) && (columna + 2 < t.length) && (t[fila - 1][columna + 2] < 0)) {
                backtracking(t, fila - 1, columna + 2, k + 1);
            }
            if ((fila + 1 < t.length) && (columna + 2 < t.length) && (t[fila + 1][columna + 2] < 0)) {
                backtracking(t, fila + 1, columna + 2, k + 1);
            }
        }
        if (!trobada && isSolution(t)) {
            trobada = true;
            for (int i = 0; i < t.length; i++) {
                for (int j = 0; j < t.length; j++) {
                    content[i][j] = new IntegerCellDrawable(t[i][j]);
                }
            }
        } else {
            //Eliminamos el caballo que habíamos colocado
            t[fila][columna] = -1;
        }
    }

    private boolean isSolution(int t[][]) {
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                if (t[i][j] == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String accio = e.getActionCommand();
            switch (accio) {
                case "compute" -> {
                    view.reset();
                    calculo(view.getFila() - 1, view.getColumna() - 1);
                    //Colocamos el caballo en la posición inicial
                    content[view.getFila() - 1][view.getColumna() - 1] = new Cavall();
                    view.colocaPeces(content);
                    if (!trobada) {
                        JOptionPane.showMessageDialog(view, "No hi ha solució");
                    }
                }

                default ->
                    throw new badSelection();
            }

        } catch (badSelection ex) {
            Logger.getLogger(MainWindowEvents.class.getName()).log(Level.SEVERE, null, ex);
        }
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
