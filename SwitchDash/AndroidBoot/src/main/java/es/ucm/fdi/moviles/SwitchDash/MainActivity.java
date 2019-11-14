package es.ucm.fdi.moviles.SwitchDash;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logic.LoadState;
import com.example.logic.PlayState;

import es.ucm.fdi.moviles.androidModule.AndroidGraphics;
import es.ucm.fdi.moviles.androidModule.AndroidInput;
import es.ucm.fdi.moviles.androidModule.AndroidGame;
import es.ucm.fdi.moviles.engine.ResourceMan;


public class MainActivity extends AppCompatActivity {

    //Callback para cuando se crea
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game=new AndroidGame(this);
        LoadState logic=new LoadState(game);

        //Inicializar Resouce Manager
        ResourceMan.initInstance(game);

        game.setGameState(logic);
        init();
        setContentView(game);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Point size=new Point();
        this.getWindowManager().getDefaultDisplay().getSize(size);
        game.getGraphics().setCanvasSize(size.x ,size.y);
        // Checks the orientation of the screen
        /*
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            //Guardamos las coordenadas fisicas de la ventana

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Point size=new Point();
            //Guardamos las coordenadas fisicas de la ventana
            this.getWindowManager().getDefaultDisplay().getSize(size);
            game.getGraphics().setCanvasSize(size.x ,size.y);
        }
        */

    }
    @Override
    protected void onResume() {

        super.onResume();
        game.onResume();
    }

    @Override
    protected void onPause() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onPause();
        game.pause();

    }

    protected void init()
    {
        //Establecemos vision vertical como predeterminado
        game.getGraphics().setLogicalView();
        Point size=new Point();
        this.getWindowManager().getDefaultDisplay().getSize(size);
        game.getGraphics().setCanvasSize(size.x ,size.y);
    }


    private AndroidGame game;
    private AndroidInput input;
    private AndroidGraphics graphics;
    private SurfaceView surf;
}
