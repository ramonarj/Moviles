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
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public void drawRealImage(Image image, int posX, int posY){};

    public void drawRealImage(Image image, int posX, int posY, float alpha){};

    public void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY){};

    public void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY, int rectMin, int rectMax){};
}
