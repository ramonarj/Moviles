package es.ucm.fdi.moviles.engine.system;

import java.util.HashMap;
import java.util.Map;

import es.ucm.fdi.moviles.engine.graphics.Image;

public class ResourceMan {
    private static ResourceMan ourInstance = null;


    private static Game game;

    static Map<String, Image> images;

    /**
     * Constructora que recibe el game y crea un diccionario de Imagenes
     * @param g Game proporcionado
     */
    private ResourceMan(Game g)
    {
        this.game = g;
        images = new HashMap<String, Image>();
    }

    /**
     * Crea una instancia del ResourceMan que sera utilizada
     * en la posteridad
     * @param g Game proporcionado
     */
    public static void initInstance(Game g)
    {
        ourInstance = new ResourceMan(g);
    }

    /**
     *
     * @return la instancia del ResourceMan
     */
    static ResourceMan getInstance()
    {
        return ourInstance;
    }

    /**
     * Carga una imagen y la guarda en el diccionario con un nombre concreto
     * @param ImagePath ruta de la Imagen a guardar
     * @param name nombre proporcionado a la imagen
     */
    public static void loadImage(String ImagePath, String name)
    {

        Image img = game.getGraphics().newImage(ImagePath);
        images.put(name, img);
    }

    /**
     * Devuelve una imagen con un nombre concreto del diccionario
     * @param name nombre de la imagen del diccionario
     * @return
     */
    public static Image getImage(String name)
    {
        Image img = images.get(name);
        if(img == null)
            System.out.println("El recurso solicitado no existe");
        return images.get(name);
    }
}
