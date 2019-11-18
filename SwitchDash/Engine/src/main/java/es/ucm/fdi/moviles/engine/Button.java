package es.ucm.fdi.moviles.engine;

/**
 * Clase auxiliar para ayudar con la representación de un botón
 */
public class Button
{
    //Posición que ocupa en la pantalla
    private Rect rect;

    //Sprite utilizado
    private Sprite sprite;

    //Nombre del botón
    private String name;

    /**
     * Constructora
     * @param sprite sprite que usará el botón
     * @param rect posición donde se pintará
     * @param name nombre del botón
     */
    public Button(Sprite sprite, Rect rect, String name)
    {
        this.sprite = sprite;
        this.rect = rect;
        this.name = name;
    }

    /**
     * Dibuja el botón
     */
    public void draw()
    {
        sprite.draw(rect);
    }

    /**
     * Ini
     * @param x coordenada horizontal
     * @param y coordenada vertical
     * @return true si está siendo pulsado
     */
    public boolean isPressed(int x, int y)
    {
        System.out.println("Clicado en X: " + x + ", Y: " + y);
        System.out.println("El botón va del {" + rect.x1() + "," + rect.y1() + "} al {" + rect.x2() + "," + rect.y2() + "}");
        return (x >= rect.x1() && x <= rect.x2() && y >= rect.y1() && y <= rect.y2());
    }

    /**
     * Nombre del botón
     * @return el nombre del botón
     */
    public String getName(){return this.name;}
}
