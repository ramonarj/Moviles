
package es.ucm.fdi.moviles.androidModule;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Input;

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
        return (Graphics) graphic_;
    }

    @Override
    public Input getInput() {
        return (Input) input_;
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
