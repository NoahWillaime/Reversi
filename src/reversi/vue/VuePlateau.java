package reversi.vue;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class VuePlateau extends JPanel implements Observer {
    private ArrayList<JButton> cases;

    public VuePlateau(Observable o) {
        super();
        cases = new ArrayList<>(8 * 8);
        o.addObserver(this);
        for (int i = 0; i < 8 * 8; i++) {
            JButton button = new JButton();
            cases.add(button);
            add(button);
        }
        setLayout(new GridLayout(8, 8));
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
