package misc;

import javafx.scene.control.Button;

public class PropertyButton<E> extends Button {

    E property;



    public PropertyButton(E property) {
        this.property = property;
    }

    public E getProperty(){
        return property;
    }
}
