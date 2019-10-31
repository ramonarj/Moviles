package es.ucm.fdi.moviles.androidModule;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Input;

public class AndroidGame implements Runnable , Game {

    public  AndroidGame(AppCompatActivity app)
    {

    }

    @Override
    public void run() {

    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Input getInput() {
        return null;
    }

    @Override
    public void setGameState(GameState state) {
        state_=state;
    }

    GameState state_;
}
