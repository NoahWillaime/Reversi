package reversi.vue;

import java.util.Observable;
import java.util.Observer;

public class VuePlateau implements Observer {

    public VuePlateau(Observable o) {
        super();
        o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
