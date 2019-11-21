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

public class GameOverState implements GameState {


    //Objeto del juego
    private Game game;

    public GameOverState(Game game,int score,int numDigitos)
    {
        this.game = game;
        this.score=score;
        this.NumScores=numDigitos;
        this.backGroundNo =GameManager.getInstance().getBackGroundNo();
        this.lateralColor=GameManager.getInstance().getLateralColor();
        this.barsWidth = GameManager.getInstance().getBarsWidth();
    }

    @Override
    public boolean init() {
        Graphics g = game.getGraphics();

        buttons = ResourceMan.getImage("Buttons");
        gameOver = ResourceMan.getImage("GameOver");
        playAgain = ResourceMan.getImage("PlayAgain");
        backgrounds = ResourceMan.getImage("Backgrounds");
        scoreFont = ResourceMan.getImage("ScoreFont");

        barsWidth = g.getWidth() / 5;

        int buttonWidth = buttons.getWidth() / 10;
        int buttonHeight = buttons.getHeight();

        //Botón de sonido
        Rect srcRect = new Rect(2* buttonWidth,0,  buttonWidth,buttonHeight);
        Sprite soundSprite=new Sprite(buttons,srcRect,g);
        soundButton = new Button(soundSprite, barsWidth / 2,200, "Sonido");

        //Botón de instrucciones
        srcRect =new Rect(0,0, buttonWidth,buttonHeight);
        Sprite infoSprite=new Sprite(buttons,srcRect,g);
        instructionsButton = new Button(infoSprite, g.getWidth() - barsWidth / 2, 200, "Instrucciones");

        alphaTap=1f;
        veloidad=0.6f;
        return true;
    }

    @Override
    public void update(float deltaTime) {
        Input input = game.getInput();
        ArrayList<Input.TouchEvent> events = (ArrayList)input.getTouchEvents();
        for(Input.TouchEvent evt: events)
        {
            //Cambiamos al juego
            if(evt.type == Input.TouchEvent.EventType.PRESSED)
                if(instructionsButton.isPressed(evt.x, evt.y))
                    game.setGameState(new InstructionsState(game));
                else
                    game.setGameState(new PlayState(game));
    }

        alphaTap+=(deltaTime*veloidad);
        if(alphaTap>=1 || alphaTap<=0)
        {
            if(alphaTap<=0f)alphaTap=0;
            else if(alphaTap>=1f)alphaTap=1f;
            veloidad*=-1;
        }
    }

    @Override
    public void render()
    {
        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        g.clear(lateralColor);

        Rect backRect = new Rect(g.getWidth() / 5,0,3 * g.getWidth() / 5, g.getHeight());
        Rect dstRect=new Rect(backgrounds.getWidth() / 9 * backGroundNo,0,backgrounds.getWidth() / 9,backgrounds.getHeight());
        Sprite backSprite=new Sprite(backgrounds,dstRect,g);
        backSprite.draw(backRect);

        //Game Over
        dstRect = new Rect((int)(g.getWidth() / 2.75),(int)(g.getHeight()/5) ,
                g.getWidth() / 4,g.getHeight() / 8);
        g.drawImage(gameOver, dstRect, 1f);

        //Play again
        dstRect = new Rect(g.getWidth() / 3,(int)(g.getHeight()/1.5) , //950, 1464
                g.getWidth() / 3,g.getHeight() / 30);
        g.drawImage(playAgain, dstRect, alphaTap);

        //Botón de sonido
        soundButton.draw();

        //Botón de info
        instructionsButton.draw();

        drawScore();

        drawText();

    }

    private void drawScore()
    {
        int auxScore = score;
        int gap = (int)((float)(scoreFont.getHeight() / 15) * 1.5f);
        int initialX = game.getGraphics().getWidth() - 3 * barsWidth / 2;
        int initialY = 1035 - (int)((float)(scoreFont.getHeight() / 7) * 1.5f);

        //Pintamos una vez por cada dígito (de derecha a izquierda)
        for(int i=0;i<NumScores;i++)
        {
            //Número que queremos pintar
            int numeroApintar = auxScore % 10;
            auxScore /= 10;

            //Posición en la spritesheet
            int posicion=7+numeroApintar;
            int posicionY=3;
            if(posicion>14 && posicion<=16)
            {
                posicion-=15;
                posicionY=4;
            }
            else if(posicion>1 && posicionY==4)
            {
                posicion=7;
                posicionY=3;
            }

            Rect srcRect = new Rect(posicion * scoreFont.getWidth() / 15, posicionY * scoreFont.getHeight() / 7, scoreFont.getWidth() / 15, scoreFont.getHeight() / 7);
            Sprite scoreSprites = new Sprite(scoreFont, srcRect, game.getGraphics());

            scoreSprites.drawCentered(initialX - gap * i, initialY, 1.5f);
        }
    }
    private void drawText()
    {
        //P
        Rect srcRect = new Rect(11 * scoreFont.getWidth() / 15, 2 * scoreFont.getHeight() / 7, scoreFont.getWidth() / 15, scoreFont.getHeight() / 7);
        Sprite scoreSprites = new Sprite(scoreFont, srcRect, game.getGraphics());
        scoreSprites.drawCentered((game.getGraphics().getWidth()/4)+75,1035);

        //O
        srcRect = new Rect(10 * scoreFont.getWidth() / 15, 2 * scoreFont.getHeight() / 7, scoreFont.getWidth() / 15, scoreFont.getHeight() / 7);
        scoreSprites = new Sprite(scoreFont, srcRect, game.getGraphics());
        scoreSprites.drawCentered((game.getGraphics().getWidth()/4)+150,1035);

        //I
        srcRect = new Rect(4 * scoreFont.getWidth() / 15, 2 * scoreFont.getHeight() / 7, scoreFont.getWidth() / 15, scoreFont.getHeight() / 7);
        scoreSprites = new Sprite(scoreFont, srcRect, game.getGraphics());
        scoreSprites.drawCentered((game.getGraphics().getWidth()/4)+225,1035);

        //N
        srcRect = new Rect(9 * scoreFont.getWidth() / 15, 2 * scoreFont.getHeight() / 7, scoreFont.getWidth() / 15, scoreFont.getHeight() / 7);
        scoreSprites = new Sprite(scoreFont, srcRect, game.getGraphics());
        scoreSprites.drawCentered((game.getGraphics().getWidth()/4)+300,1035);

        //T
        srcRect = new Rect(4 * scoreFont.getWidth() / 15, 1 * scoreFont.getHeight() / 7, scoreFont.getWidth() / 15, scoreFont.getHeight() / 7);
        scoreSprites = new Sprite(scoreFont, srcRect, game.getGraphics());
        scoreSprites.drawCentered((game.getGraphics().getWidth()/4)+375,1035);

        //S
        srcRect = new Rect(14 * scoreFont.getWidth() / 15, 2 * scoreFont.getHeight() / 7, scoreFont.getWidth() / 15, scoreFont.getHeight() / 7);
        scoreSprites = new Sprite(scoreFont, srcRect, game.getGraphics());
        scoreSprites.drawCentered((game.getGraphics().getWidth()/4)+450,1035);
    }



    private Image gameOver;
    private Image playAgain;
    private int backGroundNo;
    private int lateralColor;
    private Image backgrounds;
    private Image buttons;
    private float alphaTap;
    private float veloidad;
    private int score;
    private int NumScores;
    private Image scoreFont;
    private int barsWidth;

    private Button soundButton;
    private Button instructionsButton;
}
