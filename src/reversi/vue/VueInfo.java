package reversi.vue;

import reversi.modele.Modele;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VueInfo extends JPanel implements Observer {
    private Modele m;
    private JPanel jetons;
    private JButton jetonsN;
    private JButton jetonsB;
    private JLabel turn;
    private JLabel nombre;
    private JPanel resetMod;
    private JLabel mode;
    private JButton reset;

    public VueInfo(Observable o){
        super();
        this.setLayout(new BorderLayout());
        m = (Modele)o;
        o.addObserver(this);
        jetons = new JPanel(new GridLayout(3, 1));
        this.add(jetons, BorderLayout.SOUTH);
        nombre = new JLabel("Nombre de Jetons :", SwingConstants.CENTER);
        jetons.add(nombre);
        jetonsN = new JButton(m.getJetonsNoir()+"");
        jetonsN.setBackground(Color.BLACK);
        jetonsN.setForeground(Color.WHITE);
        jetonsN.setEnabled(false);
        jetonsB = new JButton(m.getJetonsBlanc()+"");
        jetonsB.setBackground(Color.WHITE);
        jetonsB.setForeground(Color.BLACK);
        jetonsB.setEnabled(false);
        jetons.add(jetonsN);
        jetons.add(jetonsB);
        turn = new JLabel(m.getPlayer(), SwingConstants.CENTER);
        this.add(turn, BorderLayout.CENTER);
        resetMod = new JPanel(new GridLayout(2, 1));
        this.add(resetMod, BorderLayout.NORTH);
        mode = new JLabel("", SwingConstants.CENTER);
        if (m.getMode() == 0)
            mode.setText("Joueur vs Joueur");
        else if (m.getMode() == 1)
            mode.setText("Joueur vs IA");
        else if (m.getMode() == 2)
            mode.setText("IA vs IA");
        resetMod.add(mode);
        reset = new JButton("Recommencer le jeu");
        reset.addActionListener(e -> m.reset());
        resetMod.add(reset);
    }

    @Override
    public void update(Observable o, Object arg) {
        jetonsN.setText(m.getJetonsNoir()+"");
        jetonsB.setText(m.getJetonsBlanc()+"");
        turn.setText(m.getPlayer());
        if (m.getMode() == 0)
            mode.setText("Joueur vs Joueur");
        else if (m.getMode() == 1)
            mode.setText("Joueur vs IA");
        else if (m.getMode() == 2)
            mode.setText("IA vs IA");
    }
}
