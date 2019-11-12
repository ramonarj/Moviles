package es.ucm.fdi.moviles.pcmodule;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import es.ucm.fdi.moviles.engine.AbstractInput;

/**
 * Implementa la clase Input para PC
 * Nótese que implementamos MouseListener (para la pulsación y liberación)
 * pero también MouseMotionListener (para el movimiento del ratón)
 * El resto de eventos está porque se obliga a sobrecargarlos pero no se usan
 */
public class PCInput extends AbstractInput implements MouseListener, MouseMotionListener
{

    public PCInput()
    {

    }


    //MOUSE LISTENER
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        System.out.println("Pressed");
        TouchEvent evt = new TouchEvent();
        evt.x = mouseEvent.getX();
        evt.y = mouseEvent.getY();
        evt.id = mouseEvent.getID();
        evt.type = TouchEvent.EventType.PRESSED;
        addEvent(evt);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
        System.out.println("Released");
        TouchEvent evt = new TouchEvent();
        evt.x = mouseEvent.getX();
        evt.y = mouseEvent.getY();
        evt.id = mouseEvent.getID();
        evt.type = TouchEvent.EventType.RELEASED;
        addEvent(evt);

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    //MOUSE MOTION LISTENER
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        System.out.println("Moved");
        TouchEvent evt = new TouchEvent();
        evt.x = mouseEvent.getX();
        evt.y = mouseEvent.getY();
        evt.id = mouseEvent.getID();
        evt.type = TouchEvent.EventType.MOVED;
        addEvent(evt);
    }
}
