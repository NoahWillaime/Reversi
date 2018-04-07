package reversi.Ecouteur;

import reversi.modele.Modele;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurCase implements ActionListener {
    private Point p;
    private Modele mod;

    public EcouteurCase(Modele m, int x, int y){
        p = new Point(x, y);
        mod = m;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mod.getMode() == 0)
            mod.joueurAction(p);
        else if (mod.getMode() == 1)
            mod.jouerIAvsP(p);
        else if (mod.getMode() == 2)
            mod.jouerIAvsIA();
    }
}
