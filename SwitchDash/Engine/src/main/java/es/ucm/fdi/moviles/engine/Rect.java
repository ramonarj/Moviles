package es.ucm.fdi.moviles.engine;

/**
 * Clase usada para representar un rectángulo
 */
public class Rect {

    //Coordenadas de la esquina superior izquierda del rectángulo
    private int x;
    private int y;

    //Anchura y altura del rectángulo
    private int w;
    private int h;

    /**
     *
     * @param x Coordenada x de la esquina superior izquierda
     * @param y Coordenada y de la esquina superior izquierda
     * @param width Anchura del rectángulo
     * @param height Altura del rectángulo
     */
    public Rect(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
    }

    //Getters

    //Anchura y altura
    int getWidth() {return w;};
    int getHeight() {return h;};

    //Esquina superior izquierda
    int getX() {return x;};
    int getY() {return y;};
}
