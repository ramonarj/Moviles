package es.ucm.fdi.moviles.pcmodule;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;

import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;

public class PCGraphics  implements Graphics {

    //Ventana de la aplicación
    java.awt.Canvas canvas;
    java.awt.Graphics graphics;


    public PCGraphics(java.awt.Canvas canvas)
    {
        this.canvas = canvas;
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
        graphics.setColor(new Color(color));
        graphics.fillRect(0,0, getWidth(), getHeight());
    }

    @Override
    public void drawImage(Image image, int posX, int posY)
    {
        PCImage pcImage = (PCImage)image;
        graphics.drawImage(pcImage.img, posX, posY, null);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha)
    {
        //Castings
        PCImage pcImage = (PCImage)image;
        Graphics2D g2d = (Graphics2D)graphics;

        //Creamos el composite de alpha y lo aplicamos
        Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComp);

        //Pintamos
        graphics.drawImage(pcImage.img, posX, posY, null);

        //Dejamos como estaba
        Composite opaqueComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        g2d.setComposite(alphaComp);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY)
    {
        //Tamaño del rectángulo que se va a pintar (en píxeles)
        int tamX = (int)((float)image.getWidth() * scaleX);
        int tamY = (int)((float)image.getHeigth() * scaleY);

        //Castings
        PCImage pcImage = (PCImage)image;
        Graphics2D g2d = (Graphics2D)graphics;

        //Creamos el composite de alpha y lo aplicamos
        Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComp);

        //Pintamos
        graphics.drawImage(pcImage.img, posX, posY, tamX, tamY, null);

        //Dejamos como estaba
        Composite opaqueComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        g2d.setComposite(alphaComp);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY,
                          int rectMin, int rectMax)
    {
        //graphics.drawImage(image, leftX, upY, rightX, downY, //Los del rectángulo destino
         //                         leftX, upY, rightX, downY, //Los del rectángulo fuente
         //       null);
    }

    @Override
    public int getWidth() {
        return canvas.getWidth();
    }

    @Override
    public int getHeight() {
        return canvas.getHeight();
    }
}
