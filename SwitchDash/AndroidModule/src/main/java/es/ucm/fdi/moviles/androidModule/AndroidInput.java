package es.ucm.fdi.moviles.androidModule;

import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import es.ucm.fdi.moviles.engine.AbstractInput;
import es.ucm.fdi.moviles.engine.Input;

/**
 * Implementa la clase Input para Android
 */
public class AndroidInput extends AbstractInput implements View.OnTouchListener{

    public AndroidInput()
    {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        //Multitouch ( puede haber varias pulsaciones a la vez)
        //TODO: que funcione el multitouch de verdad (ahora mismo lanza excepción)
        final int pointerCount = event.getPointerCount();
        for (int p = 0; p < pointerCount; p++)
        {
            //Un evento nuestro por cada una
            Input.TouchEvent evt = new TouchEvent();
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                evt.type = TouchEvent.EventType.PRESSED;
            else if (event.getAction() == MotionEvent.ACTION_MOVE)
                evt.type = TouchEvent.EventType.MOVED;
            else if (event.getAction() == MotionEvent.ACTION_UP)
                evt.type = TouchEvent.EventType.RELEASED;
            else
                break;

            evt.x = (int)event.getX();
            evt.y = (int)event.getY();
            evt.id = event.getPointerId(p);
            System.out.println("Evento de tipo " + evt.type.toString() + "en {" + evt.x + ", " + evt.y + "}");
            addEvent(evt);
        }
        return true; //El evento se consume
    }
}
