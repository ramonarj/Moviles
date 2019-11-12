package es.ucm.fdi.moviles.engine;

public abstract class AbstractGraphics implements  Graphics
{
    int canvasWidth, canvasHeight;

    @Override
    public abstract void setCanvasSize(int x, int y);

    //NOTA: ahora mismo esto no sirve para nada
    @Override
    public void drawImage(Image image, int posX, int posY)
    {
        drawRealImage(image,posX,posY);
    }



    public abstract void drawRealImage(Image image, int posX, int posY);

    public abstract void drawRealImage(Image image, int posX, int posY, float alpha);

    public abstract void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY);

    public abstract void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY, int rectMin, int rectMax);
}
