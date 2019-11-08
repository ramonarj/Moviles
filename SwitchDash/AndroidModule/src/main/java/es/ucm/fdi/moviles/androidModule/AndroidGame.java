
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
        while (true) {
        state_.render();
        }
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

    public void pause() {

        if (running_) {
            running_= false;
            while (true) {
                try {
                    gameThread.join();
                    gameThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            }
         }
    }

    public void onResume()
    {
        if (!running_) {
            running_ = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    private GameState state_;
    private AndroidGraphics graphic_;
    private AndroidInput input_;
    private AppCompatActivity activity_;
    boolean running_=false;
    Thread gameThread;
}
