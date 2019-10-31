package es.ucm.fdi.moviles.androidModule;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.fdi.moviles.interfacemodule.Game;
import es.ucm.fdi.moviles.interfacemodule.GameState;
import es.ucm.fdi.moviles.interfacemodule.Graphics;
import es.ucm.fdi.moviles.interfacemodule.Input;

public class AndroidGame implements Runnable , Game {

    public  AndroidGame(AppCompatActivity Activity , AndroidGraphics Graphics, AndroidInput Input)
    {
        this.activity_=Activity;
        this.graphic_=Graphics;
        this.input_=Input;
    }

    @Override
    public void run() {

    }


    @Override
    public Graphics getGraphics() {
        return graphic_;
    }

    @Override
    public Input getInput() {
        return input_;
    }

    @Override
    public void setGameState(GameState state) {
        state_=state;
    }
    GameState state_;
    AndroidGraphics graphic_;
    AndroidInput input_;
    AppCompatActivity activity_;
    boolean running_=false;
}
