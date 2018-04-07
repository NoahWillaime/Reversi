package reversi.vue;

import reversi.modele.Modele;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VueMenu extends JPanel implements Observer{
    private Modele mod;
    private JPanel boutons;
    private JMenuBar menu;
    private JMenu reversi;
    private JMenuItem quitt;
    private JButton pvp;
    private JButton pve;
    private JButton eve;

    public VueMenu(Observable o){
        super();
        this.setLayout(new BorderLayout());
        mod = (Modele)o;
        o.addObserver(this);
        menu = new JMenuBar();
        this.add(menu, BorderLayout.NORTH);
        reversi = new JMenu("Reversi");
        menu.add(reversi);
        quitt = new JMenuItem("Quitter");
        quitt.addActionListener(e -> System.exit(0));
        reversi.add(quitt);
        boutons = new JPanel();
        this.add(boutons, BorderLayout.SOUTH);
        pvp = new JButton("Joueur vs Joueur");
        pvp.addActionListener(e -> mod.setMode(0));
        boutons.add(pvp);
        pve = new JButton("Joueur vs IA");
        pve.addActionListener(e -> mod.setMode(1));
        boutons.add(pve);
        eve = new JButton("IA vs IA");
        eve.addActionListener(e -> mod.setMode(2));
        boutons.add(eve);
    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
