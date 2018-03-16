package reversi;

import reversi.modele.etat.Etat;
import reversi.modele.etat.EtatReversi;
import reversi.modele.joueur.JoueurReversi;

import java.awt.*;
import java.util.Arrays;

public class Test {
    public static void main(String[] args){
        EtatReversi jeu = new EtatReversi(new JoueurReversi(EtatReversi.NOIR), 8);
        EtatReversi suc;
        for (Point p : jeu.getContour()) {
            System.out.println(p.toString());
            System.out.println("---------");
            suc = jeu.successeurHumain(p, new JoueurReversi(EtatReversi.BLANC));
            if (suc != null)
                System.out.println(suc.toString());
            System.out.println("---------");

        }
      /*  ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(10, 10));
        points.add(new Point(11, 11));
        points.add(new Point(10, 10));
        for (Point p : points){
            System.out.println(p.x+"/"+p.y);
        }
        ArrayList<Point> pts= new ArrayList<Point>();
        pts.add(new Point(10, 10));
        points.removeAll(pts);
        for (Point p : points){
            System.out.println(p.x+"/"+p.y);
        } */
    }
}
