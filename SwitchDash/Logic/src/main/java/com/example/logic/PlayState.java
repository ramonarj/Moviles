package com.example.logic;

import java.util.ArrayList;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Input;
import es.ucm.fdi.moviles.engine.Rect;
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
        fondo =  game.getGraphics().newImage("backgrounds.png");
        bola =  game.getGraphics().newImage("balls.png");
        flechas =  game.getGraphics().newImage("arrowsBackground.png");
        player =  game.getGraphics().newImage("players.png");
        backgroundNo = (int)Math.floor(Math.random() * 9);
        playerColor = 0;
        coloresFlechas = new int[]{ 0x41a85f, 0x00a885, 0x3d8eb9, 0x2969b0,
                0x553982, 0x28324e, 0xf37934, 0xd14b41, 0x75706b };
        //posFlechas = game.getGraphics().getHeight() / 2;
        posFlechas=0;
        return true;
    }

    @Override
    public void update(float deltaTime)
    {
        Input input = game.getInput();
        ArrayList<Input.TouchEvent> events = (ArrayList)input.getTouchEvents();
        for(Input.TouchEvent evt: events)
        {
            //Cambiamos de color
            if(evt.type == Input.TouchEvent.EventType.RELEASED)
                playerColor = 1 - playerColor;
        }
    }

    @Override
    public void render()
    {
        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        g.clear(0x000000FF);

        //1. FONDO
        Rect fullRect = new Rect(0,0,g.getLogicalWidth(), g.getLogicalHeight());
        Rect backgroundRect=new Rect(fondo.getWidth() / 9 * backgroundNo,0,fondo.getWidth() / 9,fondo.getHeight());
        Sprite backSprite=new Sprite(fondo,backgroundRect,g);
        backSprite.draw(fullRect,100);

        /*
        //2. FLECHAS
        Rect rectFlechas = new Rect(0,posFlechas - flechas.getHeight() / 2, g.getWidth(), flechas.getHeight());
        g.drawImage(flechas, rectFlechas, 100);
       // posFlechas +=10;


        //3. PELOTA
        Rect ballRect=new Rect(bola.getWidth() / 10 * 7,0,bola.getWidth() / 10,bola.getHeight() / 2);
        Sprite ballSprite=new Sprite(bola,ballRect,g);
        ballSprite.drawCentered(g.getWidth() / 2,g.getHeight()/4);

        //4. JUGADOR
        Rect playerRect=new Rect(0,playerColor * player.getHeight() / 2,player.getWidth(),player.getHeight() / 2);
        Sprite playerSprite=new Sprite(player,playerRect,g);
        playerSprite.drawCentered(g.getWidth() / 2,g.getHeight()/2);

         */
    }

    //@Override
    //public  void setImage(Image im){fondo=im;}
    //public void setWidth(int width){width_=width;}
    //public void setHeight(int height){height_=height;}


    private Image fondo;
    private Image bola;
    private Image flechas;
    private Image player;
    private int playerColor; //1 = blanco, 2 = negro
    private int backgroundNo;
    private int posFlechas;
    private int [] coloresFlechas;
}
