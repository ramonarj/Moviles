package es.ucm.fdi.moviles.SwitchDash;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logic.SwitchDashLogic;

import es.ucm.fdi.moviles.androidModule.AndroidGraphics;
import es.ucm.fdi.moviles.androidModule.AndroidInput;
import es.ucm.fdi.moviles.androidModule.AndroidGame;



public class MainActivity extends AppCompatActivity {

    //Callback para cuando se crea
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game=new AndroidGame(this);
        logic=new SwitchDashLogic(game);
        game.setGameState(logic);
        setContentView(game);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            logic.setWidth(1920);
            logic.setHeight(1080);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            logic.setWidth(1080);
            logic.setHeight(620);
        }
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

    }


    private AndroidGame game;
    private AndroidInput input;
    private AndroidGraphics graphics;
    private SwitchDashLogic logic;
    private SurfaceView surf;
}
