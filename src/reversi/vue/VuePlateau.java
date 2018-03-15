package reversi.vue;

import reversi.modele.Modele;
import reversi.modele.etat.EtatReversi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

public class VuePlateau extends JPanel implements Observer {
    private ArrayList<JButton> cases;
    private Modele m;

    public VuePlateau(Observable o) {
        super();
        m = (Modele)o;
        cases = new ArrayList<>(m.getTaille_plateau() * m.getTaille_plateau());
        o.addObserver(this);
        for (int i = 0; i < m.getTaille_plateau() * m.getTaille_plateau(); i++) {
            JButton button = new JButton();
            cases.add(button);
            add(button);
        }
        setLayout(new GridLayout(m.getTaille_plateau(), m.getTaille_plateau()));
        System.out.println(Arrays.toString(m.getPlateau()));
        int count = 0;
        for (int line[] : m.getPlateau()) {
            for (int c : line) {
                cases.get(count++).setBackground(c == EtatReversi.NOIR ? Color.BLACK
                        : c == EtatReversi.BLANC ? Color.WHITE
                        : Color.GRAY);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
