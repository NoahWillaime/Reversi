package reversi;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class EtatReversi extends Etat{
    private JoueurReversi joueur;
    private int[][] plateau;
    private ArrayList<Point> contour;
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
        this.contour = new ArrayList<Point>(12);
        contour.add(new Point(taille/2-1, taille/2-2));
        contour.add(new Point(taille/2-2, taille/2-2));
        contour.add(new Point(taille/2-2, taille/2-1));
        contour.add(new Point(taille/2-2, taille/2));
        contour.add(new Point(taille/2-2, taille/2+1));
        contour.add(new Point(taille/2-1, taille/2+1));
        contour.add(new Point(taille/2, taille/2+1));
        contour.add(new Point(taille/2+1, taille/2+1));
        contour.add(new Point(taille/2+1, taille/2));
        contour.add(new Point(taille/2+1, taille/2-1));
        contour.add(new Point(taille/2+1, taille/2-2));
        contour.add(new Point(taille/2, taille/2-2));
    }

    public EtatReversi(JoueurReversi player, int[][] plateau, ArrayList<Point> contour){
        super();
        this.joueur = player;
        this.plateau = plateau;
        this.contour = contour;
    }

    public Iterator<EtatReversi> successeurs(){
    /*    if (joueur.getCouleur() == BLANC)
            return successeursBlanc();*/
        algoSuccesseur();
        return null;//successeursNoir();
    }

    private Iterator<EtatReversi> successeursBlanc(){
        ArrayList<EtatReversi> sBlanc = new ArrayList<EtatReversi>();
        return sBlanc.iterator();
    }

    private Iterator<EtatReversi> successeursNoir() {
        ArrayList<EtatReversi> sNoir = new ArrayList<EtatReversi>();
        return sNoir.iterator();
    }

    private EtatReversi successeurHaut(int x, int y, int oppose){
        int tmp = y;
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
            ArrayList<Point> new_contour = new ArrayList<>(contour);
            for (int i = 0; i < new_contour.size(); i++) //For each ne marche pas ici car on remove l'objet
                if (new_contour.get(i).x == x && new_contour.get(i).y == tmp) {
                    new_contour.remove(i);
                }
            if (tmp-1 >= 0) {
                if (x - 1 >= 0)
                    new_contour.add(new Point(x - 1, tmp - 1));
                new_contour.add(new Point(x, tmp - 1));
                if (x + 1 < getTaille())
                    new_contour.add(new Point(x + 1, tmp - 1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurBas(int x, int y, int oppose){
        int tmp = y;
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
            ArrayList<Point> new_contour = new ArrayList<>(contour);
            for (int i = 0; i < new_contour.size(); i++) //For each ne marche pas ici car on remove l'objet
                if (new_contour.get(i).x == x && new_contour.get(i).y == tmp) {
                    new_contour.remove(i);
                }
            if (tmp + 1 < getTaille()) {
                if (x - 1 >= 0)
                    new_contour.add(new Point(x - 1, tmp + 1));
                new_contour.add(new Point(x, tmp + 1));
                if (x + 1 < getTaille())
                    new_contour.add(new Point(x + 1, tmp + 1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurDroite(int x, int y, int oppose){
        int tmp = x;
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
            ArrayList<Point> new_contour = new ArrayList<>(contour);
            for (int i = 0; i < new_contour.size(); i++) //For each ne marche pas ici car on remove l'objet
                if (new_contour.get(i).x == x && new_contour.get(i).y == tmp) {
                    new_contour.remove(i);
                }
            /* Contour mis à jour si dans les limites du plateau */
            if (tmp - 1 >= 0) {
                if (y - 1 >= 0)
                    new_contour.add(new Point(tmp - 1, y - 1));
                new_contour.add(new Point(tmp - 1, y));
                if (y + 1 < getTaille())
                    new_contour.add(new Point(tmp - 1, y + 1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurGauche(int x, int y, int oppose){
        int tmp = x;
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
            ArrayList<Point> new_contour = new ArrayList<>(contour);
            for (int i = 0; i < new_contour.size(); i++) //For each ne marche pas ici car on remove l'objet
                if (new_contour.get(i).x == x && new_contour.get(i).y == tmp) {
                    new_contour.remove(i);
                }
            if (tmp + 1 < getTaille()) {
                if (y - 1 >= 0)
                    new_contour.add(new Point(tmp + 1, y - 1));
                new_contour.add(new Point(tmp + 1, y));
                if (y + 1 < getTaille())
                    new_contour.add(new Point(tmp + 1, y + 1));
            }
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private EtatReversi successeurNordEst(int x, int y, int oppose){
        int tmp_x = x;
        int tmp_y = y;
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
            ArrayList<Point> new_contour = new ArrayList<>(contour);
            for (int i = 0; i < new_contour.size(); i++) //For each ne marche pas ici car on remove l'objet
                if (new_contour.get(i).x == tmp_x && new_contour.get(i).y == tmp_y) {
                    new_contour.remove(i);
                }
            /* Contour mis à jour si dans les limites du plateau */
       /*     if (tmp - 1 >= 0) {
                if (y - 1 >= 0)
                    new_contour.add(new Point(tmp - 1, y - 1));
                new_contour.add(new Point(tmp - 1, y));
                if (y + 1 < getTaille())
                    new_contour.add(new Point(tmp - 1, y + 1));
            }*/
            return new EtatReversi(joueur, new_plateau, new_contour);
        }
        return null;
    }

    private void algoSuccesseur(){
        int oppose = joueur.getOppose();
        for (Point p : contour){
            //Si une case couleur opposé autour de la case vide
            if (plateau[p.y+1][p.x] == oppose){ //EN HAUT
                System.out.println(successeurHaut(p.x, p.y+1, oppose).toString());
            }
            if (plateau[p.y-1][p.x+1] == oppose){//Diagonal haut droite

            }
            if (plateau[p.y][p.x+1] == oppose){//Droite
                System.out.println(successeurDroite(p.x+1, p.y, oppose).toString());
            }
            if (plateau[p.y+1][p.x+1] == oppose){//Diagonal bas droite

            }
            if (plateau[p.y-1][p.x] == oppose){//BAS
                System.out.println(successeurBas(p.x, p.y-1, oppose).toString());
            }
            if (plateau[p.y+1][p.x-1] == oppose){//Diagonal bas gauche

            }
            if (plateau[p.y][p.x-1] == oppose){//Gauche
                System.out.println(successeurGauche(p.x-1, p.y, oppose).toString());
            }
            if (plateau[p.y-1][p.x-1] == oppose){//Diagonal haut gauche

            }
        }
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
