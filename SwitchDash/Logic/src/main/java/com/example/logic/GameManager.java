package com.example.logic;

import javax.print.attribute.ResolutionSyntax;

import es.ucm.fdi.moviles.engine.graphics.Graphics;
import es.ucm.fdi.moviles.engine.system.Game;
import es.ucm.fdi.moviles.engine.graphics.Image;
import es.ucm.fdi.moviles.engine.utils.Rect;
import es.ucm.fdi.moviles.engine.system.ResourceMan;
import es.ucm.fdi.moviles.engine.graphics.Sprite;

class GameManager {
    private static GameManager ourInstance = null;
    /**
     * crea la instancia del GameManageer que sera posteriormente usada
     * hasta que acabe el juego
     */
    public static void initInstance(Game game)
    {
        ourInstance = new GameManager(game);
    }

    /**
     * @return la instancia del GameManager
     */
    static GameManager getInstance() {
        return ourInstance;
    }

    /**
     * Carga la imagen de los puntos y se guarda la instancia de Game
     * @param game instancia que nos guardaremos
     */
    private GameManager(Game game)
    {
        this.scoreFont = ResourceMan.getImage("ScoreFont");
        this.game = game;
    }

    /**
     * Establece el color de fondo
     * @param backGroundNo color de fondo
     */
    public void setBackGroundNo(int backGroundNo){this.backGroundNo = backGroundNo;}

    /**
     * Establece el color lateral
     * @param lateralColor color laterañ
     */
    public void setLateralColor(int lateralColor){this.lateralColor = lateralColor;}

    /**
     * Establece la anchura de la barra
     * @param barsWidth anchura de la barra
     */
    public void setBarsWidth(int barsWidth){this.barsWidth = barsWidth;}

    /**
     * @return el color de fondo
     */
    public int getBackGroundNo(){return backGroundNo;}

    /**
     * @return el color del lateral
     */
    public int getLateralColor(){return lateralColor;}

    /**
     * @return el ancho de la barra
     */
    public int getBarsWidth(){return barsWidth;}


    public void setArrowSprite(Image img)
    {
        this.flechas = img;
        this.posFlechas1 = game.getGraphics().getHeight()-flechas.getHeight();
        this.posFlechas2 = posFlechas1-flechas.getHeight();
    }

    /**
     * Pinta dos imagenes de flechas completas haciendo que una se ponga
     * encima de la otra hasta el final de la partida
     */
    public void drawArrows()
    {
        Graphics g  = game.getGraphics();

        Rect dstRect = new Rect(barsWidth,posFlechas1,
                3 * barsWidth, flechas.getHeight());
        g.drawImage(flechas, dstRect, 0.25f);


        Rect dstRect2 = new Rect(barsWidth,posFlechas2 ,
                3 * barsWidth, flechas.getHeight());
        g.drawImage(flechas, dstRect2, 0.25f);
    }

    /**
     * Actualiza las posiciones de las flechas con el uso del deltatime
     * @param deltaTime usado para actualizar las posiciones de las flechas
     */
    public void updateArrows(float deltaTime)
    {
        //Flechas
        int incr = (int)(deltaTime*384);
        posFlechas1 += incr;
        posFlechas2 += incr;


        //Comprobar si hay que moverla al salirse de la pantalla
        if(posFlechas1>game.getGraphics().getHeight())
            posFlechas1=posFlechas2-flechas.getHeight();
        else if(posFlechas2>game.getGraphics().getHeight())
            posFlechas2=posFlechas1-flechas.getHeight();
    }


    /**
     * Metodo generico para pintar numeros que puede ser llamado desde varios estados
     * @param number numero a pintar
     * @param x posicion x donde pintar
     * @param y posicion y donde pintar
     * @param scale escala del numero a pintar
     */
    public void drawNumber(int number, int x, int y, float scale)
    {
        int charWidth = scoreFont.getWidth() / 15 - 16;
        int charHeight = scoreFont.getHeight() / 7 - 24;
        int gap = (int)((float)charWidth * 1.5f);

        //Posición en la spritesheet
        int posicion=7+number;
        int posicionY=3;
        if(posicion>14 && posicion<=16)
        {
            posicion-=15;
            posicionY=4;
        }
        else if(posicion>1 && posicionY==4)
        {
            posicion=7;
            posicionY=3;
        }

        //El 16 y el 24 son la mitad del espacio en blanco que hay en cada caracter (33px en X, 48px en Y)
        Rect srcRect = new Rect(posicion * scoreFont.getWidth() / 15 + 16, posicionY * scoreFont.getHeight() / 7 + 24,
                charWidth, charHeight);
        Sprite scoreSprites = new Sprite(scoreFont, srcRect, game.getGraphics());

        scoreSprites.drawCentered(x, y, scale);
    }

    private int posFlechas1;
    private int posFlechas2;
    private Image flechas;

    private int lateralColor;
    private int backGroundNo;
    private int barsWidth;
    private Image scoreFont;
    private Game game;
}
