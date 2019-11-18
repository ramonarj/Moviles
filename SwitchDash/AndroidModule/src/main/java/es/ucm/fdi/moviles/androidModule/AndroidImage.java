package es.ucm.fdi.moviles.androidModule;
import android.graphics.Bitmap;


import es.ucm.fdi.moviles.engine.Image;

/**
 * Clase que implementa la interfaz "Imagen" única y exclusivamente
 * para la plataforma de Android. Usa un bitmap para renderizar.
 */
public class AndroidImage implements Image
{
    //Mapa de bits
    Bitmap sprite;

    /**
     * Constructora
     * Recibe un bitmap (YA CREADO) para inicializar su atributo de esa clase
     * @param sprite bitmap que usará la imagen
     */
    public AndroidImage( Bitmap sprite)
    {
        this.sprite = sprite;
    }

    /**
     * Devuelve la anchura de la imagen
     * @return anchura de la imagen en píxeles
     */
    public int getWidth()
    {
        return sprite.getWidth();
    }

    /**
     * Devuelve la altura de la imagen
     * @return altura de la imagen en píxeles
     */
    public int getHeight()
    {
        return sprite.getHeight();
    }
}
