package es.ucm.fdi.moviles.engine;

public abstract class AbstractGraphics implements  Graphics {

    @Override
    public void drawImage(Image image, Rect destRect, float alpha) {

        //Sigfinica que estamos con la pantalla apaisada
        float factor=takeScaleFactor();
        int newx1=(int)(windowWidth/2-((logicalWidth*factor)/2));
        int newy1=(int)(windowHeight/2-((logicalHeight*factor)/2));

        Rect srcRect=new Rect(0,0,image.getWidth(),image.getHeight());

        Rect newRect=new Rect(0,0,0,0);
        newRect.x1=(int)(newx1+(destRect.x1*factor));
        newRect.y1=(int)(newy1+destRect.y1*factor);
        newRect.x2=(int)(windowWidth/2-((logicalWidth*factor)/2)+destRect.x2*factor);
        newRect.y2=(int)(windowHeight/2-((logicalHeight*factor)/2)+destRect.y2*factor);

        drawRealImage(image,srcRect,newRect,alpha);
    }


    @Override
    public void drawImage(Image image, Rect destRect) {
        //Sigfinica que estamos con la pantalla apaisada
        float factor=takeScaleFactor();
        int newx1=(int)(windowWidth/2-((logicalWidth*factor)/2));
        int newy1=(int)(windowHeight/2-((logicalHeight*factor)/2));

        Rect srcRect=new Rect(0,0,image.getWidth(),image.getHeight());

        Rect newRect=new Rect(0,0,0,0);
        newRect.x1=(int)(newx1+(destRect.x1*factor));
        newRect.y1=(int)(newy1+destRect.y1*factor);
        newRect.x2=(int)(windowWidth/2-((logicalWidth*factor)/2)+destRect.x2*factor);
        newRect.y2=(int)(windowHeight/2-((logicalHeight*factor)/2)+destRect.y2*factor);

        drawRealImage(image,srcRect,newRect);
    }

    @Override
    public void drawImage(Image image, Rect srcRect, Rect destRect, float alpha) {

        //Sigfinica que estamos con la pantalla apaisada
        float factor=takeScaleFactor();
        int newx1=(int)(windowWidth/2-((logicalWidth*factor)/2));
        int newy1=(int)(windowHeight/2-((logicalHeight*factor)/2));
        Rect newRect=new Rect(0,0,0,0);
        newRect.x1=(int)(newx1+(destRect.x1*factor));
        newRect.y1=(int)(newy1+destRect.y1*factor);
        newRect.x2=(int)(windowWidth/2-((logicalWidth*factor)/2)+destRect.x2*factor);
        newRect.y2=(int)(windowHeight/2-((logicalHeight*factor)/2)+destRect.y2*factor);

        drawRealImage(image,srcRect,newRect,alpha);
    }


    @Override
    public void drawImage(Image image, Rect srcRect, Rect destRect) {

        //Sigfinica que estamos con la pantalla apaisada
        float factor=takeScaleFactor();
        int newx1=(int)(windowWidth/2-((logicalWidth*factor)/2));
        int newy1=(int)(windowHeight/2-((logicalHeight*factor)/2));
        Rect newRect=new Rect(0,0,0,0);
        newRect.x1=(int)(newx1+(destRect.x1*factor));
        newRect.y1=(int)(newy1+destRect.y1*factor);
        newRect.x2=(int)(windowWidth/2-((logicalWidth*factor)/2)+destRect.x2*factor);
        newRect.y2=(int)(windowHeight/2-((logicalHeight*factor)/2)+destRect.y2*factor);

        drawRealImage(image,srcRect,newRect);
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


    //Métodos que sobreescribiran las clases hijas haciendo que la imagen se pinta de una forma concreta
    //depediendo la plataforma
    public abstract void drawRealImage(Image image, Rect destRect);
    public abstract void drawRealImage(Image image, Rect destRect,float alpha);
    public abstract void drawRealImage(Image image, Rect srcRect, Rect destRect);
    public abstract void drawRealImage(Image image, Rect srcRect, Rect destRect, float alpha);
}