package reversi.vue;

import reversi.Ecouteur.EcouteurCase;
import reversi.modele.Modele;
import reversi.modele.etat.EtatReversi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public class VuePlateau extends JPanel implements Observer {
    private ArrayList<JButton> cases;
    private Modele m;

    public VuePlateau(Observable o) {
        super();
        m = (Modele)o;
        cases = new ArrayList<>(m.getTaillePlateau() * m.getTaillePlateau());
        o.addObserver(this);
        int x = 0;
        int y = 0;
        for (int i = 0; i < m.getTaillePlateau() * m.getTaillePlateau(); i++) {
            if (i % m.getTaillePlateau() == 0 && i != 0){
                y += 1;
                x = 0;
            }
            JButton button = new JButton();
            button.addActionListener(new EcouteurCase(m, x, y));
            x++;
            cases.add(button);
            add(button);
        }
        setLayout(new GridLayout(m.getTaillePlateau(), m.getTaillePlateau()));
        int count = 0;
        for (int line[] : m.getPlateau()) {
            for (int c : line) {
                cases.get(count++).setBackground(c == EtatReversi.NOIR ? Color.BLACK
                        : c == EtatReversi.BLANC ? Color.WHITE
                        : Color.GRAY);
            }
        }
        Iterator<Point> i1 = m.getContour().iterator();
        while (i1.hasNext()) {
            Point p = i1.next();
            cases.get(m.getTaillePlateau() * p.y + p.x).setBackground(Color.RED);
        }
        Iterator<Point> i = m.getPlayable();
        while (i.hasNext()) {
            Point p = i.next();
            cases.get(m.getTaillePlateau() * p.y + p.x).setBackground(Color.GREEN);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        int count = 0;
        for (int line[] : m.getPlateau()) {
            for (int c : line) {
                cases.get(count++).setBackground(c == EtatReversi.NOIR ? Color.BLACK
                        : c == EtatReversi.BLANC ? Color.WHITE
                        : Color.GRAY);
            }
        }
        Iterator<Point> i1 = m.getContour().iterator();
        while (i1.hasNext()) {
            Point p = i1.next();
            cases.get(m.getTaillePlateau() * p.y + p.x).setBackground(Color.RED);
        }
        Iterator<Point> i = m.getPlayable();
        while (i.hasNext()) {
            Point p = i.next();
            cases.get(m.getTaillePlateau() * p.y + p.x).setBackground(Color.GREEN);
        }
    }
}
