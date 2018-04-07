package reversi;

import reversi.modele.Modele;
import reversi.vue.VueInfo;
import reversi.vue.VueMenu;
import reversi.vue.VuePlateau;

import javax.swing.*;
import java.awt.*;

public class Reversi extends JFrame{

    public Reversi() {
        super("Reversi");
        Modele m = new Modele(8);
        VueMenu vm = new VueMenu(m);
        add(vm, BorderLayout.NORTH);
        VuePlateau vp = new VuePlateau(m);
        add(vp, BorderLayout.CENTER);
        VueInfo vi = new VueInfo(m);
        add(vi, BorderLayout.EAST);
        setPreferredSize(new Dimension(800, 800));
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      //  m.jouerIAvsIA();
        //m.compareIA();

//        System.out.println(m.getFinJeu());
    }

    public static void main(String[] args) {
        new Reversi();
    }
}
