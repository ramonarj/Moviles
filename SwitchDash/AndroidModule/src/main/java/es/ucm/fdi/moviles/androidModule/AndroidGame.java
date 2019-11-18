
package es.ucm.fdi.moviles.androidModule;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Input;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class AndroidGame extends SurfaceView implements Runnable , Game {


    public  AndroidGame(AppCompatActivity Activity)
    {
        super(Activity);
        this.activity_=Activity;
        this.graphic_=new AndroidGraphics(activity_,this);
        this.input_=new AndroidInput();
        this.canvas=null;
        setOnTouchListener((AndroidInput)(this.input_));
    }

    private void init()
    {

    }

    @Override
    public void run() {
        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        init(); //Prueba para cargar una unica imagen
        while (running_) {
            //render

            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            float elapsedTime = (float)(nanoElapsedTime / 1.0E9);
            state_.update(elapsedTime);
            lockCanvas();
            ((AndroidGraphics)graphic_).setCanvas(this.canvas);
            state_.render();
            unLockCanvas();


            //Contador de Frames
            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;
        }
    }
    public void pause() {

        if (running_) {
            running_ = false;
            while (true) {
                try {
                    gameThread.join();
                    gameThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            } // while(true)
        } // if (_running)

    } // pause

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
        state_.init();
    }

    public void onResume()
    {
        if (!running_) {
            running_ = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    private void lockCanvas()
    {
        while(!this.getHolder().getSurface().isValid())
            ;
        canvas = this.getHolder().lockCanvas();
    }
    private void unLockCanvas()
    {
        this.getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void GameOver(){
        running_=false;
    }

    private GameState state_;
    private AndroidGraphics graphic_;
    private AndroidInput input_;
    private AppCompatActivity activity_;
    boolean running_=false;
    Thread gameThread;
    private Canvas canvas;


}
