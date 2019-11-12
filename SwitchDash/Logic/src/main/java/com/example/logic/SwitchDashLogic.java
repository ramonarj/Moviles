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
        test = game.getGraphics().newImage("Assets/Chestplate.jpg");
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
        game.getGraphics().clear(0x000000);
        Rect rect=new Rect(0,0,test.getWidth(),test.getHeigth());
        Sprite sprite=new Sprite(test,rect,game .getGraphics());
        sprite.draw();
    }

    @Override
    public  void setImage(Image im){test=im;}
    public void setWidth(int width){width_=width;}
    public void setHeight(int height){height_=height;}


    public Image test=null;
    private int width_;
    private int height_;
}
