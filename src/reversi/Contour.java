package reversi;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Contour implements Iterable<Point> {
        private ArrayList<Point> enveloppe;

        public Contour(int taille) {
            enveloppe = new ArrayList<>(12);
            enveloppe.add(new Point(taille/2-1, taille/2-2));
            enveloppe.add(new Point(taille/2-2, taille/2-2));
            enveloppe.add(new Point(taille/2-2, taille/2-1));
            enveloppe.add(new Point(taille/2-2, taille/2));
            enveloppe.add(new Point(taille/2-2, taille/2+1));
            enveloppe.add(new Point(taille/2-1, taille/2+1));
            enveloppe.add(new Point(taille/2, taille/2+1));
            enveloppe.add(new Point(taille/2+1, taille/2+1));
            enveloppe.add(new Point(taille/2+1, taille/2));
            enveloppe.add(new Point(taille/2+1, taille/2-1));
            enveloppe.add(new Point(taille/2+1, taille/2-2));
            enveloppe.add(new Point(taille/2, taille/2-2));
        }

        public Contour(Contour c) {
            /*Iterator i = c.getEnveloppe();
            int j = 0;
            while (i.hasNext()) {
                System.out.println(j);
                enveloppe.add((Point)i.next());
                j++;
            }*/
            this.enveloppe = new ArrayList<Point>();
            for (Point p : c){
                enveloppe.add(p);
            }
        }

        public void addPoint(Point ... points) {
            for (Point p : points) {
                if (!enveloppe.contains(p)) {
                    enveloppe.add(p);
                }
            }
        }

        public void removePoint(Point ... points) {
            for (Point p : points) {
                enveloppe.remove(p);
            }
        }

        public Iterator getEnveloppe() {
            return enveloppe.iterator();
        }

    @Override
    public Iterator<Point> iterator() {
        return enveloppe.iterator();
    }
}
