package es.ucm.fdi.moviles.engine;

public abstract class AbstractGraphics implements  Graphics{

    //NOTA: ahora mismo esto no sirve para nada
    @Override
    public void drawImage(Image image, int posX, int posY) {
        drawRealImage(image,posX,posY);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha){}

    @Override
    public void drawImage(Image image, int posX, int posY, Rect srcRect){}

    @Override
    public void drawImage(Image image, Rect destRect, float alpha){}


    @Override
    public void drawImage(Image image, Rect srcRect, Rect destRect, float alpha){}



    public abstract void drawRealImage(Image image, int posX, int posY);

    public abstract void drawRealImage(Image image, int posX, int posY, float alpha);

    public abstract void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY);

    public abstract void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY, int rectMin, int rectMax);
}
