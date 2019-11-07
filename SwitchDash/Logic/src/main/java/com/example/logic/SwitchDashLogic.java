package com.example.logic;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Input;

public class SwitchDashLogic implements GameState
{
    private Game game;
    public SwitchDashLogic(Game game)
    {
        this.game = game;
    }

    @Override
    public void update(float deltaTime)
    {

        Input input = game.getInput();
    }

    @Override
    public void render()
    {
        //TODO: hay que gestionar errores con el strategy.contentsLost()
        Graphics g = game.getGraphics();
        g.clear(0x00);

    }
}
