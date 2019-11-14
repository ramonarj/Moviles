package com.example.logic;

import java.util.ArrayList;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Input;
import es.ucm.fdi.moviles.engine.Rect;
import es.ucm.fdi.moviles.engine.ResourceMan;
import es.ucm.fdi.moviles.engine.Sprite;

public class PlayState implements GameState
{
    //Objeto del juego
    private Game game;

    public PlayState(Game game)
    {
        this.game = game;
    }

    @Override
    public boolean init()
    {
        //Carga de recursos
        backgrounds = ResourceMan.getImage("Backgrounds");
        balls = ResourceMan.getImage("Balls");
        flechas = ResourceMan.getImage("Flechas");
        player = ResourceMan.getImage("Players");
        buttons = ResourceMan.getImage("Buttons");



        white = game.getGraphics().newImage("white.png");
        scoreFont = game.getGraphics().newImage("scoreFont.png");

        //Inicialización de las variables
        backgroundNo = (int)Math.floor(Math.random() * 9);
        playerColor = 0;
        score = 0;
        coloresFlechas = new int[]{ 0xff41a85f, 0xff00a885, 0xff3d8eb9, 0xff2969b0,
                0xff553982, 0xff28324e, 0xfff37934, 0xffd14b41, 0xff75706b };

        posFlechas = game.getGraphics().getHeight() / 2;

        //Inicializamos las bolas
        numBolas=6;
        ballColor=new int[numBolas];
        posBolas=new int[numBolas];
        for(int i=0;i<numBolas;i++) {
            posBolas[i] = -(i*395);
            ballColor[i]=balls.getHeight()/2*(int)Math.floor(Math.random() * 2);
        }
        velBolas=430;
        return  true;
    }

    @Override
    public void update(float deltaTime)
    {
        Input input = game.getInput();
        ArrayList<Input.TouchEvent> events = (ArrayList)input.getTouchEvents();
        for(Input.TouchEvent evt: events)
        {
            //Cambiamos de color
            if(evt.type == Input.TouchEvent.EventType.PRESSED)
                playerColor = 1 - playerColor;

            //TODO: registrar clicks en los botones de las esquinas
        }

        if(contBolas%10==0)velBolas+=90;
        //TODO: usar el deltaTime
        posFlechas += (deltaTime*384);

        for(int i=0;i<numBolas;i++) {
            posBolas[i] += (deltaTime * velBolas);
            if(posBolas[i]>game.getGraphics().getHeight()) {
                posBolas[i] = getMenor() - 395;
                ballColor[i]=balls.getHeight()/2*(int)Math.floor(Math.random() * 2);
            }
            else if(match(ballColor[i],posBolas[i]))
            {
                posBolas[i] = getMenor() - 395;
                ballColor[i]=balls.getHeight()/2*(int)Math.floor(Math.random() * 2);
            }
        }

        //TODO: registrar los choques entre jugador y bolas y aumentar la puntuación/acabar el juego
    }

    private int getMenor()
    {
        int value=game.getGraphics().getHeight();
        for(int i=0;i<numBolas;i++)
            if(posBolas[i]<value)value=posBolas[i];
        return value;
    }

    private boolean match(int ballcolor,int posBola) {
        return (posBola>1200 && posBola<1200+player.getHeight() &&((ballcolor==0 &&
               playerColor==0) || (ballcolor==balls.getHeight()/2 && playerColor==1)));
    }

    @Override
    public void render()
    {
        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        g.clear(coloresFlechas[backgroundNo]);

        //1. FONDO
        Rect backRect = new Rect(g.getWidth() / 5,0,3 * g.getWidth() / 5, g.getHeight());
        Rect dstRect=new Rect(backgrounds.getWidth() / 9 * backgroundNo,0,backgrounds.getWidth() / 9,backgrounds.getHeight());
        Sprite backSprite=new Sprite(backgrounds,dstRect,g);
        backSprite.draw(backRect);

        //2. FLECHAS (hay 2 sprites)
        //dstRect = new Rect(g.getWidth() / 5,posFlechas,
               // 3 * g.getWidth() / 5, flechas.getHeight());
        //g.drawImage(flechas, dstRect, 0.25f);
        dstRect = new Rect(g.getWidth() / 5,posFlechas - flechas.getHeight(),
                3 * g.getWidth() / 5, flechas.getHeight());
        g.drawImage(flechas, dstRect, 0.25f);


        //3. PELOTA

        for(int i=0;i<numBolas;i++)
        {
            Rect srcRect=new Rect(balls.getWidth() / 10 * 7,ballColor[i],balls.getWidth() / 10,balls.getHeight() / 2);
            Sprite ballSprite=new Sprite(balls,srcRect,g);
            ballSprite.drawCentered(g.getWidth() / 2,posBolas[i]);
        }

        //4. JUGADOR
        Rect srcRect=new Rect(0,playerColor * player.getHeight() / 2,player.getWidth(),player.getHeight() / 2);
        Sprite playerSprite=new Sprite(player,srcRect,g);
        playerSprite.drawCentered(g.getWidth() / 2,1200);


        //5. GUI
        //Botón de sonido
        srcRect=new Rect(2 * buttons.getWidth() / 10,0, buttons.getWidth() / 10,buttons.getHeight());
        Sprite soundSprite=new Sprite(buttons,srcRect,g);
        soundSprite.drawCentered(50, 200);

        //Botón de info
        srcRect=new Rect(0,0, buttons.getWidth() / 10,buttons.getHeight());
        Sprite infoSprite=new Sprite(buttons,srcRect,g);
        infoSprite.drawCentered(g.getWidth() - 50,200);

        //Puntuación
        srcRect=new Rect(0,0, scoreFont.getWidth() / 15,scoreFont.getHeight() / 6);
        Sprite scoreSprites=new Sprite(scoreFont,srcRect,g);
        scoreSprites.drawCentered(g.getWidth() - 50,400);
    }

    //IMAGENES Y SUS DIMENSIONES:
    //Fondo
    private Image backgrounds; //1 fila, 9 columnas
    private Image white;

    //Objetos
    private Image balls; //2 filas, 10 columnas
    private Image flechas;
    private Image player; //2 filas, 1 columna

    //Textos
    private Image buttons; //1 fila, 10 columnas

    //Fuente
    private Image scoreFont; //6 filas, 15 columnas

    //VARIABLES DE JUEGO:
    private int playerColor; //0 = blanco, 1 = negro
    private int score;
    private int backgroundNo;
    private int posFlechas;
    private int [] coloresFlechas;
    private int contBolas=1;
    private int velBolas;
    private int posBolas[];
    private int numBolas;
    private int ballColor[];
}
