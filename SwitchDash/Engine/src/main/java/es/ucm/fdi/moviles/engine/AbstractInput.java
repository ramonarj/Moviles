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
     * @return lista de eventos, de tamaño 0 si no ha habido
     */
    @Override
    public List<TouchEvent> getTouchEvents()
    {
        if(events.size() > 0)
        {
            //Creamos una nueva lista que sea copia de "events"

            ArrayList<TouchEvent> aux =  (ArrayList<TouchEvent>) events.clone();
            //List<TouchEvent> aux = new ArrayList<TouchEvent>(events.size());
            //Collections.copy(aux, events);

            //Vaciamos "events" para la próxsima invocación
            events.clear();
            return aux;
        }
        return events;
    }
}
