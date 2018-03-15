package reversi.vue;

import reversi.modele.Modele;
import reversi.modele.etat.EtatReversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
            button.addActionListener(e -> {

            });
            cases.add(button);
            add(button);
        }
        setLayout(new GridLayout(m.getTaille_plateau(), m.getTaille_plateau()));
        int count = 0;
        for (int line[] : m.getPlateau()) {
            for (int c : line) {
                cases.get(count++).setBackground(c == EtatReversi.NOIR ? Color.BLACK
                        : c == EtatReversi.BLANC ? Color.WHITE
                        : Color.GRAY);
            }
        }
        Iterator<Point> i = m.getPlayable();
        while (i.hasNext()) {
            Point p = i.next();
            cases.get(m.getTaille_plateau() * p.y + p.x).setBackground(Color.GREEN);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
