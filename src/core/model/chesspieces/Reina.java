package core.model.chesspieces;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author bernat
 */
public class Reina extends Pesa{
    
    public Reina() {
    }
    
   @Override
    public Image FiguraFBlanc() {
        Toolkit t = Toolkit.getDefaultToolkit ();
        return t.getImage("resources/imatges/Fons_Blanc/Reina.gif");
    }

    @Override
    public Image FiguraFNegre() {
         Toolkit t = Toolkit.getDefaultToolkit ();
        return t.getImage("resources/imatges/Fons_Negre/Reina.gif");
    }

    @Override
    public String toString() {
        return "Reina";
    }
}
