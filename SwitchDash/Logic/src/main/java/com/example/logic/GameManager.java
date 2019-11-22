package com.example.logic;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Rect;
import es.ucm.fdi.moviles.engine.ResourceMan;
import es.ucm.fdi.moviles.engine.Sprite;

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
     *
     * @return la instancia del GameManager
     */
    static GameManager getInstance() {
        return ourInstance;
    }

    private GameManager(Game game)
    {
        this.scoreFont = ResourceMan.getImage("ScoreFont");
        this.game = game;
    }

    public void setBackGroundNo(int backGroundNo){this.backGroundNo = backGroundNo;}
    public void setLateralColor(int lateralColor){this.lateralColor = lateralColor;}
    public void setBarsWidth(int barsWidth){this.barsWidth = barsWidth;}


    public int getBackGroundNo(){return backGroundNo;}
    public int getLateralColor(){return lateralColor;}
    public int getBarsWidth(){return barsWidth;}


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

    private int lateralColor;
    private int backGroundNo;
    private int barsWidth;
    private Image scoreFont;
    private Game game;
}
