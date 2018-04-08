package reversi.modele;

import reversi.modele.algo.AlgoMinMax;
import reversi.modele.etat.Contour;
import reversi.modele.etat.EtatReversi;
import reversi.modele.joueur.JoueurReversi;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;

public class Modele extends Observable {
    private JoueurReversi player1;
    private JoueurReversi player2;
    private EtatReversi etat;
    private int taille_plateau;
    private int endGame;
    private String finJeu;
    private int[][] interest;
    private int[][] newInterest;
    public static Integer alpha;
    public static Integer beta;
    public int mode; //0 : PVP, 1 : PVE, 2 : EVE

    public Modele(int PLATEAU_SIZE) {
        taille_plateau = PLATEAU_SIZE;
        endGame = 0;
        finJeu = "Personne";
        player1 = new JoueurReversi(EtatReversi.NOIR);
        player2 = new JoueurReversi(EtatReversi.BLANC);
        etat = new EtatReversi(player1, taille_plateau);
        interest = interest();
        newInterest = interest.clone();
        mode = 0;
    }

    public void reset(){
        endGame = 0;
        finJeu = "Personne";
        player1 = new JoueurReversi(EtatReversi.NOIR);
        player2 = new JoueurReversi(EtatReversi.BLANC);
        etat = new EtatReversi(player1, taille_plateau);
        setChanged();
        notifyObservers();
    }

    public void compareIA(){
        evaluationIA eval = new evaluationIA(this);
        System.out.println(eval.evalIA(0, 2));
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
            iaAction(0, 2);
            setChanged();
            notifyObservers();
        }
    }

    public void jouerIAvsIA() {
        if (!passTurn()) {
            iaAction(0, 2);
            setChanged();
            notifyObservers();
        } else {
            etat = new EtatReversi(getAdversaire(etat), etat.getPlateau(), etat.getContour(), etat.getResult(), etat.getNbJetonsP2(), etat.getNbJetonsP1());
            endGame++;
        }
        if (!passTurn()) {
            iaAction(2, 1);
            setChanged();
            notifyObservers();
        } else {
            etat = new EtatReversi(getAdversaire(etat), etat.getPlateau(), etat.getContour(),etat.getResult(), etat.getNbJetonsP2(), etat.getNbJetonsP1());
            endGame++;
        }
        if (endGame == 2){
            if (getGagnant() == EtatReversi.BLANC){
                finJeu = "Blanc";
            } else {
                finJeu = "Noir";
            }
        } else {
            endGame = 0;
            jouerIAvsIA();
        }
    }

    public void iaAction(int depth, int eval) {
        alpha = Integer.MIN_VALUE;
        beta = Integer.MAX_VALUE;
        AlgoMinMax algo = new AlgoMinMax(this, etat.getCurrent(), eval);
        EtatReversi meilleurCoup = null;
        int max = Integer.MIN_VALUE;
        Iterator<EtatReversi> coups = etat.successeursIA(getAdversaire(etat));
        while (coups.hasNext()) {
            EtatReversi c = coups.next();
            int val = algo.min(c, depth, alpha, beta);
            if (val > max) {
                max = val;
                meilleurCoup = c;
            }
        }
        etat = meilleurCoup;
    }
    
    public int[][] interest() {
        int taille = getTaillePlateau();
        int[][] res = new int[taille][taille];
        int count = 0;
        for (int k = 0; k+1 < taille - count; k++) {
            for (int i = k; i < taille - count; i++) {
                if (count < taille - count) {
                    res[i][count] = 99 - count;
                    res[i][taille - count - 1] = 99 - count;
                    res[count][i] = 99 - count;
                    res[taille - count - 1][i] = 99 - count;
                }
            }
            count++;
        }
        res[0][0] = 100;
        res[0][taille - 1] = 100;
        res[taille - 1][0] = 100;
        res[taille -1][taille - 1] = 100;
        res[0][1] = 0;
        res[1][0] = 0;
        res[1][1] = 0;
        res[0][taille - 2] = 0;
        res[1][taille - 2] = 0;
        res[1][taille - 1] = 0;
        res[taille - 2][0] = 0;
        res[taille - 2][1] = 0;
        res[taille - 1][1] = 0;
        res[taille - 2][taille - 2] = 0;
        res[taille - 2][taille - 1] = 0;
        res[taille - 1][taille - 2] = 0;
        return res;
    }

    public void changeInterest() {
        int taille = getTaillePlateau();
        Random random = new Random();
        newInterest[random.nextInt(taille - 1)][random.nextInt(taille - 1)] = random.nextInt(100);
        interest[0][0] = 100;
        interest[0][taille - 1] = 100;
        interest[taille - 1][0] = 100;
        interest[taille -1][taille - 1] = 100;
        interest[0][1] = 0;
        interest[1][0] = 0;
        interest[1][1] = 0;
        interest[0][taille - 2] = 0;
        interest[1][taille - 2] = 0;
        interest[1][taille - 1] = 0;
        interest[taille - 2][0] = 0;
        interest[taille - 2][1] = 0;
        interest[taille - 1][1] = 0;
        interest[taille - 2][taille - 2] = 0;
        interest[taille - 2][taille - 1] = 0;
        interest[taille - 1][taille - 2] = 0;
    }

    public int[][] getNewInterest() {
        return newInterest;
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

    public EtatReversi getEtat() {
        return etat;
    }

    public int getEndGame() {
        return endGame;
    }

    public void setEtat(EtatReversi etat) {
        this.etat = etat;
    }

    public void setEndGame(int endGame) {
        this.endGame = endGame;
    }

    public void setFinJeu(String finJeu) {
        this.finJeu = finJeu;
    }

    public int[][] getInterest() {
        return interest;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        reset();
        setChanged();
        notifyObservers();
        if (mode == 2) {
            changeInterest();
            jouerIAvsIA();
        }
    }

    public int getJetonsNoir(){
        if (etat.getCurrent() == etat.BLANC)
            return etat.getNbJetonsP2();
        else
            return etat.getNbJetonsP1();
    }

    public int getJetonsBlanc(){
        if (etat.getCurrent() == etat.BLANC)
            return etat.getNbJetonsP1();
        else
            return etat.getNbJetonsP2();
    }


    public String getPlayer(){
        if (etat.getCurrent() == etat.BLANC){
            return "Au tour du Joueur BLANC";
        } else {
            return "Au tour du Joueur NOIR";
        }
    }
}
