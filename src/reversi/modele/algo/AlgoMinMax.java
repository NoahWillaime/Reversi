package reversi.modele.algo;

import reversi.modele.Modele;
import reversi.modele.etat.EtatReversi;

import java.util.Iterator;

public class AlgoMinMax {
    private Modele m;

    public AlgoMinMax(Modele m) {
        this.m = m;
    }

    public int eval0(EtatReversi e) {
        return e.getNbJetonsP1();
    }

    public int max(EtatReversi e, int depth, Integer alpha, Integer beta) {
        if (depth == 0) {
            return eval0(e);
        }
        int max = Integer.MIN_VALUE;
        Iterator<EtatReversi> coups = e.successeursIA(m.getAdversaire(e));
        while (coups.hasNext()) {
            EtatReversi c = coups.next();
            int val = min(c, depth - 1, alpha, beta);
            if (val > max) {
                max = val;
                if (max > alpha) {
                    alpha = max;
                    if (alpha > beta) { // coupure beta
                        return max;
                    }
                }
            }
        }
        return max;
    }

    public int min(EtatReversi e, int depth, Integer alpha, Integer beta) {
        if (depth == 0){
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
                if (min < beta) {
                    beta = min;
                    if (alpha > beta) { // coupure alpha
                        return beta;
                    }
                }
            }
        }
        return min;
    }
}
