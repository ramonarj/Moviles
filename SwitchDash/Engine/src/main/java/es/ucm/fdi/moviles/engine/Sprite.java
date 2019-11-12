package es.ucm.fdi.moviles.engine;

public class Sprite {

    //Imagen que usa el sprite
    private Image img_;
    //Rectángulo fuente de la imagen que se va a pintar
    Rect rect_;
    Graphics graphics_;

    /**
     * Constructora de Sprite
     * @param image imagen que usará el sprite
     * @param rect rectángulo fuente de la imagen que se pintará
     */
    public Sprite(Image image, Rect rect,Graphics graphics)
    {
        this.img_ = image;
        this.rect_ = rect;
        this.graphics_=graphics;
    }

    /**
     * Draws the sprite in a given position
     * @param posX horizontal coordinate
     * @param posY vertical coordinate
     */
    public void draw(int posX, int posY)
    {
        graphics_.drawImage(img_, posX, posY, rect_);
    }

    /**
     * Draws the sprite in a given destiny rectangle.
     * Note that if the recatngle given is a different size from the
     * sprite rectangle, this will cause the sprite to reescalate
     * @param dstRect the rectangle where ew desire to draw the sprite
     */
    public void draw(Rect dstRect)
    {
        graphics_.drawImage(img_,rect_, dstRect, 1f);
    }

    /**
     * Draws the sprite centered in the postion given
     * @param posX horizontal coordinate
     * @param posY vertical coordinate
     */
    public void drawCentered(int posX, int posY)
    {
        graphics_.drawImage(img_,
                posX - rect_.getWidth() / 2, posY - rect_.getHeight() / 2, rect_);
    }
}
