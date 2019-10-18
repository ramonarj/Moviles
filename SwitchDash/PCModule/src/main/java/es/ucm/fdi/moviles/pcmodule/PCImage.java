package es.ucm.fdi.moviles.pcmodule;
import java.awt.*;

import es.ucm.fdi.moviles.interfacemodule.Image;

public class PCImage implements Image
{
    //Imagen bruta de Java (para que no colisione el namespace)
    java.awt.Image img;

    public PCImage()
    {
    }

    //Get image width
    public float getWidth()
    {
        return img.getWidth(null);
    }

    //Get image heigth
    public float getHeigth()
    {
        return img.getHeight(null);
    }
}
