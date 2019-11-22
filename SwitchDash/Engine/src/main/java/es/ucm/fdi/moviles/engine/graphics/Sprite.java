package es.ucm.fdi.moviles.engine.graphics;

import es.ucm.fdi.moviles.engine.utils.Rect;

/**
 * Clase para representar un Sprite
 * Almacena una imagen y un rectángulo fuente de esta para automatizar
 * el pintado de partes específicas de imágenes (especialmente útil para spritesheets)
 * Tambíén contiene una referencia a los gráficos usados para pintarla.
 */
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

    public void draw(Rect dstRect,float alpha)
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
        Rect dest=new Rect(posX-rect_.getWidth() / 2,posY-rect_.getHeight() / 2,rect_.getWidth(),rect_.getHeight());
        graphics_.drawImage(img_,rect_,dest);
    }

    public void drawCentered(int posX, int posY,float scale,float alpha)
    {

        Rect dest=new Rect(posX-rect_.getWidth() / (int)(2/scale),posY-rect_.getHeight() / (int)(2/scale),
                (int)((float)rect_.getWidth() * scale),(int)((float)rect_.getHeight() * scale));
        graphics_.drawImage(img_,rect_,dest,alpha);
    }


    public void drawCentered(int posX, int posY, float scale)
    {
        Rect dest=new Rect(posX-rect_.getWidth() / 2,posY-rect_.getHeight() / 2,
                (int)((float)rect_.getWidth() * scale),(int)((float)rect_.getHeight() * scale));
        graphics_.drawImage(img_,rect_,dest);
    }

    public int getWidth(){return this.rect_.getWidth();}
    public int getHeight(){return this.rect_.getHeight();}



    public Image getImage(){return this.img_;}

}
