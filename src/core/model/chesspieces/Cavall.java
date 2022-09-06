package core.model.chesspieces;

import java.awt.Image;
import java.awt.Toolkit;


/**
 *
 * @author bernat
 */
public class Cavall extends Pesa{
    
    public Cavall() {
    }
    
    @Override
    public Image FiguraFBlanc() {
         Toolkit t = Toolkit.getDefaultToolkit ();
        return t.getImage("resources/imatges/Fons_Blanc/Cavall.gif");
    }

    @Override
    public Image FiguraFNegre() {
         Toolkit t = Toolkit.getDefaultToolkit ();
        return t.getImage("resources/imatges/Fons_Negre/Cavall.gif");
    }

    @Override
    public String toString() {
        return "Cavall";
    }
}
