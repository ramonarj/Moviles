package es.ucm.fdi.moviles.SwitchDash;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logic.LoadState;

import es.ucm.fdi.moviles.androidModule.AndroidGraphics;
import es.ucm.fdi.moviles.androidModule.AndroidInput;
import es.ucm.fdi.moviles.androidModule.AndroidGame;
import es.ucm.fdi.moviles.engine.ResourceMan;


public class MainActivity extends AppCompatActivity {

    //Callback para cuando se crea
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Para la pantalla completa y que no se vea el logo ni los botones de abajo
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
       // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        //Constructora padre
        super.onCreate(savedInstanceState);

        //Creamos el juego y estado actual
        game=new AndroidGame(this);
        LoadState loadState=new LoadState(game);
        init();

        //Inicializar Resouce Manager
        ResourceMan.initInstance(game);

        //Cargar el estado inicial
        game.setGameState(loadState);

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

    /**
     * Llama tanto al resume de la superclase como al
     * resume del game
     */
    @Override
    protected void onResume() {

        super.onResume();
        game.onResume();
    }

    /**
     * LLama al Pause de la superclase y al pause del Game
     */
    @Override
    protected void onPause() {

        // Avisamos a la vista (que es la encargada del active render)
        // de lo que está pasando.
        super.onPause();
        game.pause();

    }

    /**
     * Estlece tanto el tamaño logico de la pantalla como el
     * tamaño fisico del dispositivo
     */
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
