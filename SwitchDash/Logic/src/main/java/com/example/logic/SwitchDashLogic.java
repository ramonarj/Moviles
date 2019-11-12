package com.example.logic;

import java.util.ArrayList;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Input;
import es.ucm.fdi.moviles.engine.Rect;
import es.ucm.fdi.moviles.engine.Sprite;

public class SwitchDashLogic implements GameState
{
    //Objeto del juego
    private Game game;

    public SwitchDashLogic(Game game)
    {
        this.game = game;
    }

    @Override
    public boolean init()
    {
        fondo =  game.getGraphics().newImage("Assets/backgrounds.png");
        bola =  game.getGraphics().newImage("Assets/balls.png");
        flechas =  game.getGraphics().newImage("Assets/arrowsBackground.png");
        return true;
    }

    @Override
    public void update(float deltaTime)
    {
        Input input = game.getInput();
        ArrayList<Input.TouchEvent> events = (ArrayList)input.getTouchEvents();
        for(Input.TouchEvent evt: events)
        {
            System.out.println("X:" + evt.x + ", Y: " + evt.y);
        }
    }

    @Override
    public void render()
    {
        //Colores para el fondo:
        //0x41a85f, 0x00a885, 0x3d8eb9, 0x2969b0, 0x553982, 0x28324e, 0xf37934,
        //        0xd14b41 y 0x75706b
        Graphics g = game.getGraphics();
        g.clear(0x000000);


        //1. FONDO

        Rect fullRect = new Rect(0,0,g.getWidth(), g.getHeight());

        Rect backgroundRect=new Rect(0,0,fondo.getWidth() / 9,fondo.getHeigth());
        Sprite backSprite=new Sprite(fondo,backgroundRect,g);
        backSprite.draw(fullRect);


        //2. FLECHAS
        g.drawImage(flechas, fullRect, 0.5f);

        //3. PELOTA
        Rect ballRect=new Rect(bola.getWidth() / 10 * 7,0,bola.getWidth() / 10,bola.getHeigth() / 2);
        Sprite ballSprite=new Sprite(bola,ballRect,g);
        ballSprite.drawCentered(g.getWidth() / 2,g.getHeight()/2);
    }

    //@Override
    //public  void setImage(Image im){fondo=im;}
    public void setWidth(int width){width_=width;}
    public void setHeight(int height){height_=height;}


    public Image fondo;
    public Image bola;
    public Image flechas;

    private int width_;
    private int height_;
}
