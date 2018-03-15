package reversi.modele.etat;

import reversi.modele.joueur.JoueurReversi;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class EtatReversi extends Etat{
    private JoueurReversi joueur;
    private int[][] plateau;
    private Contour contour;
    public static int VIDE = 0;
    public static int BLANC = 1;
    public static int NOIR = 2;

    public EtatReversi(JoueurReversi player, int taille){
        super();
        this.joueur = player;
        this.plateau = new int[taille][taille];
        plateau[taille/2-1][taille/2-1] = BLANC;
        plateau[taille/2][taille/2-1] = NOIR;
        plateau[taille/2][taille/2] = BLANC;
        plateau[taille/2-1][taille/2] = NOIR;
        this.contour = new Contour(8);
    }

    public EtatReversi(JoueurReversi player, int[][] plateau, Contour contour){
        super();
        this.joueur = player;
        this.plateau = plateau;
        this.contour = contour;
    }

  /*  public Iterator<EtatReversi> successeursIA(){
        return algoSuccesseurIA().iterator();
    }
*/
    public EtatReversi successeurHumain(Point p, JoueurReversi adversaire) {
        return algoSuccesseurHumain(p, adversaire);
    }

    private EtatReversi successeurNord(int x, int y, JoueurReversi adversaire){
        int tmp = y;
        int oppose = adversaire.getCouleur();
        while (plateau[tmp][x] == oppose && tmp < getTaille()){
            tmp++;
        }
        if (plateau[tmp][x] == joueur.getCouleur()){
            int[][] new_plateau = new int[getTaille()][];
            for (int i = 0; i < getTaille(); i++)
                new_plateau[i] = Arrays.copyOf(this.plateau[i], getTaille());
            while (tmp >= y){ //Tant qu'on est pas à la 1ère case de couleur opposée (après la case vide)
                new_plateau[tmp][x] = joueur.getCouleur(); //Transformation en couleur du joueur
                tmp--;
            }
            /* A ce point notre coordonnée est sur la case vide */
            new_plateau[tmp][x] = joueur.getCouleur(); //MAJ DE LA CASE VIDE
            //MAJ DU CONTOUR
            Contour new_contour = new Contour(this.contour);
            new_contour.removePoint(new Point(x, tmp));
            if (tmp-1 >= 0) {
                if (x - 1 >= 0)
                    new_contour.addPoint(new Point(x - 1, tmp - 1));
                new_contour.addPoint(new Point(x, tmp - 1));
                if (x + 1 < getTaille())
                    new_contour.addPoint(new Point(x + 1, tmp - 1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurSud(int x, int y, JoueurReversi adversaire){
        int tmp = y;
        int oppose = adversaire.getCouleur();
        while (plateau[tmp][x] == oppose && tmp >= 0 ){
            tmp--;
        }
        if (plateau[tmp][x] == joueur.getCouleur()){
            int[][] new_plateau = new int[getTaille()][];
            for (int i = 0; i < getTaille(); i++)
                new_plateau[i] = Arrays.copyOf(this.plateau[i], getTaille());
            while (tmp <= y){ //Tant qu'on est pas à la 1ère case de couleur opposée (après la case vide)
                new_plateau[tmp][x] = joueur.getCouleur(); //Transformation en couleur du joueur
                tmp++;
            }
            /* A ce point notre coordonnée est sur la case vide */
            new_plateau[tmp][x] = joueur.getCouleur(); //MAJ DE LA CASE VIDE
            //MAJ DU CONTOUR
            Contour new_contour = new Contour(this.contour);
            new_contour.removePoint(new Point(x, tmp));
            if (tmp + 1 < getTaille()) {
                if (x - 1 >= 0)
                    new_contour.addPoint(new Point(x - 1, tmp + 1));
                new_contour.addPoint(new Point(x, tmp + 1));
                if (x + 1 < getTaille())
                    new_contour.addPoint(new Point(x + 1, tmp + 1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurEst(int x, int y, JoueurReversi adversaire){
        int tmp = x;
        int oppose = adversaire.getCouleur();
        while (plateau[y][tmp] == oppose && tmp < getTaille()){
            tmp++;
        }
        if (plateau[y][tmp] == joueur.getCouleur()){
            int[][] new_plateau = new int[getTaille()][];
            for (int i = 0; i < getTaille(); i++)
                new_plateau[i] = Arrays.copyOf(this.plateau[i], getTaille());
            while (tmp >= x){ //Tant qu'on est pas à la 1ère case de couleur opposée (après la case vide)
                new_plateau[y][tmp] = joueur.getCouleur(); //Transformation en couleur du joueur
                tmp--;
            }
            /* A ce point notre coordonnée est sur la case vide */
            new_plateau[y][tmp] = joueur.getCouleur(); //MAJ DE LA CASE VIDE
            //MAJ DU CONTOUR
            Contour new_contour = new Contour(this.contour);
            new_contour.removePoint(new Point(tmp, y));
            /* Contour mis à jour si dans les limites du plateau */
            if (tmp - 1 >= 0) {
                if (y - 1 >= 0)
                    new_contour.addPoint(new Point(tmp - 1, y - 1));
                new_contour.addPoint(new Point(tmp - 1, y));
                if (y + 1 < getTaille())
                    new_contour.addPoint(new Point(tmp - 1, y + 1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurOuest(int x, int y, JoueurReversi adversaire){
        int tmp = x;
        int oppose = adversaire.getCouleur();
        while (plateau[y][tmp] == oppose && tmp >= 0){
            tmp--;
        }
        if (plateau[y][tmp] == joueur.getCouleur()){
            int[][] new_plateau = new int[getTaille()][];
            for (int i = 0; i < getTaille(); i++)
                new_plateau[i] = Arrays.copyOf(this.plateau[i], getTaille());
            while (tmp <= x){ //Tant qu'on est pas à la 1ère case de couleur opposée (après la case vide)
                new_plateau[y][tmp] = joueur.getCouleur(); //Transformation en couleur du joueur
                tmp++;
            }
            /* A ce point notre coordonnée est sur la case vide */
            new_plateau[y][tmp] = joueur.getCouleur(); //MAJ DE LA CASE VIDE
            //MAJ DU CONTOUR
            Contour new_contour = new Contour(this.contour);
            new_contour.removePoint(new Point(tmp, y));
            if (tmp + 1 < getTaille()) {
                if (y - 1 >= 0)
                    new_contour.addPoint(new Point(tmp + 1, y - 1));
                new_contour.addPoint(new Point(tmp + 1, y));
                if (y + 1 < getTaille())
                    new_contour.addPoint(new Point(tmp + 1, y + 1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurNordEst(int x, int y, JoueurReversi adversaire){
        int tmp_x = x;
        int tmp_y = y;
        int oppose = adversaire.getCouleur();
        while (plateau[tmp_y][tmp_x] == oppose && tmp_x < getTaille() && tmp_y < getTaille()){
            tmp_x++;
            tmp_y++;
        }
        if (plateau[tmp_y][tmp_x] == joueur.getCouleur()){
            int[][] new_plateau = new int[getTaille()][];
            for (int i = 0; i < getTaille(); i++)
                new_plateau[i] = Arrays.copyOf(this.plateau[i], getTaille());
            while (tmp_x >= x && tmp_y >= y){ //Tant qu'on est pas à la 1ère case de couleur opposée (après la case vide)
                new_plateau[tmp_y][tmp_x] = joueur.getCouleur(); //Transformation en couleur du joueur
                tmp_x--;
                tmp_y--;
            }
            /* A ce point notre coordonnée est sur la case vide */
            new_plateau[tmp_y][tmp_x] = joueur.getCouleur(); //MAJ DE LA CASE VIDE
            //MAJ DU CONTOUR
            Contour new_contour = new Contour(this.contour);
            new_contour.removePoint(new Point(tmp_x, tmp_y));
            /* Contour mis à jour si dans les limites du plateau */
            if (tmp_y - 1 >= 0) {
                new_contour.addPoint(new Point(tmp_x, tmp_y-1));
                if (tmp_x - 1 >= 0)
                    new_contour.addPoint(new Point(tmp_x-1, tmp_y-1));
                if (tmp_x + 1 < getTaille())
                    new_contour.addPoint(new Point(tmp_x+1, tmp_y-1));
            }
            if (tmp_x + 1 < getTaille()) {
                new_contour.addPoint(new Point(tmp_x + 1, tmp_y));
                if (tmp_y+1 < getTaille())
                    new_contour.addPoint(new Point(tmp_x+1, tmp_y+1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurSudEst(int x, int y, JoueurReversi adversaire){
        int tmp_x = x;
        int tmp_y = y;
        int oppose = adversaire.getCouleur();
        while (plateau[tmp_y][tmp_x] == oppose && tmp_x < getTaille() && tmp_y >=0){
            tmp_x++;
            tmp_y--;
        }
        if (plateau[tmp_y][tmp_x] == joueur.getCouleur()){
            int[][] new_plateau = new int[getTaille()][];
            for (int i = 0; i < getTaille(); i++)
                new_plateau[i] = Arrays.copyOf(this.plateau[i], getTaille());
            while (tmp_x >= x && tmp_y < getTaille()){ //Tant qu'on est pas à la 1ère case de couleur opposée (après la case vide)
                new_plateau[tmp_y][tmp_x] = joueur.getCouleur(); //Transformation en couleur du joueur
                tmp_x--;
                tmp_y++;
            }
            /* A ce point notre coordonnée est sur la case vide */
            new_plateau[tmp_y][tmp_x] = joueur.getCouleur(); //MAJ DE LA CASE VIDE
            //MAJ DU CONTOUR
            Contour new_contour = new Contour(this.contour);
            new_contour.removePoint(new Point(tmp_x, tmp_y));
            /* Contour mis à jour si dans les limites du plateau */
            if (tmp_y + 1 < getTaille()) {
                new_contour.addPoint(new Point(tmp_x, tmp_y+1));
                if (tmp_x - 1 >= 0)
                    new_contour.addPoint(new Point(tmp_x-1, tmp_y+1));
                if (tmp_x + 1 < getTaille())
                    new_contour.addPoint(new Point(tmp_x+1, tmp_y+1));
            }
            if (tmp_x + 1 < getTaille()) {
                new_contour.addPoint(new Point(tmp_x + 1, tmp_y));
                if (tmp_y+1 < getTaille())
                    new_contour.addPoint(new Point(tmp_x+1, tmp_y-1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurNordOuest(int x, int y, JoueurReversi adversaire){
        int tmp_x = x;
        int tmp_y = y;
        int oppose = adversaire.getCouleur();
        while (plateau[tmp_y][tmp_x] == oppose && tmp_x >= 0 && tmp_y < getTaille()){
            tmp_x--;
            tmp_y++;
        }
        if (plateau[tmp_y][tmp_x] == joueur.getCouleur()){
            int[][] new_plateau = new int[getTaille()][];
            for (int i = 0; i < getTaille(); i++)
                new_plateau[i] = Arrays.copyOf(this.plateau[i], getTaille());
            while (tmp_x <= x && tmp_y >= y){ //Tant qu'on est pas à la 1ère case de couleur opposée (après la case vide)
                new_plateau[tmp_y][tmp_x] = joueur.getCouleur(); //Transformation en couleur du joueur
                tmp_x++;
                tmp_y--;
            }
            /* A ce point notre coordonnée est sur la case vide */
            new_plateau[tmp_y][tmp_x] = joueur.getCouleur(); //MAJ DE LA CASE VIDE
            //MAJ DU CONTOUR
            Contour new_contour = new Contour(this.contour);
            new_contour.removePoint(new Point(tmp_x, tmp_y));
            /* Contour mis à jour si dans les limites du plateau */
            if (tmp_y - 1 >= 0) {
                new_contour.addPoint(new Point(tmp_x, tmp_y-1));
                if (tmp_x - 1 >= 0)
                    new_contour.addPoint(new Point(tmp_x-1, tmp_y-1));
                if (tmp_x + 1 < getTaille())
                    new_contour.addPoint(new Point(tmp_x+1, tmp_y-1));
            }
            if (tmp_x - 1 >= 0) {
                new_contour.addPoint(new Point(tmp_x - 1, tmp_y));
                if (tmp_y+1 < getTaille())
                    new_contour.addPoint(new Point(tmp_x-1, tmp_y+1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurSudOuest(int x, int y, JoueurReversi adversaire){
        int tmp_x = x;
        int tmp_y = y;
        int oppose = adversaire.getCouleur();
        while (plateau[tmp_y][tmp_x] == oppose && tmp_x >= 0 && tmp_y >= 0){
            tmp_x--;
            tmp_y--;
        }
        if (plateau[tmp_y][tmp_x] == joueur.getCouleur()){
            int[][] new_plateau = new int[getTaille()][];
            for (int i = 0; i < getTaille(); i++)
                new_plateau[i] = Arrays.copyOf(this.plateau[i], getTaille());
            while (tmp_x <= x && tmp_y < getTaille()){ //Tant qu'on est pas à la 1ère case de couleur opposée (après la case vide)
                new_plateau[tmp_y][tmp_x] = joueur.getCouleur(); //Transformation en couleur du joueur
                tmp_x++;
                tmp_y++;
            }
            /* A ce point notre coordonnée est sur la case vide */
            new_plateau[tmp_y][tmp_x] = joueur.getCouleur(); //MAJ DE LA CASE VIDE
            //MAJ DU CONTOUR
            Contour new_contour = new Contour(this.contour);
            new_contour.removePoint(new Point(tmp_x, tmp_y));
            /* Contour mis à jour si dans les limites du plateau */
            if (tmp_y + 1 <getTaille()) {
                new_contour.addPoint(new Point(tmp_x, tmp_y+1));
                if (tmp_x - 1 >= 0)
                    new_contour.addPoint(new Point(tmp_x-1, tmp_y+1));
                if (tmp_x + 1 < getTaille())
                    new_contour.addPoint(new Point(tmp_x+1, tmp_y+1));
            }
            if (tmp_x - 1 >= 0) {
                new_contour.addPoint(new Point(tmp_x - 1, tmp_y));
                if (tmp_y-1 >= 0)
                    new_contour.addPoint(new Point(tmp_x-1, tmp_y-1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }
/*
    private ArrayList<EtatReversi> algoSuccesseurIA(){
        ArrayList<EtatReversi> succ = new ArrayList<>();
        EtatReversi er;
        int oppose = joueur.getOppose();
        for (Point p : contour){
            er = null;
            //Si une case couleur opposé autour de la case vide
            if (plateau[p.y+1][p.x] == oppose){ //EN HAUT
                er = successeurNord(p.x, p.y+1, oppose);
                if (er != null)
                    succ.add(er);
            } else if (plateau[p.y+1][p.x+1] == oppose){//Diagonal haut droite
                er = successeurNordEst(p.x+1, p.y+1, oppose);
                if (er != null)
                    succ.add(er);
            } else if (plateau[p.y][p.x+1] == oppose){//Droite
                er = successeurEst(p.x+1, p.y, oppose);
                if (er != null)
                    succ.add(er);
            } else if (plateau[p.y-1][p.x+1] == oppose){//Diagonal bas droite
                er = successeurSudEst(p.x+1, p.y-1, oppose);
                if (er != null)
                    succ.add(er);
            } else if (plateau[p.y-1][p.x] == oppose){//BAS
                er = successeurSud(p.x, p.y-1, oppose);
                if (er != null)
                    succ.add(er);
            } else if (plateau[p.y-1][p.x-1] == oppose){//Diagonal bas gauche
                er = successeurSudOuest(p.x-1, p.y-1, oppose);
                if (er != null)
                    succ.add(er);
            } else if (plateau[p.y][p.x-1] == oppose){//Gauche
                er = successeurOuest(p.x-1, p.y, oppose);
                if (er != null)
                    succ.add(er);
            } else if (plateau[p.y+1][p.x-1] == oppose){//Diagonal haut gauche
                er = successeurNordOuest(p.x-1, p.y+1, oppose);
                if (er != null)
                    succ.add(er);
            }
        }
        return succ;
    }
*/
    private EtatReversi algoSuccesseurHumain(Point p, JoueurReversi adversaire) {
        EtatReversi er = null;
        int oppose = adversaire.getCouleur();
        //Si une case couleur opposé autour de la case vide
        if (plateau[p.y+1][p.x] == oppose){ //EN HAUT
            er = successeurNord(p.x, p.y+1, adversaire);
        } else if (plateau[p.y+1][p.x+1] == oppose){//Diagonal haut droite
            er = successeurNordEst(p.x+1, p.y+1, adversaire);
        } else if (plateau[p.y][p.x+1] == oppose){//Droite
            er = successeurEst(p.x+1, p.y, adversaire);
        } else if (plateau[p.y-1][p.x+1] == oppose){//Diagonal bas droite
            er = successeurSudEst(p.x+1, p.y-1, adversaire);
        } else if (plateau[p.y-1][p.x] == oppose){//BAS
            er = successeurSud(p.x, p.y-1, adversaire);
        } else if (plateau[p.y-1][p.x-1] == oppose){//Diagonal bas gauche
            er = successeurSudOuest(p.x-1, p.y-1, adversaire);
        } else if (plateau[p.y][p.x-1] == oppose){//Gauche
            er = successeurOuest(p.x-1, p.y,  adversaire);
        } else if (plateau[p.y+1][p.x-1] == oppose){//Diagonal haut gauche
            er = successeurNordOuest(p.x-1, p.y+1, adversaire);
        }
        return er;
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

    public int[][] getPlateau() {
        return plateau;
    }

    public int getTaille(){
        return plateau.length;
    }

}
