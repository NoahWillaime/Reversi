package reversi;

import reversi.modele.Modele;
import reversi.vue.VuePlateau;

import javax.swing.*;
import java.awt.*;

public class Reversi extends JFrame{

    public Reversi() {
        super("Reversi");
        Modele m = new Modele(8);
        VuePlateau vp = new VuePlateau(m);
        add(vp, BorderLayout.CENTER);
        setPreferredSize(new Dimension(800, 800));
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Reversi();
    }
}
