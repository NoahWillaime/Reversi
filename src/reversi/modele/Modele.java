package reversi.modele;

import reversi.modele.algo.AlgoMinMax;
import reversi.modele.etat.Contour;
import reversi.modele.etat.Etat;
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
    private int end_game;
    private String finJeu;

    public Modele(int PLATEAU_SIZE) {
        taille_plateau = PLATEAU_SIZE;
        end_game = 0;
        finJeu = "Personne";
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

    public void jouerIAvsP(Point p){
        if (etat.inPlayable(p)){
            etat = etat.successeurHumain(p, player2);
            setChanged();
            notifyObservers();
            iaAction(4, 0);
            setChanged();
            notifyObservers();
        }
    }

    public void jouerIAvsIA() {
        if (!passTurn()) {
            iaAction(2, 0);
        } else {
            etat = new EtatReversi(getAdversaire(etat), etat.getPlateau(), etat.getContour(), etat.getResult(), etat.getNbJetonsP2(), etat.getNbJetonsP1());
            end_game++;
        }
        if (!passTurn()) {
            iaAction(2, 0);
        } else {
            etat = new EtatReversi(getAdversaire(etat), etat.getPlateau(), etat.getContour(),etat.getResult(), etat.getNbJetonsP2(), etat.getNbJetonsP1());
            end_game++;
        }
        if (end_game == 2){
            if (getGagnant() == EtatReversi.BLANC){
                finJeu = "Blanc";
            } else {
                finJeu = "Noir";
            }
        } else {
            end_game = 0;
            setChanged();
            notifyObservers();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jouerIAvsIA();
        }
    }

    public void iaAction(int depth, int eval) {
        boolean leave = true;
        Integer alpha = Integer.MIN_VALUE;
        Integer beta = Integer.MAX_VALUE;
        AlgoMinMax algo = new AlgoMinMax(this, etat.getCurrent(), eval);
        EtatReversi meilleurCoup = null;
        int max = Integer.MIN_VALUE;
        Iterator<EtatReversi> coups = etat.successeursIA(getAdversaire(etat));
        while (coups.hasNext() && leave) {
            EtatReversi c = coups.next();
            int val = algo.min(c, depth, alpha, beta);
            if (val > max) {
                max = val;
                meilleurCoup = c;
                if (max > alpha) {
                    leave = false;
                }
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

    public int getGagnant(){
        return etat.getGagnant();
    }

    public boolean passTurn(){
        if (etat.getPlayable().size() == 0){
            return true;
        }
        return false;
    }

    public String getFinJeu() {
        return finJeu;
    }
}
