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
    private int end_game;
    private String finJeu;
    private int[][] interest;

    public Modele(int PLATEAU_SIZE) {
        taille_plateau = PLATEAU_SIZE;
        end_game = 0;
        finJeu = "Personne";
        player1 = new JoueurReversi(EtatReversi.NOIR);
        player2 = new JoueurReversi(EtatReversi.BLANC);
        etat = new EtatReversi(player1, taille_plateau);
        interest = interest();
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
            iaAction(6, 0);
            setChanged();
            notifyObservers();
        }
    }

    public void jouerIAvsIA() {
        if (!passTurn()) {
            iaAction(4, 0);
        } else {
            etat = new EtatReversi(getAdversaire(etat), etat.getPlateau(), etat.getContour(), etat.getResult(), etat.getNbJetonsP2(), etat.getNbJetonsP1());
            end_game++;
        }
        if (!passTurn()) {
            iaAction(4, 2);
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
            jouerIAvsIA();
        }
    }

    public void iaAction(int depth, int eval) {
        boolean leave = true;
        Integer alpha = Integer.MIN_VALUE;
        Integer beta = Integer.MAX_VALUE;
        AlgoMinMax algo = new AlgoMinMax(this, etat.getCurrent(), eval);
        EtatReversi meilleurCoup = null;
        Integer max = Integer.MIN_VALUE;
        Iterator<EtatReversi> coups = etat.successeursIA(getAdversaire(etat));
        while (coups.hasNext() && leave) {
            System.out.println(max);
            EtatReversi c = coups.next();
            int val = algo.min(c, depth, alpha, beta);
            if (val > max) {
                max = val;
                meilleurCoup = c;
                if (max > alpha) {
                    leave = false;
                }
            }
            System.out.println(">>>"+max);
        }
        etat = meilleurCoup;
    }


    public int[][] interest() {
        int taille = getTaillePlateau();
        int[][] res = new int[taille][taille];
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j ++) {
                if ((i == 0 && j == 0) || (i == 0 && j == taille - 1)
                        || (i == taille - 1 && j == 0) || (i == taille - 1 && j == taille - 1)) {
                    res[i][j] = 100;
                } else if ((i == 0 && j == 1) || (i == 1 && j == 0) || (i == 1 && j == 1) ||
                        (i == 0 && j == taille - 2) || (i == 1 && j == taille - 2) || (i == 1 && j == taille - 1) ||
                        (i == taille - 2 && j == 0) || (i == taille - 2 && j == 1) || (i == taille - 1 && j == 1) ||
                        (i == taille - 2 && j == taille - 2) || (i == taille - 2 && j == taille - 1) || (i == taille - 1 && j == taille - 2)) {
                    res[i][j] = 0;
                } else {
                    if (i == 0 || j == 0 || i == taille - 1 || j == taille - 1) {
                        res[i][j] = 99;
                    } else if (i == 1 || j == 1 || i == taille - 2 || j == taille - 2) {
                        res[i][j] = 98;
                    } else {
                        res[i][j] = 97;
                    }
                }
            }
        }
        return res;
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

    public int[][] getInterest() {
        return interest;
    }
}
