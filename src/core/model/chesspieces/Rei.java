package core.model.chesspieces;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author bernat
 */
public class Rei extends Pesa {

    public Rei() {
    }

    @Override
    public Image FiguraFBlanc() {
        Toolkit t = Toolkit.getDefaultToolkit();
        return t.getImage("resources/imatges/Fons_Blanc/Rei.gif");
    }

    @Override
    public Image FiguraFNegre() {
        Toolkit t = Toolkit.getDefaultToolkit();
        return t.getImage("resources/imatges/Fons_Negre/Rei.gif");
    }

    @Override
    public String toString() {
        return "Rei";
    }

}
