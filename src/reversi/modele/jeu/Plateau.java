package reversi.modele.jeu;

import reversi.modele.etat.EtatReversi;
import reversi.modele.joueur.JoueurReversi;

public class Plateau {
    private JoueurReversi player1;
    private JoueurReversi player2;
    private EtatReversi etat;

    public Plateau(JoueurReversi player1, JoueurReversi player2, EtatReversi etat) {
        this.player1 = player1;
        this.player2 = player2;
        this.etat = etat;
    }

    public JoueurReversi getPlayer1() {
        return player1;
    }

    public JoueurReversi getPlayer2() {
        return player2;
    }
}
