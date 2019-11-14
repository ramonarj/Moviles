package com.example.logic;

import java.util.ArrayList;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Input;
import es.ucm.fdi.moviles.engine.Rect;
import es.ucm.fdi.moviles.engine.Sprite;

public class PlayState implements GameState
{
    //Objeto del juego
    private Game game;

    public PlayState(Game game)
    {
        this.game = game;
    }

    @Override
    public boolean init()
    {
        //Carga de recursos
        backgrounds =  game.getGraphics().newImage("backgrounds.png");
        balls =  game.getGraphics().newImage("balls.png");
        flechas =  game.getGraphics().newImage("arrowsBackground.png");
        player =  game.getGraphics().newImage("players.png");
        logo = game.getGraphics().newImage("switchDashLogo.png");
        tapToPlay = game.getGraphics().newImage("tapToPlay.png");
        buttons = game.getGraphics().newImage("buttons.png");
        howToPlay = game.getGraphics().newImage("howToPlay.png");
        instructions = game.getGraphics().newImage("instructions.png");
        gameOver = game.getGraphics().newImage("gameOver.png");
        playAgain = game.getGraphics().newImage("playAgain.png");
        white = game.getGraphics().newImage("white.png");
        scoreFont = game.getGraphics().newImage("scoreFont.png");

        //Inicialización de las variables
        backgroundNo = (int)Math.floor(Math.random() * 9);
        playerColor = 0;
        score = 0;
        coloresFlechas = new int[]{ 0x41a85f, 0x00a885, 0x3d8eb9, 0x2969b0,
                0x553982, 0x28324e, 0xf37934, 0xd14b41, 0x75706b };
        posFlechas = game.getGraphics().getLogicalHeight() / 2;
        posBolas = game.getGraphics().getLogicalHeight() / 5;
        return true;
    }

    @Override
    public void update(float deltaTime)
    {
        Input input = game.getInput();
        ArrayList<Input.TouchEvent> events = (ArrayList)input.getTouchEvents();
        for(Input.TouchEvent evt: events)
        {
            //Cambiamos de color
            if(evt.type == Input.TouchEvent.EventType.PRESSED)
                playerColor = 1 - playerColor;

            //TODO: registrar clicks en los botones de las esquinas
        }

        //TODO: usar el deltaTime
        posFlechas +=10;
        posBolas+=12;

        //TODO: registrar los choques entre jugador y bolas y aumentar la puntuación/acabar el juego
    }

    @Override
    public void render()
    {
        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        g.clear(coloresFlechas[backgroundNo]);

        //1. FONDO
        Rect backRect = new Rect(g.getLogicalWidth() / 5,0,3 * g.getLogicalWidth() / 5, g.getLogicalHeight());
        Rect dstRect=new Rect(backgrounds.getWidth() / 9 * backgroundNo,0,backgrounds.getWidth() / 9,backgrounds.getHeight());
        Sprite backSprite=new Sprite(backgrounds,dstRect,g);
        backSprite.draw(backRect);

        //2. FLECHAS (hay 2 sprites)
        //dstRect = new Rect(g.getLogicalWidth() / 5,posFlechas,
               // 3 * g.getLogicalWidth() / 5, flechas.getHeight());
        //g.drawImage(flechas, dstRect, 0.25f);
        dstRect = new Rect(g.getLogicalWidth() / 5,posFlechas - flechas.getHeight(),
                3 * g.getLogicalWidth() / 5, flechas.getHeight());
        g.drawImage(flechas, dstRect, 0.25f);


        //3. PELOTA
        Rect srcRect=new Rect(balls.getWidth() / 10 * 7,0,balls.getWidth() / 10,balls.getHeight() / 2);
        Sprite ballSprite=new Sprite(balls,srcRect,g);
        ballSprite.drawCentered(g.getLogicalWidth() / 2,posBolas);


        //4. JUGADOR
        srcRect=new Rect(0,playerColor * player.getHeight() / 2,player.getWidth(),player.getHeight() / 2);
        Sprite playerSprite=new Sprite(player,srcRect,g);
        playerSprite.drawCentered(g.getLogicalWidth() / 2,1200);


        //5. GUI
        //Logo
        dstRect = new Rect(g.getLogicalWidth() / 3,356 ,g.getLogicalWidth() / 3,g.getLogicalHeight() / 6);
        g.drawImage(logo, dstRect, 1f);

        //TapToPlay
        //TODO: hacer que "parpadee"
        dstRect = new Rect(g.getLogicalWidth() / 3,950 , //950, 1464 (depende de la pantalla)
                g.getLogicalWidth() / 3,g.getLogicalHeight() / 30);
        g.drawImage(tapToPlay, dstRect, 1f);

        //Botón de sonido
        srcRect=new Rect(2 * buttons.getWidth() / 10,0, buttons.getWidth() / 10,buttons.getHeight());
        Sprite soundSprite=new Sprite(buttons,srcRect,g);
        soundSprite.drawCentered(50, 200);

        //Botón de info
        srcRect=new Rect(0,0, buttons.getWidth() / 10,buttons.getHeight());
        Sprite infoSprite=new Sprite(buttons,srcRect,g);
        infoSprite.drawCentered(g.getLogicalWidth() - 50,200);

        //Puntuación
        srcRect=new Rect(0,0, scoreFont.getWidth() / 15,scoreFont.getHeight() / 6);
        Sprite scoreSprites=new Sprite(scoreFont,srcRect,g);
        scoreSprites.drawCentered(g.getLogicalWidth() - 50,400);

        //How to play
        dstRect = new Rect(g.getLogicalWidth() / 3,290 ,
                g.getLogicalWidth() / 3,g.getLogicalHeight() / 7);
        //g.drawImage(howToPlay, dstRect, 1f);

        //Instructions
        dstRect = new Rect(g.getLogicalWidth() / 4,768 ,
                g.getLogicalWidth() / 2, g.getLogicalHeight() / 3);
        //g.drawImage(instructions, dstRect, 1f);

        //Game Over
        dstRect = new Rect(g.getLogicalWidth() / 3,364 ,
                g.getLogicalWidth() / 4,g.getLogicalHeight() / 8);
        //g.drawImage(gameOver, dstRect, 1f);

        //Play again
        dstRect = new Rect(g.getLogicalWidth() / 3,1396 , //950, 1464
                g.getLogicalWidth() / 3,g.getLogicalHeight() / 30);
        //g.drawImage(playAgain, dstRect, 1f);
    }

    //IMÁGENES Y SUS DIMENSIONES:
    //Fondo
    private Image backgrounds; //1 fila, 9 columnas
    private Image white;

    //Objetos
    private Image balls; //2 filas, 10 columnas
    private Image flechas;
    private Image player; //2 filas, 1 columna
    private Image logo;

    //Textos
    private Image tapToPlay;
    private Image buttons; //1 fila, 10 columnas
    private Image howToPlay;
    private Image instructions;
    private Image gameOver;
    private Image playAgain;

    //Fuente
    private Image scoreFont; //6 filas, 15 columnas



    //VARIABLES DE JUEGO:
    private int playerColor; //0 = blanco, 1 = negro
    private int score;
    private int backgroundNo;
    private int posFlechas;
    private int posBolas;
    private int [] coloresFlechas;
}