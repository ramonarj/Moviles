package es.ucm.fdi.moviles.engine;

public interface Graphics
{
    //Create new image
    public Image newImage(String name);

    //Clears
    void clear(int color);

    //CONSTRUCTORAS:
    //Normal
    void drawImage(Image image, int posX, int posY);

    //Con alfa
    void drawImage(Image image, int posX, int posY, float alpha);

    //Escalada
    void drawImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY);

    //Escalada y con porción
    void drawImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY,
                   int rectMin, int rectMax);



    //Tamaño de la ventana
    int getWidth();
    int getHeight();
}
