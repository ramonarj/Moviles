package es.ucm.fdi.moviles.engine;

import java.util.HashMap;
import java.util.Map;

public class ResourceMan {
    private static ResourceMan ourInstance = null;


    private static Game game;

    static Map<String, Image> images;

    private ResourceMan(Game g)
    {
        this.game = g;
        images = new HashMap<String, Image>();
    }

    // static method to create instance of Singleton class
    public static void initInstance(Game g)
    {
        ourInstance = new ResourceMan(g);
    }


    static ResourceMan getInstance()
    {
        return ourInstance;
    }

    public static void loadImage(String ImagePath, String name)
    {

        Image img = game.getGraphics().newImage(ImagePath);
        images.put(name, img);
    }

    public static Image getImage(String name)
    {
        Image img = images.get(name);
        if(img == null)
            System.out.println("El recurso solicitado no existe");
        return images.get(name);
    }
}
