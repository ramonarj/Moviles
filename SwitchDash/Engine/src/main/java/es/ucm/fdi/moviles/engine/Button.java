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
    private Sprite spriteToggle;

    boolean toggled;

    //Nombre del botón
    private String name;

    /**
     * Constructora con rectángulo destino
     * @param sprite sprite que usará el botón
     * @param rect posición donde se pintará
     * @param name nombre del botón
     */
    public Button(Sprite sprite, Rect rect, String name)
    {
        this.sprite = sprite;
        this.rect = rect;
        this.name = name;
        this.spriteToggle = null;
        this.toggled = false;
    }

    /**
     * Constructora con posición (el botón se pintará centrado en esa posición)
     * @param sprite sprite que usará el botón
     * @param X posición X se pintará
     * @param name nombre del botón
     */
    public Button(Sprite sprite, int X, int Y, String name)
    {
        int width = sprite.rect_.getWidth();
        int height = sprite.rect_.getHeight();

        this.sprite = sprite;
        this.rect = new Rect(X - width / 2, Y - height / 2, width, height);
        this.name = name;
        this.spriteToggle = null;
        this.toggled = false;
    }

    /**
     * Constructora con posición (el botón se pintará centrado en esa posición)
     * @param sprite sprite que usará el botón
     * @param X posición X se pintará
     * @param name nombre del botón
     */
    public Button(Sprite sprite,Sprite toggle, int X, int Y, String name)
    {
        int width = sprite.rect_.getWidth();
        int height = sprite.rect_.getHeight();

        this.sprite = sprite;
        this.spriteToggle = toggle;
        this.rect = new Rect(X - width / 2, Y - height / 2, width, height);
        this.name = name;
        this.toggled = false;
    }


    /**
     * Dibuja el botón
     */
    public void draw()
    {
        if(toggled)
            spriteToggle.draw(rect);
        else
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

    public void toggleSprite()
    {
        if(spriteToggle != null)
            toggled = !toggled;
    }
}
