package es.ucm.fdi.moviles.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Implementa las funcionalidades comunes de la clase para cualquier plataforma
 */
public class AbstractInput implements Input {
    protected ArrayList<TouchEvent> events;

    public AbstractInput()
    {
        events = new ArrayList<TouchEvent>();
    }

    /**
     * Devuelve la lista de eventos producidos dede la última llamada a la función
     * @return lista de eventos
     */
    @Override
    public List<TouchEvent> getTouchEvents()
    {
        //Creamos una nueva lista que sea copia de "events"
        List<TouchEvent> aux = new ArrayList<TouchEvent>();
        Collections.copy(aux, events);

        //Vaciamos "events" para la próxima invocación
        events.clear();

        return aux;
    }
}
