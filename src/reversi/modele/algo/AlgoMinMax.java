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
        Iterator
        return 0;
    }

    public int min(EtatReversi e, int depth) {
        return 0;
    }
}
