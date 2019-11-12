package es.ucm.fdi.moviles.pcmodule;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.ImageIcon;

import es.ucm.fdi.moviles.engine.AbstractGraphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Rect;

public class PCGraphics extends AbstractGraphics {

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


    protected void drawImagePrivate(PCImage image, Graphics g, Rect srcRect, Rect destRect)
    {
        //Pintamos
        g.drawImage(image.img,
                destRect.x1(), destRect.y1(), destRect.x2(), destRect.y2(),       //Rectángulo destino
                srcRect.x1(), srcRect.y1(), srcRect.x2(), srcRect.y2(),           //Rectángulo fuente
                null);
    }

    @Override
    public void drawImage(Image image, int posX, int posY)
    {
        PCImage pcImage = (PCImage)image;
        java.awt.Graphics g = strategy.getDrawGraphics();
        g.drawImage(pcImage.img, posX, posY, null);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha)
    {
        //Castings
        java.awt.Graphics g = strategy.getDrawGraphics();
        PCImage pcImage = (PCImage)image;
        Graphics2D g2d = (Graphics2D)g;

        //Creamos el composite de alpha y lo aplicamos
        Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComp);

        //Pintamos
        g.drawImage(pcImage.img, posX, posY, null);

        //Dejamos como estaba
        Composite opaqueComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        g2d.setComposite(alphaComp);
    }


    @Override
    public void drawImage(Image image, int posX, int posY, Rect srcRect)
    {
        //Castings
        PCImage pcImage = (PCImage)image;
        java.awt.Graphics g = strategy.getDrawGraphics();
        Rect destiny = new Rect(posX, posY, srcRect.getWidth(), srcRect.getHeight());
        drawImagePrivate(pcImage, g, srcRect, destiny);
    }

    @Override
    public void drawImage(Image image, Rect destRect, float alpha)
    {
        java.awt.Graphics g = strategy.getDrawGraphics();
        PCImage pcImage = (PCImage)image;
        //Castings
        Graphics2D g2d = (Graphics2D)g;

        //Creamos el composite de alpha y lo aplicamos
        Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComp);

        Rect source = new Rect(0,0, image.getWidth(), image.getHeigth());
        drawImagePrivate(pcImage, g2d, source, destRect);

        //Dejamos como estaba
        Composite opaqueComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        g2d.setComposite(alphaComp);
    }

    @Override
    public void drawImage(Image image, Rect srcRect, Rect destRect, float alpha)
    {
        java.awt.Graphics g = strategy.getDrawGraphics();

        //Castings
        PCImage pcImage = (PCImage)image;
        Graphics2D g2d = (Graphics2D)g;

        //Creamos el composite de alpha y lo aplicamos
        Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComp);

        //Pintamos
        g.drawImage(pcImage.img,
                destRect.x1(), destRect.y1(), destRect.x2(), destRect.y2(),       //Rectángulo destino
                srcRect.x1(), srcRect.y1(), srcRect.x2(), srcRect.y2(),           //Rectángulo fuente
                null);

        //Dejamos como estaba
        Composite opaqueComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        g2d.setComposite(alphaComp);
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
    public void drawRealImage(Image image, int posX, int posY) {

    }

    @Override
    public void drawRealImage(Image image, int posX, int posY, float alpha) {

    }

    @Override
    public void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY) {

    }

    @Override
    public void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY, int rectMin, int rectMax) {

    }
}
