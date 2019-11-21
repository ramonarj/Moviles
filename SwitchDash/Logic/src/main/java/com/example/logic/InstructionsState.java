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

    public InstructionsState(Game game)
    {
        this.game = game;
        this.backGroundNo =GameManager.getInstance().getBackGroundNo();
        this.lateralColor=GameManager.getInstance().getLateralColor();
        this.barsWidth = GameManager.getInstance().getBarsWidth();
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
                    game.setGameState(new PlayState(game));
        }

    }

    @Override
    public void render() {

        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        int width = g.getWidth();
        int height = g.getHeight();
        g.clear(lateralColor);

        //1. FONDO
        Rect destRect = new Rect(width / 5,0,3 * width / 5, height);
        Rect srcRect=new Rect(backgrounds.getWidth() / 9 * backGroundNo,0,backgrounds.getWidth() / 9,backgrounds.getHeight());
        Sprite backSprite=new Sprite(backgrounds,srcRect,g);
        backSprite.draw(destRect);

        //How to play
        destRect = new Rect((int)(width / 3.25),height / 7 ,
                (int)(width / 2.5),height / 6);
        g.drawImage(howToPlay, destRect, 1f);

        //Instructions
        srcRect = new Rect(0,0,instructions.getWidth(), instructions.getHeight());
        Sprite instructionsSprite=new Sprite(instructions,srcRect,g);
        instructionsSprite.drawCentered(width / 2, 950);

        //TapToPlay
        srcRect = new Rect(0,0,tapToPlay.getWidth(), tapToPlay.getHeight());
        Sprite tapSprite=new Sprite(tapToPlay,srcRect,g);
        tapSprite.drawCentered(width / 2, 1400, 1, alphaTap);

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

