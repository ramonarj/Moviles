package com.example.logic;

import java.util.ArrayList;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Input;

public class SwitchDashLogic implements GameState
{
    //Objeto del juego
    private Game game;

    //Imágenes
    Image prueba;
    public SwitchDashLogic(Game game)
    {
        this.game = game;
    }

    @Override
    public boolean init()
    {
        prueba = game.getGraphics().newImage("assets/Chestplate.jpg");
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

        Graphics g = game.getGraphics();
       // g.clear(0xFF0000FF);
        g.drawImage(test, 0, 0,255,(float)g.getWidth()/test.getWidth(),(float)  g.getHeight()/test.getHeigth());

    }

    @Override
    public  void setImage(Image im){test=im;}
    public void setWidth(int width){width_=width;}
    public void setHeight(int height){height_=height;}


    public Image test=null;
    private int width_;
    private int height_;
}
