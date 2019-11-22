package es.ucm.fdi.moviles.pcmodule;

import es.ucm.fdi.moviles.engine.graphics.Image;

public class PCImage implements Image
{
    //Imagen bruta de Java (para que no colisione el namespace)
    java.awt.Image img;

    public PCImage(java.awt.Image img)
    {
        this.img = img;
    }

    /**
     * Gets the width of the image
     * @return image width
     */
    @Override
    public int getWidth()
    {
        return img.getWidth(null);
    }

    /**
     * Gets the heigth of the image
     * @return image heigth
     */
    @Override
    public int getHeight()
    {
        return img.getHeight(null);
    }
}
