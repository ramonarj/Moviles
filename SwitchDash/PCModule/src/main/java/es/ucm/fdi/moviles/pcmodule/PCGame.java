package es.ucm.fdi.moviles.pcmodule;

import java.awt.Window;
import java.awt.image.BufferStrategy;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Input;

public class PCGame implements Game {

    //Sistema de input
    PCInput input;
    //Sistema de gráficos
    PCGraphics graphics;
    //Ventana
    Window window;


    //Estado actual
    GameState state;




    @Override
    public void run()
    {
        BufferStrategy strategy = window.getBufferStrategy();
        long lastFrameTime = System.nanoTime();
        while(true)
        {
            //Calcular el deltaTime
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            //Update
            state.update((float)elapsedTime);

            // Pintamos el frame con el BufferStrategy
            do {
                do {
                    java.awt.Graphics g = window.getBufferStrategy().getDrawGraphics();
                    try {
                        state.render();
                    }
                    finally {
                        g.dispose();
                    }
                } while(strategy.contentsRestored());
                strategy.show();
            } while(strategy.contentsLost());
        }
    }



    /**
     * Creaa la instancia recibiendo como parámetro la lógica del juego
     * @param graphics El sistema de gráficos que usará el juego
     * @param input El sistema de entrada que usará el juego
     */
    public PCGame(PCGraphics graphics, PCInput input, Window window)
    {
        this.graphics = graphics;
        this.input = input;
        this.window = window;

        //Establecemos vision vertical como predeterminado
        graphics.setLogicalView();
        graphics.setCanvasSize(window.getWidth() ,window.getHeight());
    }

    /**
     * Devuelve el sistema de gráficos del juego
     * @return
     */
    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    /**
     * Devuelve el sistema de entrada del juego
     * @return
     */
    @Override
    public Input getInput() {
        return input;
    }

    /**
     * Establece la lógica a la que llamará el juego
     * @param state
     */
    @Override
    public void setGameState(GameState state)
    {
        this.state = state;
    }
}
