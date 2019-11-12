
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
        setOnTouchListener(this.input_);
    }

    private void init()
    {
        chargeImage();
    }

    @Override
    public void run() {
        init(); //Prueba para cargar una unica imagen
        while (running_) {
            //render
            state_.render();
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
    }

    public void onResume()
    {
        if (!running_) {
            running_ = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    private void chargeImage()
    {
        InputStream inputStream = null;
        AssetManager assetManager = activity_.getAssets();
        AndroidImage image=null;
        try {
            inputStream = assetManager.open("Chestplate.jpg");
            image = new AndroidImage(BitmapFactory.decodeStream(inputStream));
        }
        catch (IOException e) {
            android.util.Log.e("MainActivity", "Error leyendo el sprite");
        }
        state_.setImage(image);
    }

    private GameState state_;
    private AndroidGraphics graphic_;
    private AndroidInput input_;
    private AppCompatActivity activity_;
    boolean running_=false;
    Thread gameThread;
}
