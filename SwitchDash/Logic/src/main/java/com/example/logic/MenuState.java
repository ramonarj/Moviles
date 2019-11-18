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
        Graphics g = game.getGraphics();

        backgrounds = ResourceMan.getImage("Backgrounds");
        logo =        ResourceMan.getImage("Logo");
        tapToPlay =   ResourceMan.getImage("TapToPlay");
        buttons =     ResourceMan.getImage("Buttons");

        barsWidth = g.getWidth() / 5;

        int buttonWidth = buttons.getWidth() / 10;
        int buttonHeight = buttons.getHeight();

        //Botón de instrucciones
        Rect srcRect =new Rect(0,0, buttonWidth,buttonHeight);
        Sprite infoSprite=new Sprite(buttons,srcRect,g);
        instructionsButton = new Button(infoSprite, g.getWidth() - barsWidth / 2, 200, "Instrucciones");

        //Botón de sonido
        srcRect = new Rect(2* buttonWidth,0,  buttonWidth,buttonHeight);
        Sprite soundSprite=new Sprite(buttons,srcRect,g);
        soundButton = new Button(soundSprite, barsWidth / 2,200, "Sonido");


        //Otros
        coloresFlechas = new int[]{ 0xff41a85f, 0xff00a885, 0xff3d8eb9, 0xff2969b0,
                0xff553982, 0xff28324e, 0xfff37934, 0xffd14b41, 0xff75706b };
        backgroundNo = (int)Math.floor(Math.random() * 9);
        alphaTap=1f;
        velocidad =0.6f;
        return true;
    }


    @Override
    public void update(float deltaTime)
    {
        Input input = game.getInput();
        ArrayList<Input.TouchEvent> events = (ArrayList)input.getTouchEvents();
        for(Input.TouchEvent evt: events)
        {
            if(evt.type == Input.TouchEvent.EventType.PRESSED)
            {
                //Botón de instrucciones
                if(instructionsButton.isPressed(evt.x, evt.y))
                    game.setGameState(new InstructionsState(game,backgroundNo,coloresFlechas[backgroundNo]));

            }
            //Detectamos las pulsaciones
            else if(evt.type == Input.TouchEvent.EventType.RELEASED)
            {
                game.setGameState(new PlayState(game));
            }
        }

        alphaTap+=(deltaTime* velocidad);
        if(alphaTap>=1 || alphaTap<=0)
        {
            if(alphaTap<=0f)alphaTap=0;
            else if(alphaTap>=1f)alphaTap=1f;
            velocidad *=-1;
        }

    }

    @Override
    public void render()
    {
        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        g.clear(coloresFlechas[backgroundNo]);

        //1. FONDO
        Rect backRect = new Rect(barsWidth,0,3 * barsWidth, g.getHeight());
        Rect dstRect=new Rect(backgrounds.getWidth() / 9 * backgroundNo,0,backgrounds.getWidth() / 9,backgrounds.getHeight());
        Sprite backSprite=new Sprite(backgrounds,dstRect,g);
        backSprite.draw(backRect);

        //Logo
        dstRect = new Rect(g.getWidth() / 3,356 ,g.getWidth() / 3,g.getHeight() / 6);
        g.drawImage(logo, dstRect,1f );

        //TapToPlay
        dstRect = new Rect(g.getWidth() / 3,g.getHeight()/2 , //950, 1464 (depende de la pantalla)
                g.getWidth() / 3,g.getHeight() / 30);
        g.drawImage(tapToPlay, dstRect, alphaTap);

        //Botón de sonido
        soundButton.draw();

        //Botón de info
        instructionsButton.draw();
    }


    private int backgroundNo;
    private Image backgrounds; //1 fila, 9 columnas
    private Image logo;
    private Image tapToPlay;
    private Image buttons; //1 fila, 10 columnas
    private int[] coloresFlechas;
    private float alphaTap;
    private float velocidad;

    private int barsWidth;

    private Button soundButton;
    private Button instructionsButton;
}
