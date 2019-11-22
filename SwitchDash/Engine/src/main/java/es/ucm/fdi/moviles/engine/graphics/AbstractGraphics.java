package es.ucm.fdi.moviles.engine.graphics;

import es.ucm.fdi.moviles.engine.utils.Point;
import es.ucm.fdi.moviles.engine.utils.Rect;

/**
 * Clase intermedia entre la interfaz y las implementaciones de cada plataforma
 * Esta clase proporciona reescalado para pasar de coordenadas
 * lógicas a coordenadas reales, dejando a las clases que hereden de ella
 * únicamente de implementar los métodos "drawRealImage", ya en coordenadas reales del canvas
 */
public abstract class AbstractGraphics implements  Graphics {

    @Override
    public void drawImage(Image image, Rect destRect, float alpha)
    {
        Rect srcRect=new Rect(0,0,image.getWidth(),image.getHeight());
        Rect newRect = realDestRect(destRect);
        drawRealImage(image,srcRect,newRect,alpha);
    }


    @Override
    public void drawImage(Image image, Rect destRect)
    {
        Rect srcRect=new Rect(0,0,image.getWidth(),image.getHeight());
        Rect newRect = realDestRect(destRect);
        drawRealImage(image,srcRect,newRect);
    }

    @Override
    public void drawImage(Image image, Rect srcRect, Rect destRect, float alpha) {

        Rect newRect = realDestRect(destRect);
        drawRealImage(image,srcRect,newRect,alpha);
    }


    @Override
    public void drawImage(Image image, Rect srcRect, Rect destRect)
    {

        Rect newRect = realDestRect(destRect);
        drawRealImage(image,srcRect,newRect);
    }


    /**
     * Calcula el rectángulo de destino real, i.e, recibe el rectángulo en posiciones lógicas
     * y calcula las posiciones reales del canvas en las que se tendrá que dibujar
     * @param destRect rectángulo en coordenadas lógicas
     * @return rectángulo en coordenadas reales
     */
    private Rect realDestRect(Rect destRect)
    {
        //Sigfinica que estamos con la pantalla apaisada
        float factor=takeScaleFactor();
        int newx1=(int)(windowWidth/2-((logicalWidth*factor)/2));
        int newy1=(int)(windowHeight/2-((logicalHeight*factor)/2));

        Rect newRect=new Rect(0,0,0,0);
        newRect.x1=(int)(newx1+(destRect.x1*factor));
        newRect.y1=(int)(newy1+destRect.y1*factor);
        newRect.x2=(int)(windowWidth/2-((logicalWidth*factor)/2)+destRect.x2*factor);
        newRect.y2=(int)(windowHeight/2-((logicalHeight*factor)/2)+destRect.y2*factor);


        return newRect;
    }

    /**
     * Reescala un punto en coordenadas fisicas a logicas,
     * para asbtraer a la logica del tamaño del dispositivo
     * @param physical
     * @return
     */
    public Point physicalToLogical(Point physical)
    {
        float factor = takeScaleFactor();
        int newx1=(int)(logicalWidth/2-((windowWidth/factor)/2));
        int newy1=(int)(logicalHeight/2-((windowHeight/factor)/2));
        return new Point((int)(newx1 + (float)physical.getX() / factor), (int)( newy1 + (float)physical.getY() / factor));
    }


    @Override
    public void setLogicalView()
    {
        logicalWidth=1080;
        logicalHeight=1920;
    }

    @Override
    public void setCanvasSize(int width,int height)
    {
        windowWidth=width;
        windowHeight=height;
    }



    private float takeScaleFactor()
    {
        float widthFactor = windowWidth / logicalWidth;
        float heigthFactor = windowHeight / logicalHeight;

        if (widthFactor < heigthFactor) return widthFactor;
        else return  heigthFactor;
    }

    protected int logicalWidth;
    protected int logicalHeight;
    protected float windowWidth;
    protected float windowHeight;

    @Override
    public int getWidth() {
        return this.logicalWidth;
    }

    @Override
    public int getHeight() {
        return this.logicalHeight;
    }

    @Override
    public int getWindowWidth() {
        return (int)this.windowWidth;
    }

    @Override
    public int getWindowHeight() {
        return (int)this.windowHeight;
    }


    //Métodos que sobreescribiran las clases hijas haciendo que la imagen se pinta de una forma concreta
    //depediendo la plataforma
    public abstract void drawRealImage(Image image, Rect destRect);
    public abstract void drawRealImage(Image image, Rect destRect,float alpha);
    public abstract void drawRealImage(Image image, Rect srcRect, Rect destRect);
    public abstract void drawRealImage(Image image, Rect srcRect, Rect destRect, float alpha);
}