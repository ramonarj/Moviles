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

public class MenuState implements GameState {
    //Objeto del juego
    private Game game;

    public MenuState(Game game)
    {
        this.game = game;
    }

    @Override
    public boolean init()
    {
        backgrounds = ResourceMan.getImage("Backgrounds");
        logo = ResourceMan.getImage("Logo");
        tapToPlay = ResourceMan.getImage("TapToPlay");
        buttons = ResourceMan.getImage("Buttons");

        backgroundNo = (int)Math.floor(Math.random() * 9);
        return true;
    }

    @Override
    public void update(float deltaTime)
    {
        Input input = game.getInput();
        ArrayList<Input.TouchEvent> events = (ArrayList)input.getTouchEvents();
        for(Input.TouchEvent evt: events)
        {
            //Cambiamos al juego
            if(evt.type == Input.TouchEvent.EventType.RELEASED)
                game.setGameState(new PlayState(game));
        }

    }

    @Override
    public void render()
    {
        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        g.clear(0x000000);

        //1. FONDO
        Rect backRect = new Rect(g.getWidth() / 5,0,3 * g.getWidth() / 5, g.getHeight());
        Rect dstRect=new Rect(backgrounds.getWidth() / 9 * backgroundNo,0,backgrounds.getWidth() / 9,backgrounds.getHeight());
        Sprite backSprite=new Sprite(backgrounds,dstRect,g);
        backSprite.draw(backRect);

        //Logo
        dstRect = new Rect(g.getWidth() / 3,356 ,g.getWidth() / 3,g.getHeight() / 6);
        g.drawImage(logo, dstRect, 1f);

        //TapToPlay
        //TODO: hacer que "parpadee"
        dstRect = new Rect(g.getWidth() / 3,950 , //950, 1464 (depende de la pantalla)
                g.getWidth() / 3,g.getHeight() / 30);
        g.drawImage(tapToPlay, dstRect, 1f);

        //Botón de sonido
        Rect srcRect=new Rect(2 * buttons.getWidth() / 10,0, buttons.getWidth() / 10,buttons.getHeight());
        Sprite soundSprite=new Sprite(buttons,srcRect,g);
        soundSprite.drawCentered(50, 200);

        //Botón de info
        srcRect=new Rect(0,0, buttons.getWidth() / 10,buttons.getHeight());
        Sprite infoSprite=new Sprite(buttons,srcRect,g);
        infoSprite.drawCentered(g.getWidth() - 50,200);

    }


    private int backgroundNo;
    private Image backgrounds; //1 fila, 9 columnas
    private Image logo;
    private Image tapToPlay;
    private Image buttons; //1 fila, 10 columnas
}
