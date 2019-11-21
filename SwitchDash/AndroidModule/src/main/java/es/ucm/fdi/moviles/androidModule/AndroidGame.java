
package es.ucm.fdi.moviles.androidModule;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.os.Build;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Input;


public class AndroidGame extends SurfaceView implements Runnable , Game {


    public  AndroidGame(AppCompatActivity Activity)
    {
        super(Activity);
        this.activity_=Activity;
        this.graphic_=new AndroidGraphics(activity_,this);
        this.input_=new AndroidInput();

        setOnTouchListener(this.input_);
        this.input_.init(this);
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

            //Bloqueamos el canvas, se lo pasamos al graphics y pintamos antes de desbloquearlo
            Canvas canvas = lockCanvas();
            graphic_.setCanvas(canvas);
            state_.render();
            unLockCanvas(canvas);

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
        return graphic_;
    }

    @Override
    public Input getInput() {
        return input_;
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

    /**
     * Bloquea el canvas para poder usarlo para renderizar
     */
    private Canvas lockCanvas()
    {
        while(!getHolder().getSurface().isValid());

        //Ejecución condicional (solo usamos un canvas acelerado si estamos en un API >=23,
        //que es el que lo soporta
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return getHolder().getSurface().lockHardwareCanvas();
        else
            return getHolder().lockCanvas();
    }


    private void unLockCanvas(Canvas canvas)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            getHolder().getSurface().unlockCanvasAndPost(canvas);
        else
            getHolder().unlockCanvasAndPost(canvas);
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
}
