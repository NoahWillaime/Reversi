package reversi.modele.algo;

import reversi.modele.etat.EtatReversi;

public class AlgoMinMax {
    public int eval0(EtatReversi e) {
        return e.getNbJetonsP1();
    }

    public int max(EtatReversi e, int depth) {
        return 0;
    }

    public int min(EtatReversi e, int depth) {
        return 0;
    }
}
