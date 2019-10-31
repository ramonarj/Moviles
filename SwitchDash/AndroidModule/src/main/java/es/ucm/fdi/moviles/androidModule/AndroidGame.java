package es.ucm.fdi.moviles.androidModule;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.fdi.moviles.interfacemodule.Game;
import es.ucm.fdi.moviles.interfacemodule.GameState;
import es.ucm.fdi.moviles.interfacemodule.Graphics;
import es.ucm.fdi.moviles.interfacemodule.Input;

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
