package reversi.modele;

import reversi.modele.etat.Etat;
import reversi.modele.etat.EtatReversi;
import reversi.modele.joueur.JoueurReversi;

import java.awt.*;
import java.util.Observable;

public class Modele extends Observable {
    private JoueurReversi player1;
    private JoueurReversi player2;
    private EtatReversi etat;
    private int taille_plateau;

    public Modele(int PLATEAU_SIZE) {
        taille_plateau = PLATEAU_SIZE;

        player1 = new JoueurReversi(EtatReversi.NOIR);
        player2 = new JoueurReversi(EtatReversi.BLANC);
        EtatReversi etat = new EtatReversi(player1, taille_plateau);
    }

    public void joueurAction(Point p){
        if (etat.getJoueur().equals(player1))
            etat = etat.successeurHumain(p, player2);
        else
            etat = etat.successeurHumain(p, player1);
        setChanged();
        notifyObservers();
    }

    public int[][] getPlateau(){
        return etat.getPlateau();
    }

    public int getTaille_plateau() {
        return taille_plateau;
    }

}
