package es.ucm.fdi.moviles.pcmodule;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import es.ucm.fdi.moviles.engine.AbstractGraphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Rect;

public class PCGraphics extends AbstractGraphics
{
    //Ventana de la aplicación
    java.awt.Window window;
    //Gráficos de Java
    java.awt.image.BufferStrategy strategy;


    public PCGraphics()
    {
        this.window = null;
        this.strategy = null;
    }

    public PCGraphics(java.awt.Window window)
    {
        this.window = window;
        this.strategy = window.getBufferStrategy();
    }

    /**
     * Creates a new image
     * @return the image created
     */
    @Override
    public Image newImage(String name)
    {
        name= "Assets/" + name;
        PCImage image = null;
        //TODO: gestionar errores cargando la imagen con un bucle
        try
        {
            java.awt.Image img = javax.imageio.ImageIO.read(new java.io.File(name));
            image = new PCImage(img);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }

        return image;
    }

    /**
     * Clears the content of the screen with the provided color
     * @param color
     */
    @Override
    public void clear(int color)
    {
        java.awt.Graphics g = strategy.getDrawGraphics();
        g.setColor(new Color(color));
        g.fillRect(0,0, getWidth(), getHeight());
    }


    /**
     * Method resposible for the painting of an image using Java graphics.
     * It draws the specified image with the given parameters.
     * All public "drawImage" methods end up calling this one
     * @param image image to be drawn (type java.awt.Image)
     * @param srcRect rectangle of the image (in px) taken
     * @param destRect rectangle of the graphics (in px) where the portion of the image will show
     * @param alpha transparence value (0 = fully transparent, 1 = fully opaque)
     */
    protected void drawImagePrivate(PCImage image, Rect srcRect, Rect destRect, float alpha)
    {
        java.awt.Graphics g = strategy.getDrawGraphics();
        //No need to use alpha compositor
        if(alpha == 1)
        {
            //Pintamos
            g.drawImage(image.img,
                    destRect.x1(), destRect.y1(), destRect.x2(), destRect.y2(),       //Rectángulo destino
                    srcRect.x1(), srcRect.y1(), srcRect.x2(), srcRect.y2(),           //Rectángulo fuente
                    null);
        }

        //Usamos el composite
        else
        {
            //Casting
            Graphics2D g2d = (Graphics2D)g;

            //Creamos el composite de alpha y lo aplicamos
            Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(alphaComp);

            //Pintamos
            g.drawImage(image.img,
                    destRect.x1(), destRect.y1(), destRect.x2(), destRect.y2(),       //Rectángulo destino
                    srcRect.x1(), srcRect.y1(), srcRect.x2(), srcRect.y2(),           //Rectángulo fuente
                    null);

            //Dejamos como estaba
            Composite opaqueComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
            g2d.setComposite(alphaComp);
        }
    }


    @Override
    public int getWidth() {
        return window.getWidth();
    }

    @Override
    public int getHeight() {
        return window.getHeight();
    }

    @Override
    public int getLogicalWidth() {
        return this.logicalWidth;
    }

    @Override
    public int getLogicalHeight() {
        return this.logicalHeight;
    }
    @Override
    public void setCanvasSize(int width, int height)
    {
        windowWidth=width;
        windowHeight=height;
        window.setSize(width,height);
    }


    @Override
    public void drawRealImage(Image image, Rect destRect)
    {
        Rect srcRect = new Rect(0,0, image.getWidth(), image.getHeight());
        drawImagePrivate((PCImage)image, srcRect, destRect,1f);
    }

    @Override
    public void drawRealImage(Image image, Rect destRect, float alpha)
    {
        Rect srcRect = new Rect(0,0, image.getWidth(), image.getHeight());
        drawImagePrivate((PCImage)image, srcRect, destRect, alpha);
    }

    @Override
    public void drawRealImage(Image image, Rect srcRect, Rect destRect)
    {
        drawImagePrivate((PCImage)image, srcRect, destRect, 1f);
    }

    @Override
    public void drawRealImage(Image image, Rect srcRect, Rect destRect, float alpha)
    {
        drawImagePrivate((PCImage)image, srcRect, destRect, alpha);
    }
}
