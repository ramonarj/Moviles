package es.ucm.fdi.moviles.androidModule;

import java.util.List;

import es.ucm.fdi.moviles.engine.Input;

/**
 * Implementa la clase Input para Android
 */
public class AndroidInput implements Input {
    //Lista de eventos recibidos
    List<TouchEvent> events = null;

    @Override
    public List<TouchEvent> getTouchEvents() {
        return null;
    }
}
