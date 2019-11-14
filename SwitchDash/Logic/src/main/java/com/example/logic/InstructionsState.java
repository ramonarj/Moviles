package com.example.logic;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Rect;
import es.ucm.fdi.moviles.engine.ResourceMan;
import es.ucm.fdi.moviles.engine.Sprite;

public class InstructionsState implements GameState {


    //Objeto del juego
    private Game game;

    public InstructionsState(Game game)
    {
        this.game = game;
    }

    @Override
    public boolean init()
    {
        howToPlay = ResourceMan.getImage("HowToPlay");
        instructions = ResourceMan.getImage("Instructions");
        tapToPlay = ResourceMan.getImage("TapToPlay");
        buttons = ResourceMan.getImage("Buttons");

        return true;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render() {

        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        g.clear(0x000000);

        //How to play
        Rect dstRect = new Rect(g.getWidth() / 3,290 ,
                g.getWidth() / 3,g.getHeight() / 7);
        //g.drawImage(howToPlay, dstRect, 1f);

        //Instructions
        dstRect = new Rect(g.getWidth() / 4,768 ,
                g.getWidth() / 2, g.getHeight() / 3);
        //g.drawImage(instructions, dstRect, 1f);

        //TapToPlay
        //TODO: hacer que "parpadee"
        dstRect = new Rect(g.getWidth() / 3,1464 , //950, 1464 (depende de la pantalla)
                g.getWidth() / 3,g.getHeight() / 30);
        g.drawImage(tapToPlay, dstRect, 1f);

        //Botón de salir
        Rect srcRect=new Rect(0,0, buttons.getWidth() / 10,buttons.getHeight());
        Sprite infoSprite=new Sprite(buttons,srcRect,g);
        infoSprite.drawCentered(g.getWidth() - 50,200);
    }

    private Image howToPlay;
    private Image instructions;
    private Image tapToPlay;

    private Image buttons; //1 fila, 10 columnas
}
