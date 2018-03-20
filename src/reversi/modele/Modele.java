package reversi.modele;

import reversi.modele.algo.AlgoMinMax;
import reversi.modele.etat.Contour;
import reversi.modele.etat.EtatReversi;
import reversi.modele.joueur.JoueurReversi;

import java.awt.*;
import java.util.Iterator;
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
        etat = new EtatReversi(player1, taille_plateau);
    }

    public void joueurAction(Point p){
        if (etat.inPlayable(p)) {
            if (etat.getJoueur().equals(player1))
                etat = etat.successeurHumain(p, player2);
            else
                etat = etat.successeurHumain(p, player1);
            setChanged();
            notifyObservers();
        }
    }

    public void iaAction(int depth) {
        AlgoMinMax algo = new AlgoMinMax(this);
        EtatReversi meilleurCoup = null;
        int max = Integer.MIN_VALUE;
        Iterator<EtatReversi> coups = etat.successeursIA(getAdversaire(etat));
        while (coups.hasNext()) {
            EtatReversi c = coups.next();
            int val = algo.min(c, depth);
            if (val > max) {
                max = val;
                meilleurCoup = c;
            }
        }
        etat = meilleurCoup;
    }

    public int[][] getPlateau(){
        return etat.getPlateau();
    }

    public int getTaillePlateau() {
        return taille_plateau;
    }

    public Iterator<Point> getPlayable() {
        return etat.getCasePlayable();
    }

    public Contour getContour(){
        return etat.getContour();
    }

    public JoueurReversi getAdversaire(EtatReversi e){
        if (e.getJoueur().equals(player1)){
            return player2;
        }
        return player1;
    }

}
