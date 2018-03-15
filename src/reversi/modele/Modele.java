package reversi.modele;

import reversi.modele.jeu.Jeu;

import java.util.Observable;

public class Modele extends Observable {
    private Jeu jeu_reversi;

    public Modele(Jeu jeu_reversi) {
        this.jeu_reversi = jeu_reversi;
    }

    public Jeu getJeu() {
        return jeu_reversi;
    }
}
