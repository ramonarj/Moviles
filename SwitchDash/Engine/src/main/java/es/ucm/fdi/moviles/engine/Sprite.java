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
     * Draws the sprite in a given destiny rectangle.
     * Note that if the recatngle given is a different size from the
     * sprite rectangle, this will cause the sprite to reescalate
     * @param dstRect the rectangle where ew desire to draw the sprite
     */
    public void draw(Rect dstRect)
    {
        graphics_.drawImage(img_,rect_, dstRect);
    }

    public void draw(Rect dstRect,int alpha)
    {
        graphics_.drawImage(img_,rect_, dstRect,alpha);
    }

    /**
     * Draws the sprite centered in the postion given
     * @param posX horizontal coordinate
     * @param posY vertical coordinate
    */

    public void drawCentered(int posX, int posY)
    {
        Rect dest=new Rect(posX-rect_.getWidth(),posY-rect_.getHeight(),posX+rect_.getWidth(),posY+rect_.getHeight());
        graphics_.drawImage(img_,rect_,dest);
    }



    public Image getImage(){return this.img_;}

}
