package es.ucm.fdi.moviles.androidModule;
import android.graphics.Bitmap;


import es.ucm.fdi.moviles.interfacemodule.Image;


public class AndroidImage implements Image
{
    //Bitmap
    Bitmap sprite;

    public AndroidImage()
    {
    }

    //Get sprite width
    public float getWidth()
    {
        return sprite.getWidth();
    }

    //Get sprite heigth
    public float getHeigth()
    {
        return sprite.getHeight();
    }
}
