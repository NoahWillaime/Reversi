package reversi.modele.jeu;

import reversi.modele.etat.EtatReversi;

public class Jeu {
    private Plateau plateau;
    private boolean turn; //True P1, False P2

    public Jeu(Plateau plateau) {
        this.plateau = plateau;
        if (this.plateau.getPlayer1().getCouleur() == EtatReversi.NOIR){
            turn = true;
        } else {
            turn = false;
        }
    }


}
