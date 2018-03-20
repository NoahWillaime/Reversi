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

    public int max(EtatReversi e, int depth) {
        if (depth == 0) {
            return eval0(e);
        }
        int max = Integer.MIN_VALUE;
        Iterator<EtatReversi> coups = e.successeursIA(m.getAdversaire(e));
        while (coups.hasNext()) {
            EtatReversi c = coups.next();
            int val = min(e, depth - 1);
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    public int min(EtatReversi e, int depth) {
        return 0;
    }
}
