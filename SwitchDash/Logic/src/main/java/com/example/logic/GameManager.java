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

    //SOLO EN MAYUSCULAS
    public void drawText(String text, int x, int y, float scale)
    {
        int charWidth = scoreFont.getWidth() / 15 - 16;
        int charHeight = scoreFont.getHeight() / 7 - 24;
        int gap = (int)((charWidth / 1.2f) * scale);

        int rows = 7;
        int cols = 15;
        //Pintamos cada una de las letras
        for(int i = 0; i< text.length();i++)
        {
            //Pasamos a ascii y contamos la a como 0
            int pos = ((int)text.charAt(i)) - 65;

            int posX, posY;
            if(pos < cols)
                posX = pos;
            else
                posX = pos % cols;

            posY =  pos / cols;

            System.out.println("PosX: " + posX + "Pos Y: " + posY);

            //Pintamos
            Rect srcRect = new Rect(posX * scoreFont.getWidth() / cols + 16, posY * scoreFont.getHeight() / rows + 24,
                    charWidth, charHeight);
            Sprite scoreSprites = new Sprite(scoreFont, srcRect, game.getGraphics());
            scoreSprites.drawCentered(x + i * gap, y, scale);
        }
    }

    public void drawNumber(int number, int x, int y, float scale, int numDigits)
    {

        int charWidth = scoreFont.getWidth() / 15 - 16;
        int charHeight = scoreFont.getHeight() / 7 - 24;

        int auxNumber = number;
        int gap = 125;

        //Pintamos una vez por cada dígito (de derecha a izquierda)
        for(int i=0;i<numDigits;i++)
        {
            //Número que queremos pintar
            int numeroApintar = auxNumber % 10;
            auxNumber /= 10;

            //Posición en la spritesheet
            int posicion=7+numeroApintar;
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

            scoreSprites.drawCentered(x + gap / 2 *(numDigits- 1) - gap * i, y, scale);
        }
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
