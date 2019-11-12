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

    public void draw()
    {
        graphics_.drawImage(img_,rect_.getX(),rect_.getY());
    }
}
