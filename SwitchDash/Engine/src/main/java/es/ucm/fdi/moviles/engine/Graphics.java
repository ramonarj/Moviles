package es.ucm.fdi.moviles.engine;

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
     * Pinta una imagen en una posición dada
     * @param image imagen a pintar
     * @param posX coordenada horizontal
     * @param posY coordenada vertical
     */
    void drawImage(Image image, int posX, int posY);


    /**
     * Pinta una imagen en una posición dada
     * @param image imagen a pintar
     * @param posX coordenada horizontal
     * @param posY coordenada vertical
     * @param srcRect rectángulo origen de la imagen
     */
    void drawImage(Image image, int posX, int posY, Rect srcRect);

    /**
     * Pinta una imagen en una posición dada usando una transparencia
     * @param image imagen a pintar
     * @param posX coordenada horizontal
     * @param posY coordenada vertical
     * @param alpha transparencia a aplicarle
     */
    void drawImage(Image image, int posX, int posY, float alpha);


    /**
     * Pinta una imagen en un rectángulo dado usando una transparencia
     * @param image imagen a pintar
     * @param destRect rectángulo destino
     * @param alpha transparencia a aplicarle
     */
    void drawImage(Image image, Rect destRect, float alpha);

    /**
     * Pinta una porción de la imagen en un rectángulo dado usando una transparencia
     * @param image imagen a pintar
     * @param srcRect rectángulo origen de la imagen
     * @param destRect rectángulo destino en la pantalla
     * @param alpha transparencia a aplicarle
     */
    void drawImage(Image image, Rect srcRect, Rect destRect, float alpha);


    /**
     * Getter
     * @return window width
     */
    int getWidth();

    /**
     * Getter
     * @return window height
     */
    int getHeight();


    /**
     * Sets canvas size manually
     */
    void setCanvasSize(int width,int height);
}
