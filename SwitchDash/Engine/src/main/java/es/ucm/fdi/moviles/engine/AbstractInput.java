package es.ucm.fdi.moviles.engine;

import java.util.ArrayList;
import java.util.List;
/**
 * Implementa las funcionalidades comunes de la clase para cualquier plataforma
 */
public abstract class AbstractInput implements Input
{
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
    synchronized public List<TouchEvent> getTouchEvents()
    {
        if(events.size() > 0)
        {
            //Creamos una nueva lista que sea copia de "events"
            ArrayList<TouchEvent> aux =  (ArrayList<TouchEvent>) events.clone();

            //Vaciamos "events" para la próxsima invocación
            events.clear();
            return aux;
        }
        return events;
    }

    /**
     * Añade un evento a la lista, usa el synchronized porque es posible que tratemos de acceder
     * a la vez que otro método (getTouchEvents), así que pedimos permiso al semáforo del objeto.
     * @param evt El evento a añadir a la cola
     */
    synchronized protected void addEvent(TouchEvent evt)
    {
        //TODO: hay que pasar las coordenadas de reales a lógicas
        //para queal recibir los eventos, el usuario los tenga así
        events.add(evt);
    }
}
