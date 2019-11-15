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
        //Carga del graphics
        g=game.getGraphics();

        //Carga de recursos
        backgrounds = ResourceMan.getImage("Backgrounds");
        balls = ResourceMan.getImage("Balls");
        flechas = ResourceMan.getImage("Flechas");
        player = ResourceMan.getImage("Players");
        buttons = ResourceMan.getImage("Buttons");
        white = game.getGraphics().newImage("white.png");
        scoreFont = game.getGraphics().newImage("scoreFont.png");

        //Inicialización de las variables
        backgroundNo = (int)Math.floor(Math.random() * 9);
        playerColor = 0;
        coloresFlechas = new int[]{ 0xff41a85f, 0xff00a885, 0xff3d8eb9, 0xff2969b0,
                0xff553982, 0xff28324e, 0xfff37934, 0xffd14b41, 0xff75706b };

        posFlechas1 = game.getGraphics().getHeight()-flechas.getHeight();
        posflechas2 = posFlechas1-flechas.getHeight();


        //Inicializamos las bolas
        contBolas=0;
        numBolas=6;
        ballColor=new int[numBolas];
        posBolas=new int[numBolas];
        for(int i=0;i<numBolas;i++) {
            posBolas[i] = -(i*395);
            ballColor[i]=balls.getHeight()/2*(int)Math.floor(Math.random() * 2);
        }
        velBolas=430;

        //Inicalizamos las variables de la puntiacion
        score = 0;
        NumScores=1;
        division=10;
        return  true;
    }

    private void checkInput()
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
    }

    private void addBallsVelocity()
    {
        if(contBolas==10) {
            velBolas += 90;
            contBolas=0;
        }
    }

    private void checkImpact()
    {

    }
    @Override
    public void update(float deltaTime)
    {

        checkInput();

        addBallsVelocity();

        //TODO: usar el deltaTime
        posFlechas1 += (deltaTime*384);
        posflechas2 += (deltaTime*384);

        //Recorremos cada una de las pelotas y comprobamos si al estar en contacto con la barra
        //han podido ser atrapdas o en caso contrario , ha finalizado la partida
        for(int i=0;i<numBolas;i++) {
            posBolas[i] += (deltaTime * velBolas);
            if(checkPosition(posBolas[i]))
            {
                if(match(ballColor[i])) {
                    posBolas[i] = getMenor() - 395;
                    ballColor[i] = balls.getHeight() / 2 ;
                    contBolas++;
                    score++;
                    if(score%division==0)
                    {
                        NumScores++;
                        division*=10;
                    }
                } else game.GameOver();
            }
        }


        //TODO: registrar los choques entre jugador y bolas y aumentar la puntuación/acabar el juego
    }


    @Override
    public void render()
    {
        g.clear(coloresFlechas[backgroundNo]);

        //Fondo
        drawBackground();

        //Flechas
        drawArrows();

        //Pelota
        drawBalls();

        //Jugador
        drawPlayer();

        //Puntuación
        drawScore();
    }

    /**
     * Pinta del color aleatorio escogido anteriormente el fondo del juego
     */
    private void drawBackground() {
        Rect backRect = new Rect(g.getWidth() / 5,0,3 * g.getWidth() / 5, g.getHeight());
        Rect dstRect=new Rect(backgrounds.getWidth() / 9 * backgroundNo,0,backgrounds.getWidth() / 9,backgrounds.getHeight());
        Sprite backSprite=new Sprite(backgrounds,dstRect,g);
        backSprite.draw(backRect);
    }

    /**
     * Pinta la barra de blanco o negro dependiendo del input del usuario
     */
    private void drawPlayer() {
        Rect srcRect=new Rect(0,playerColor * player.getHeight() / 2,player.getWidth(),player.getHeight() / 2);
        Sprite playerSprite=new Sprite(player,srcRect,g);
        playerSprite.drawCentered(g.getWidth() / 2,1200);
    }

    /**
     * Pinta un numero maximo de bolas escogidas con la intencion de
     * que sean reutilizadas hasta el final de la misma
     */
    private void drawBalls() {
        for(int i=0;i<numBolas;i++)
        {
            Rect srcRect=new Rect(balls.getWidth() / 10 * 7,ballColor[i],balls.getWidth() / 10,balls.getHeight() / 2);
            Sprite ballSprite=new Sprite(balls,srcRect,g);
            ballSprite.drawCentered(g.getWidth() / 2,posBolas[i]);
        }
    }

    /**
     * Pinta dos imagenes de flechas completas haciendo que una se ponga
     * encima de la otra hasta el final de la partida
     */
    private void drawArrows() {
        Rect dstRect = new Rect(g.getWidth() / 5,posFlechas1,
                3 * g.getWidth() / 5, flechas.getHeight());
        g.drawImage(flechas, dstRect, 100f);


        Rect dstRect2 = new Rect(g.getWidth() / 5,posflechas2 ,
                3 * g.getWidth() / 5, flechas.getHeight());
        g.drawImage(flechas, dstRect2, 100f);


       /*
        if(posFlechas1>game.getGraphics().getHeight())
            posFlechas1=posflechas2-flechas2.getHeight();
        /*
        else if(posflechas2>game.getGraphics().getHeight())
            posflechas2=posFlechas1-flechas1.getHeight();
            */

    }

    /**
     * Escoge la bola que este mas alta dentro de la pantalla
     * @return
     */
    private int getMenor()
    {
        int value=game.getGraphics().getHeight();
        for(int i=0;i<numBolas;i++)
            if(posBolas[i]<value)value=posBolas[i];
        return value;
    }

    /**
     *
     * @param posBola posicion de la bola i-esima
     * @return true si la bola toca con la barra del juego, false en caso contrario
     */
    private boolean checkPosition(int posBola) {
        return (posBola+balls.getHeight()/4>1200-player.getHeight()/4 && posBola<1200+player.getHeight()/4);
    }

    /**
     *
     * @param ballcolor color de la bola i-esima
     * @return true si el color de la bola es igual al de la barra, false en caso contraio
     */
    private boolean match(int ballcolor) {
        return (ballcolor==0 && playerColor==0) || (ballcolor==balls.getHeight()/2 && playerColor==1);
    }

    /**
     * Pinta la puntacion actucal calculando tanto la posicion deonde pintar cada uno
     * de los digitos como el numero que pintare en cada uno de ellos
     */
    private void drawScore()
    {
        for(int i=0;i<NumScores;i++) {

            int numeroApintar;
            if(i==0)numeroApintar=score%10;
            else numeroApintar=(score/(int)(Math.pow(10,i))%10);

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
            Sprite scoreSprites = new Sprite(scoreFont, srcRect, g);
            scoreSprites.drawCentered(g.getWidth() - 50 -(i*75), 200);
        }
    }

    //IMAGENES Y SUS DIMENSIONES:
    //Fondo
    private Image backgrounds; //1 fila, 9 columnas
    private Image white;

    //Objetos
    private Image balls; //2 filas, 10 columnas
    private Image flechas;
    private Image player; //2 filas, 1 columna

    //Textos
    private Image buttons; //1 fila, 10 columnas

    //Fuente
    private Image scoreFont; //6 filas, 15 columnas

    //VARIABLES DE JUEGO:
    private int playerColor; //0 = blanco, 1 = negro
    private int score;
    private int backgroundNo;
    private int posFlechas1;
    private int posflechas2;
    private int [] coloresFlechas;
    private int contBolas;
    private int velBolas;
    private int posBolas[];
    private int numBolas;
    private int ballColor[];
    private int NumScores;
    private int division;
    private Graphics g;
}
