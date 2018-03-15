package reversi.modele.joueur;

import reversi.modele.etat.EtatReversi;

public class JoueurReversi extends Joueur {
    private int couleur; // BLANC OU NOIR

    public JoueurReversi(int c){
        couleur = c;
    }

    public int getCouleur() {
        return couleur;
    }

    public int getOppose(){
        if (couleur == EtatReversi.BLANC)
            return EtatReversi.NOIR;
        return EtatReversi.BLANC;
    }
}
