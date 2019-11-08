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
        g.drawImage(prueba, 0, 0);
        //g.clear(0x0000FF00);
    }
}
