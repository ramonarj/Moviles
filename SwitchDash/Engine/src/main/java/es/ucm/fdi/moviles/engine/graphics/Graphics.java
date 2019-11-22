package es.ucm.fdi.moviles.engine.graphics;

import es.ucm.fdi.moviles.engine.utils.Rect;

public interface Graphics
{
    /**
     * Creates a new image using the given path
     * @param path relative path to the image from the root folder
     * @return
     */
    Image newImage(String path);

    /**
     * Clears the screen using the given color as the filler one
     * @param color Color used to fill the screen
     */
    void clear(int color);



    /**
     * Pinta una imagen en un rectángulo dado usando una transparencia
     * @param image imagen a pintar
     * @param destRect rectángulo destino
     * @param alpha transparencia a aplicarle
     */
    void drawImage(Image image, Rect destRect, float alpha);

    /**
     * Pinta una imagen en un rectángulo dado
     * @param image imagen a pintar
     * @param destRect rectángulo destino
     */
    void drawImage(Image image, Rect destRect);

    /**
     * Pinta una porción de la imagen en un rectángulo dado usando una transparencia
     * @param image imagen a pintar
     * @param srcRect rectángulo origen de la imagen
     * @param destRect rectángulo destino en la pantalla
     * @param alpha transparencia a aplicarle
     */
    void drawImage(Image image, Rect srcRect, Rect destRect, float alpha);

    /**
     * Pinta una porción de la imagen en un rectángulo dado
     * @param image imagen a pintar
     * @param srcRect rectángulo origen de la imagen
     * @param destRect rectángulo destino en la pantalla
     */
    void drawImage(Image image, Rect srcRect, Rect destRect);


    /*
    /**
     * Getter
     * @return window width
<<<<<<< Updated upstream
     */
    //int getWidth();

    /**
     * Getter
     * @return ancho lógico del estado
     */
    int getWidth();

    /**
     * Getter
     * @return ancho logico del estado
     */
    int getHeight();

    /**
     * Getter
     * @return ancho físico del estado
     */
    int getWindowWidth();

    /**
     * Getter
     * @return ancho físico del estado
     */
    int getWindowHeight();


    void setLogicalView();

    /**
     * Sets canvas size manually
     */
    void setCanvasSize(int width,int height);
}
