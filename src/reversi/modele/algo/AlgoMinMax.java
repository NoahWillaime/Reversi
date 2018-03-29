package reversi.modele.algo;

import reversi.modele.Modele;
import reversi.modele.etat.EtatReversi;

import javax.jws.WebParam;
import java.awt.*;
import java.util.Iterator;

public class AlgoMinMax {
    private Modele m;
    private int couleur;
    private int utiliastion;

    public AlgoMinMax(Modele m, int couleurJ, int eval) {
        this.m = m;
        couleur = couleurJ;
        utiliastion = eval;
    }

    public int eval0(EtatReversi e) {
        return e.getNbJetonsP1();
    }

    public int eval0b(EtatReversi e){
        if (e.getCurrent() == couleur)
            return e.getPlayable().size();
        EtatReversi newE = new EtatReversi(m.getAdversaire(e), e.getPlateau(), e.getContour(), null, e.getNbJetonsP2(), e.getNbJetonsP1());
        return newE.getPlayable().size();
    }

    public int eval0c(EtatReversi e) {
        Point p = e.getResult();
        int interest[][] = m.getInterest();
        return interest[p.y][p.x];
    }

    public int max(EtatReversi e, int depth, Integer alpha, Integer beta) {
        if (depth == 0 || e.getGagnant() != -1) {
            if (utiliastion == 1)
                return eval0b(e);
            else if (utiliastion == 2)
                return eval0c(e);
            return eval0(e);
        }
        int max = Integer.MIN_VALUE;
        Iterator<EtatReversi> coups = e.successeursIA(m.getAdversaire(e));
        while (coups.hasNext()) {
            EtatReversi c = coups.next();
            int val = min(c, depth - 1, alpha, beta);
            if (val > max) {
                max = val;
                if (max > Modele.alpha) {
                    Modele.alpha = max;
                    if (Modele.alpha > Modele.beta) { // coupure beta
                        return max;
                    }
                }
            }
        }
        return max;
    }

    public int min(EtatReversi e, int depth, Integer alpha, Integer beta) {
        if (depth == 0 || e.getGagnant() != -1){
            if (utiliastion == 1)
                return eval0b(e);
            else if (utiliastion == 2)
                return eval0c(e);
            return eval0(e);
        }
        int min = Integer.MAX_VALUE;
        int val = 0;
        Iterator<EtatReversi> it = e.successeursIA(m.getAdversaire(e));
        EtatReversi etat;
        while (it.hasNext()){
            etat = it.next();
            val = max(etat, depth-1, alpha, beta);
            if (val < min){
                min = val;
                if (min < Modele.beta) {
                    Modele.beta = min;
                    if (Modele.alpha > Modele.beta) { // coupure alpha
                        return Modele.beta;
                    }
                }
            }
        }
        return min;
    }
}
