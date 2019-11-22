package es.ucm.fdi.moviles.engine.graphics;

/**
 * Interfaz para representar una imagen; contiene métodos para conocer
 * sus dimensiones únicamente
 */
public interface Image
{
    /**
     * Devuelve la anchura de la imagen en píxeles
     * @return anchura de la imagen
     */
    public int getWidth();

    /**
     * Devuelve la altura de la imagen en píxeles
     * @return altura de la imagen
     */
    public int getHeight();
}
