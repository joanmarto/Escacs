package core.model.chesspieces;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * 
 * @author Bernat Galmés Rubert
 */
public class Peo extends Pesa {

    public Peo() {
    }

    @Override
    public Image FiguraFBlanc() {
        Toolkit t = Toolkit.getDefaultToolkit();
        return t.getImage("resources/imatges/Fons_Blanc/Gat.gif");
    }

    @Override
    public Image FiguraFNegre() {
        Toolkit t = Toolkit.getDefaultToolkit();
        return t.getImage("resources/imatges/Fons_Negre/Gat.gif");
    }

    @Override
    public String toString() {
        return "Gat";
    }

}
