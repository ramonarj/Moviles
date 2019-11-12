package es.ucm.fdi.moviles.androidModule;
import android.graphics.Bitmap;


import es.ucm.fdi.moviles.engine.Image;


public class AndroidImage implements Image
{
    //Bitmap
    Bitmap sprite;

    public AndroidImage( Bitmap sprite)
    {
        this.sprite = sprite;
    }

    //Get sprite width
    public int getWidth()
    {
        return sprite.getWidth();
    }

    //Get sprite heigth
    public int getHeight()
    {
        return sprite.getHeight();
    }
}
