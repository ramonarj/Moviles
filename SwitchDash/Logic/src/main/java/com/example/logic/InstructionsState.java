package com.example.logic;

import java.util.ArrayList;

import es.ucm.fdi.moviles.engine.Button;
import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Input;
import es.ucm.fdi.moviles.engine.Rect;
import es.ucm.fdi.moviles.engine.ResourceMan;
import es.ucm.fdi.moviles.engine.Sprite;

public class InstructionsState implements GameState {


    //Objeto del juego
    private Game game;

    public InstructionsState(Game game,int backGroundNo,int lateralColor, int barsWidth)
    {
        this.game = game;
        this.backGroundNo =backGroundNo;
        this.lateralColor=lateralColor;
        this.barsWidth = barsWidth;
    }

    @Override
    public boolean init()
    {
        Graphics g = game.getGraphics();

        backgrounds = ResourceMan.getImage("Backgrounds");
        howToPlay = ResourceMan.getImage("HowToPlay");
        instructions = ResourceMan.getImage("instructions");
        tapToPlay = ResourceMan.getImage("TapToPlay");
        buttons = ResourceMan.getImage("Buttons");

        barsWidth = g.getWidth() / 5;

        int buttonWidth = buttons.getWidth() / 10;
        int buttonHeight = buttons.getHeight();

        //Botón de instrucciones
        Rect srcRect =new Rect(buttonWidth,0, buttonWidth ,buttonHeight);
        Sprite infoSprite=new Sprite(buttons,srcRect,g);
        closeButton = new Button(infoSprite, g.getWidth() - barsWidth / 2, 200, "Cerrar");

        veloidad=0.6f;
        alphaTap=1f;

        return true;
    }

    @Override
    public void update(float deltaTime) {
        alphaTap+=(deltaTime*veloidad);
        if(alphaTap>=1f || alphaTap<=0)
        {
            if(alphaTap<=0)alphaTap=0;
            else if(alphaTap>=1f)alphaTap=1f;
            veloidad*=-1;
        }

        Input input = game.getInput();
        ArrayList<Input.TouchEvent> events = (ArrayList)input.getTouchEvents();
        for(Input.TouchEvent evt: events)
        {
            //Cambiamos al juego
            if(evt.type == Input.TouchEvent.EventType.PRESSED)
                if(closeButton.isPressed(evt.x, evt.y))
                    game.setGameState(new MenuState(game));
                else
                    game.setGameState(new PlayState(game, backGroundNo,lateralColor, barsWidth));
        }

    }

    @Override
    public void render() {

        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        g.clear(lateralColor);

        //1. FONDO
        Rect backRect = new Rect(g.getWidth() / 5,0,3 * g.getWidth() / 5, g.getHeight());
        Rect dstRect=new Rect(backgrounds.getWidth() / 9 * backGroundNo,0,backgrounds.getWidth() / 9,backgrounds.getHeight());
        Sprite backSprite=new Sprite(backgrounds,dstRect,g);
        backSprite.draw(backRect);

        //How to play
        dstRect = new Rect((int)(g.getWidth() / 3.25),g.getHeight()/7 ,
                (int)(g.getWidth() / 2.5),g.getHeight() / 6);
        g.drawImage(howToPlay, dstRect, 1f);

        //Instructions
        dstRect = new Rect(g.getWidth() / 4,(int)(g.getHeight() / 2.75) ,
                g.getWidth() / 2,(int)(g.getHeight()/4));
        g.drawImage(instructions, dstRect, 1f);

        //TapToPlay
        dstRect = new Rect(g.getWidth() / 3,(int)(g.getHeight()/1.5f),
                g.getWidth() / 3,g.getHeight() / 30);
        g.drawImage(tapToPlay, dstRect, alphaTap);

        //Botón de salir
        closeButton.draw();
    }

    private Image howToPlay;
    private Image instructions;
    private Image tapToPlay;
    private Image backgrounds;

    private Image buttons; //1 fila, 10 columnas
    private float alphaTap;
    private float veloidad;
    private int lateralColor;
    private int backGroundNo;
    private int barsWidth;

    Button closeButton;
}

