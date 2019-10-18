package es.ucm.fdi.moviles.pcmodule;

import es.ucm.fdi.moviles.interfacemodule.Graphics;
import es.ucm.fdi.moviles.interfacemodule.Image;

public class PCGraphics implements Graphics{
    public PCGraphics(){}


    //Create new image
    public Image newImage(String name)
    {
        PCImage image = new PCImage();
        //TODO: gestionar errores cargando la imagen con un bucle
        try
        {
            image.img = javax.imageio.ImageIO.read(new java.io.File(name));
        }
        catch(Exception e)
        {
            System.err.println(e);
        }

        return image;
    }
}
