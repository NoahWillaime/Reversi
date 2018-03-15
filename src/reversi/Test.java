package reversi;

import reversi.Etat.EtatReversi;
import reversi.Joueur.JoueurReversi;

public class Test {
    public static void main(String[] args){
      EtatReversi jeu = new EtatReversi(new JoueurReversi(EtatReversi.NOIR), 8);
        System.out.println(jeu.toString());
        jeu.successeurs();

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
