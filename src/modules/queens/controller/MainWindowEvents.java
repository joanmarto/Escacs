package modules.queens.controller;

import core.model.chesspieces.Reina;
import core.view.CellDrawable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modules.queens.model.BoardDefinition;
import modules.queens.view.MainWindow;

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
     * Valor de las casillas:
     *
     * 0 -> Casilla disponible
     *
     * 1 -> Casilla con Reina
     *
     * 2 -> Casilla con Reina inicial
     *
     */
    private void calculo(int fila, int columna) {
        resultBoard = new CellDrawable[model.getBoardSize()][model.getBoardSize()];
        
        //Creamos una matriz a inicializada con ceros
        int[][] a = new int[model.getBoardSize()][model.getBoardSize()];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                a[i][j] = 0;
            }
        }
        trobada = false; //Variable que nos indicará si hemos encontrado una solución
        
        //Colocamos la reina inicial
        a[fila - 1][columna - 1] = 2;
      
        //Llamamos al backtracking
        backtracking(a, 0, 0);
    }

    private void backtracking(int[][] a, int fila, int columna) {
        while (fila < a.length && columna < a.length) {
            if (a[fila][columna] != 2) {
                a[fila][columna] = 1;
            }
            if (canComplete(a, fila, columna)) {
                if ((columna < a.length - 1)) {
                    backtracking(a, fila, columna + 1);
                } else {
                    if ((fila < a.length - 1)) {
                        backtracking(a, fila + 1, 0);
                    }
                }
            } else {
                if (fila != a.length && columna != a.length) {
                    if (a[fila][columna] != 2) {
                        a[fila][columna] = 0;
                    }
                }
            }
            if (!trobada && isSolution(a, fila, columna)) {
                //Usamos la matriz solución para cargar las piezas en las casillas 
                //correspondientes
                trobada = true;
                for (int i = 0; i < a.length; i++) {
                    for (int j = 0; j < a.length; j++) {
                        if (a[i][j] > 0) {
                            resultBoard[i][j] = new Reina();
                        }
                    }
                }
            } else {
                if (a[fila][columna] != 2) {
                    a[fila][columna] = 0;
                }
            }
            
            //Incrementamos valores que controlan el bucle principal
            if (columna < a.length - 1) {
                columna++;
            } else {
                columna = 0;
                fila++;

            }
        }
    }

    /**
     * Método que comprueba si la matriz pasada por parámetros es una solución
     * @param a
     * @param fila
     * @param columna
     * @return 
     */
    private boolean isSolution(int[][] a, int fila, int columna) {
        //Comprobamos si estamos en un nodo hoja
        boolean reinaInicial = false;
        if (fila == a.length - 1 && columna == a.length - 1) {
            int reinas = 0;
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a.length; j++) {
                    if (a[i][j] == 2) {
                        reinaInicial = true;
                    }
                    if (a[i][j] > 0) {
                        reinas++;
                        //Miramos que no haya reinas en la misma columna
                        for (int k = 0; k < a.length; k++) {
                            if (a[i][k] > 0 && k != j) {
                                return false;
                            }
                        }
                        //Miramos que no haya reinas en la misma fila
                        for (int k = 0; k < a.length; k++) {
                            if (a[k][j] > 0 && k != i) {
                                return false;
                            }
                        }
                        //Comprobamos las diagonales
                        for (int k = 1; (j - k >= 0) && (i - k >= 0); k++) {
                            if (a[i - k][j - k] > 0) {
                                return false;
                            }
                        }
                        for (int k = 1; (i - k >= 0) && (j + k < a.length); k++) {
                            if (a[i - k][j + k] > 0) {
                                return false;
                            }
                        }
                        for (int k = 1; (i + k < a.length) && (j - k >= 0); k++) {
                            if (a[i + k][j - k] > 0) {
                                return false;
                            }
                        }
                        for (int k = 1; (i + k < a.length) && (j + k < a.length); k++) {
                            if (a[i + k][j + k] > 0) {
                                return false;
                            }
                        }
                    }
                }
            }
            if (reinas == a.length && reinaInicial) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Método para comprobar si podemos continuar con el backtracking
     * @param a
     * @param fila
     * @param columna
     * @return 
     */
    private boolean canComplete(int[][] a, int fila, int columna) {
        //Si estamos en un nodo hoja no podemos seguir haciendo backtracking
        if (fila == a.length - 1 && columna == a.length - 1) {
            return false;
        }
        //Miramos que no haya reinas en la misma columna
        for (int k = 0; k < a.length; k++) {
            if (a[fila][k] > 0 && k != columna) {
                return false;
            }
        }
        //Miramos que no haya reinas en la misma fila
        for (int k = 0; k < a.length; k++) {
            if (a[k][columna] > 0 && k != fila) {
                return false;
            }
        }
        //Comprobamos las diagonales
        for (int k = 1; (columna - k >= 0) && (fila - k >= 0); k++) {
            if (a[fila - k][columna - k] > 0) {
                return false;
            }
        }
        for (int k = 1; (fila - k >= 0) && (columna + k < a.length); k++) {
            if (a[fila - k][columna + k] > 0) {
                return false;
            }
        }
        for (int k = 1; (fila + k < a.length) && (columna - k >= 0); k++) {
            if (a[fila + k][columna - k] > 0) {
                return false;
            }
        }
        for (int k = 1; (fila + k < a.length) && (columna + k < a.length); k++) {
            if (a[fila + k][columna + k] > 0) {
                return false;
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
                    calculo(view.getFila(),view.getColumna());
                    if (!trobada) {
                        JOptionPane.showMessageDialog(view, "No hi ha solució");
                    }
                    view.colocaPeces(resultBoard);
                }

                default ->
                    throw new badSelection();

            }

        } catch (badSelection ex) {
            Logger.getLogger(MainWindowEvents.class
                    .getName()).log(Level.SEVERE, null, ex);
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
