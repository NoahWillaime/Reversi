package reversi.modele.etat;

import reversi.modele.joueur.JoueurReversi;

import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class EtatReversi extends Etat{
    private JoueurReversi joueur;
    private int[][] plateau;
    private Contour contour;
    private ArrayList<Point> CasePlayable;
    public static int VIDE = 0;
    public static int BLANC = 1;
    public static int NOIR = 2;
    private int nbJetonsP1;
    private int nbJetonsP2;
    private Point result;

    public EtatReversi(JoueurReversi player, int taille){
        super();
        this.joueur = player;
        this.plateau = new int[taille][taille];
        this.CasePlayable = new ArrayList<>();
        plateau[taille/2-1][taille/2-1] = BLANC;
        plateau[taille/2][taille/2-1] = NOIR;
        plateau[taille/2][taille/2] = BLANC;
        plateau[taille/2-1][taille/2] = NOIR;
        this.contour = new Contour(8);
        CasePlayable = getPlayable();
        nbJetonsP1 = 2;
        nbJetonsP2 = 2;
    }

    public EtatReversi(JoueurReversi player, int[][] plateau, Contour contour, Point res, int nbJetonsP1, int nbJetonsP2){
        super();
        this.CasePlayable = new ArrayList<>();
        this.joueur = player;
        this.plateau = plateau;
        this.contour = contour;
        CasePlayable = getPlayable();
        this.nbJetonsP1 = nbJetonsP1;
        this.nbJetonsP2 = nbJetonsP2;
        result = res;
    }

    public Iterator<EtatReversi> successeursIA(JoueurReversi adversaire){
        return algoSuccesseurIA(adversaire).iterator();
    }

    public EtatReversi successeurHumain(Point p, JoueurReversi adversaire) {
        return algoSuccesseurHumain(p, adversaire);
    }

    public EtatReversi successeur(int x, int y, int incrX, int incrY, JoueurReversi adversaire){
        int tmpX = x;
        int tmpY = y;
        int opppose = adversaire.getCouleur();
        int newJetons1 = nbJetonsP1;
        int newJetons2 = nbJetonsP2;
        while ((y >= 0 && y < getTaille()) && (x >= 0 && x < getTaille()) && plateau[y][x] == opppose){
            y += incrY;
            x += incrX;
        }
        if ((y >= 0 && y < getTaille()) && (x >= 0 && x < getTaille()) && plateau[y][x] == joueur.getCouleur()){
            int[][] new_plateau = new int[getTaille()][];
            for (int i = 0; i < getTaille(); i++)
                new_plateau[i] = Arrays.copyOf(this.plateau[i], getTaille());
            while (x != tmpX-incrX || y != tmpY-incrY){ //Tant qu'on est pas à la 1ère case de couleur opposée (après la case vide)
                if (new_plateau[y][x] != joueur.getCouleur()) {
                    new_plateau[y][x] = joueur.getCouleur(); //Transformation en couleur du joueur
                    newJetons1++;
                    newJetons2--;
                }
                x -= incrX;
                y -= incrY;
            }
            new_plateau[y][x] = joueur.getCouleur(); //MAJ DE LA CASE VIDE
            Point p = new Point(x, y);
            newJetons1++;
            Contour new_contour = new Contour(this.contour);
            new_contour.removePoint(new Point(x, y));
            if (y+1 < getTaille() && x - 1 >= 0 && new_plateau[y+1][x-1] == EtatReversi.VIDE)
                new_contour.addPoint(new Point(x-1, y+1));
            if (y+1 < getTaille() && new_plateau[y+1][x] == EtatReversi.VIDE)
                new_contour.addPoint(new Point(x, y+1));
            if (y+1 < getTaille() && x+1 < getTaille() && new_plateau[y+1][x+1] == EtatReversi.VIDE)
                new_contour.addPoint(new Point(x+1, y+1));
            if (x+1 < getTaille() && new_plateau[y][x+1] == EtatReversi.VIDE)
                new_contour.addPoint(new Point(x+1, y));
            if (x-1 >= 0 && new_plateau[y][x-1] == EtatReversi.VIDE)
                new_contour.addPoint(new Point(x-1, y));
            if (y-1 >= 0 && x-1 >= 0 && new_plateau[y-1][x-1] == EtatReversi.VIDE)
                new_contour.addPoint(new Point(x-1, y-1));
            if (y-1 >= 0 && new_plateau[y-1][x] == EtatReversi.VIDE)
                new_contour.addPoint(new Point(x, y-1));
            if (y-1 >= 0 && x+1 < getTaille() && new_plateau[y-1][x+1] == EtatReversi.VIDE)
                new_contour.addPoint(new Point(x+1, y-1));
            return new EtatReversi(adversaire, new_plateau, new_contour, p, newJetons2, newJetons1);
        }
        return null;
    }

    public boolean isPlayable(int x, int y, int incrX, int incrY){
        int opppose = joueur.getOppose();
        while ((y >= 0 && y < getTaille()) && (x >= 0 && x < getTaille())
                && plateau[y][x] == opppose){
            y += incrY;
            x += incrX;
        }
        if ((y >= 0 && y < getTaille()) && (x >= 0 && x < getTaille())
                && plateau[y][x] == joueur.getCouleur())
            return true;
        return false;
    }

    public ArrayList<Point> getPlayable(){
        ArrayList<Point> playable = new ArrayList<>();
        int oppose = joueur.getOppose();
        for (Point p : contour){
            //Si une case couleur opposé autour de la case vide
            if (p.y-1 >= 0 && plateau[p.y-1][p.x] == oppose){ //EN HAUT
                if (isPlayable(p.x, p.y-1, 0, -1))
                    if (!playable.contains(p))
                        playable.add(p);
            }
            if (p.y-1 >= 0 && p.x+1 < getTaille() && plateau[p.y-1][p.x+1] == oppose){//Diagonal haut droite
                if (isPlayable(p.x+1, p.y-1, 1, -1))
                    if (!playable.contains(p))
                        playable.add(p);
            }
            if (p.x+1 < getTaille() && plateau[p.y][p.x+1] == oppose){//Droite
                if (isPlayable(p.x+1, p.y, 1, 0))
                    if (!playable.contains(p))
                        playable.add(p);
            }
            if (p.y+1 < getTaille() && p.x+1 <getTaille() && plateau[p.y+1][p.x+1] == oppose){//Diagonal bas droite
                if (isPlayable(p.x+1, p.y+1, 1, 1))
                    if (!playable.contains(p))
                        playable.add(p);
            }
            if (p.y+1 < getTaille() && plateau[p.y+1][p.x] == oppose){//BAS
                if (isPlayable(p.x, p.y+1, 0, 1))
                    if (!playable.contains(p))
                        playable.add(p);
            }
            if (p.y+1 < getTaille() && p.x-1 >= 0 && plateau[p.y+1][p.x-1] == oppose){//Diagonal bas gauche
                if (isPlayable(p.x-1, p.y+1, -1, 1))
                    if (!playable.contains(p))
                        playable.add(p);
            }
            if (p.x-1 >= 0 && plateau[p.y][p.x-1] == oppose){//Gauche
                if (isPlayable(p.x-1, p.y, -1, 0))
                    if (!playable.contains(p))
                        playable.add(p);
            }
            if (p.y-1 >= 0 && p.x-1 >= 0 && plateau[p.y-1][p.x-1] == oppose){//Diagonal haut gauche
                if (isPlayable(p.x-1, p.y-1, -1, -1))
                    if (!playable.contains(p))
                        playable.add(p);
            }
        }
        return playable;
    }

    private ArrayList<EtatReversi> algoSuccesseurIA(JoueurReversi adversaire){
        ArrayList<EtatReversi> succ = new ArrayList<>();
        EtatReversi er;
        int oppose = joueur.getOppose();
        for (Point p : contour){
            er = null;
            //Si une case couleur opposé autour de la case vide
            if (p.y-1 >= 0 && plateau[p.y-1][p.x] == oppose){ //EN HAUT
                er = successeur(p.x, p.y-1, 0, -1, adversaire);
                if (er != null)
                    succ.add(er);
            }
            if (p.y-1 >= 0 && p.x+1 < getTaille() && plateau[p.y-1][p.x+1] == oppose){//Diagonal haut droite
                er = successeur(p.x+1, p.y-1, 1, -1, adversaire);
                if (er != null)
                    succ.add(er);
            }
            if (p.x+1 < getTaille() && plateau[p.y][p.x+1] == oppose){//Droite
                er = successeur(p.x+1, p.y, 1, 0, adversaire);
                if (er != null)
                    succ.add(er);
            }
            if (p.y+1 < getTaille() && p.x+1 < getTaille() && plateau[p.y+1][p.x+1] == oppose){//Diagonal bas droite
                er = successeur(p.x+1, p.y+1, 1, 1, adversaire);
                if (er != null)
                    succ.add(er);
            }
            if (p.y+1 < getTaille() && plateau[p.y+1][p.x] == oppose){//BAS
                er = successeur(p.x, p.y+1, 0, 1, adversaire);
                if (er != null)
                    succ.add(er);
            }
            if (p.y+1 < getTaille() && p.x-1 >= 0 && plateau[p.y+1][p.x-1] == oppose){//Diagonal bas gauche
                er = successeur(p.x-1, p.y+1, -1, 1, adversaire);
                if (er != null)
                    succ.add(er);
            }
            if (p.x-1 >= 0 && plateau[p.y][p.x-1] == oppose) {//Gauche
                er = successeur(p.x - 1, p.y, -1, 0, adversaire);
                if (er != null)
                    succ.add(er);
            }
            if (p.y-1 >= 0 && p.x-1 >= 0 && plateau[p.y-1][p.x-1] == oppose){//Diagonal haut gauche
                er = successeur(p.x-1, p.y-1, -1, -1, adversaire);
                if (er != null)
                    succ.add(er);
            }
        }
        return succ;
    }

    private EtatReversi algoSuccesseurHumain(Point p, JoueurReversi adversaire) {
        EtatReversi er = null;
        int oppose = joueur.getOppose();//adversaire.getCouleur();
        //Si une case couleur opposé autour de la case vide
        if (p.y-1 >= 0 && plateau[p.y-1][p.x] == oppose){ //EN HAUT
            er = successeur(p.x, p.y-1, 0, -1, adversaire);
            if (er != null)
                return er;
        }
        if (p.y-1 >= 0 && p.x+1 < getTaille() && plateau[p.y-1][p.x+1] == oppose){//Diagonal haut droite
           er = successeur(p.x+1, p.y-1, 1, -1, adversaire);
            if (er != null)
                return er;
        }
        if (p.x+1 < getTaille() && plateau[p.y][p.x+1] == oppose){//Droite
            er = successeur(p.x+1, p.y, 1, 0, adversaire);
            if (er != null)
                return er;
        }
        if (p.y+1 < getTaille() && p.x+1 < getTaille() && plateau[p.y+1][p.x+1] == oppose){//Diagonal bas droite
            er = successeur(p.x+1, p.y+1, 1, 1, adversaire);
            if (er != null)
                return er;
        }
        if (p.y+1 < getTaille() && plateau[p.y+1][p.x] == oppose){//BAS
            er = successeur(p.x, p.y+1, 0, 1, adversaire);
            if (er != null)
                return er;
        }
        if (p.y+1 < getTaille() && p.x-1 >= 0 && plateau[p.y+1][p.x-1] == oppose){//Diagonal bas gauche
           er = successeur(p.x-1, p.y+1, -1, 1, adversaire);
            if (er != null)
                return er;
        }
        if (p.x-1 >= 0 && plateau[p.y][p.x-1] == oppose) {//Gauche
            er = successeur(p.x - 1, p.y, -1, 0, adversaire);
            if (er != null)
                return er;
        }
        if (p.y-1 >= 0 && p.x-1 >= 0 && plateau[p.y-1][p.x-1] == oppose){//Diagonal haut gauche
            er = successeur(p.x-1, p.y-1, -1, -1, adversaire);
            if (er != null)
                return er;
        }
        return null;
    }

    public boolean equals(EtatReversi etat) {
        if (this.getTaille() != etat.getTaille()
                || this.getTaille() != etat.getTaille())
            return false;
        int[][] etatP = etat.getPlateau();
        for (int i = 0; i < getTaille(); i++){
            for (int j = 0; j < getTaille(); j++){
                if (plateau[i][j] != etatP[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] aPlateau : plateau) {
            for (int anAPlateau : aPlateau) {
                sb.append(anAPlateau);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Iterator<Point> getCasePlayable() {
        return CasePlayable.iterator();
    }

    public int[][] getPlateau() {
        return plateau;
    }

    public int getTaille(){
        return plateau.length;
    }

    public JoueurReversi getJoueur() {
        return joueur;
    }

    public Contour getContour() {
        return contour;
    }

    public boolean inPlayable(Point p){
        return CasePlayable.contains(p);
    }

    public int getNbJetonsP1() {
        return nbJetonsP1;
    }

    public int getNbJetonsP2() {
        return nbJetonsP2;
    }

    public int getCurrent(){
        return joueur.getCouleur();
    }

    public int getGagnant(){
        if (getPlayable().size() == 0){
            if (nbJetonsP1 > nbJetonsP2){
                return joueur.getCouleur();
            } else {
                return joueur.getOppose();
            }
        }
        return -1;
    }

    public Point getResult() {
        return result;
    }
}
