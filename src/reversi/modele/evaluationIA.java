package reversi.modele;

import reversi.modele.etat.EtatReversi;

public class evaluationIA
{
    private Modele m;

    public evaluationIA(Modele mod){
        m = mod;
    }

    public int evalIA(int ia1, int ia2){
        int somme1 = 0;
        int somme2 = 0;
        int jeu1 = evalJeu(ia1, ia2);
        somme1 += m.getJetonsNoir();
        somme2 += m.getJetonsBlanc();
        m.reset();
        int jeu2 = evalJeu(ia2, ia1);
        somme1 += m.getJetonsBlanc();
        somme2 += m.getJetonsNoir();
        m.setSomme1(somme1);
        m.setSomme2(somme2);
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
