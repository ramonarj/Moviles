package es.ucm.fdi.moviles.engine.utils;

public class Point
{
    private int x;
    private int y;

    /**
     * Constructora que recibe dos coordenadas donde se generara el punto
     * @param x
     * @param y
     */
    public Point(int x, int y)
    {
        this.x=x;
        this.y=y;
    }

    /**
     *
     * @return la coordenada X
     */
    public int getX(){return this.x;}

    /**
     *
     * @return la coordenada Y
     */
    public int getY(){return this.y;}
}
