package es.ucm.fdi.moviles.engine;

import java.util.List;

/**
 * Gestiona el input
 */
public interface Input
{
    /**
     * Representa la información de un toque sobre la pantalla
     */
    public static class TouchEvent
    {
        //Tipo enumerado para el tipo de evento
        public enum EventType {PRESSED, RELEASED, MOVED}

        //Tipo (pulsacion, liberacion, desplazamiento)
        public EventType type;

        //Posicion
        public int x;
        public int y;

        //Identificador del dedo/boton
        public int id;
    }

    //Lista de eventos
    List<TouchEvent> getTouchEvents();
}
