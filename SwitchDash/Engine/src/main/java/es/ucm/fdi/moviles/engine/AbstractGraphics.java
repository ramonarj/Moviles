package es.ucm.fdi.moviles.engine;

public abstract class AbstractGraphics implements  Graphics{

    @Override
    public void drawImage(Image image, int posX, int posY) {
        drawRealImage(image,posX,posY);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha) {
    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY) {

    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY, int rectMin, int rectMax) {

    }

    @Override
    public abstract int getWidth();

    @Override
    public abstract int getHeight();


    //Métodos abstractos que no heredan de la interfaz directamente
    public abstract void drawRealImage(Image image, int posX, int posY);

    public abstract void drawRealImage(Image image, int posX, int posY, float alpha);

    public abstract void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY);

    public abstract void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY, int rectMin, int rectMax);
}
