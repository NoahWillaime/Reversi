package reversi.modele;

import reversi.modele.etat.EtatReversi;

public class evaluationIA
{
    private Modele m;

    public evaluationIA(Modele mod){
        m = mod;
    }

    public int evalIA(int ia1, int ia2){
        int jeu1 = evalJeu(ia1, ia2);
        m.reset();
        System.out.println(jeu1);
        int jeu2 = evalJeu(ia2, ia1);
        System.out.println(jeu2);
        if (jeu1 == ia1 && jeu2 == ia1)
            return 1;
        else if (jeu1 == ia2 && jeu2 == ia2)
            return -1;
        return 0;
    }

    protected int evalJeu(int ia1, int ia2){
        if (!m.passTurn()) {
            m.iaAction(2, ia1);
        } else {
            EtatReversi etat = m.getEtat();
            m.setEtat(new EtatReversi(m.getAdversaire(etat), etat.getPlateau(), etat.getContour(), etat.getResult(), etat.getNbJetonsP2(), etat.getNbJetonsP1()));
            m.setEndGame(m.getEndGame()+1);
        }
        if (!m.passTurn()) {
            m.iaAction(2, ia2);
        } else {
            EtatReversi etat = m.getEtat();
            m.setEtat(new EtatReversi(m.getAdversaire(etat), etat.getPlateau(), etat.getContour(),etat.getResult(), etat.getNbJetonsP2(), etat.getNbJetonsP1()));
            m.setEndGame(m.getEndGame()+1);
        }
        if (m.getEndGame() == 2){
            if (m.getGagnant() == EtatReversi.BLANC){
                return ia1;
            } else {
                return ia2;
            }
        } else {
            m.setEndGame(0);
            return evalJeu(ia1, ia2);
        }
    }
}
