package reversi.modele;

import reversi.modele.etat.EtatReversi;
import reversi.modele.joueur.JoueurReversi;

import java.util.Observable;

public class Modele extends Observable {
    private JoueurReversi player1;
    private JoueurReversi player2;
    private EtatReversi etat;

    public Modele() {
    }

}
