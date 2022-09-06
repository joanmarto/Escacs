/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.model.chesspieces;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author joan martorell
 */
public class SuperReina extends Pesa{
    public SuperReina() {
    }
    
   @Override
    public Image FiguraFBlanc() {
        Toolkit t = Toolkit.getDefaultToolkit ();
        return t.getImage("resources/imatges/Fons_Blanc/SuperReina.gif");
    }

    @Override
    public Image FiguraFNegre() {
         Toolkit t = Toolkit.getDefaultToolkit ();
        return t.getImage("resources/imatges/Fons_Negre/SuperReina.gif");
    }

    @Override
    public String toString() {
        return "SuperReina";
    }
}
