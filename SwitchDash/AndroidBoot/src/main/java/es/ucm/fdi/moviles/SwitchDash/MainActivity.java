package es.ucm.fdi.moviles.SwitchDash;

import android.os.Bundle;
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
        surf=new SurfaceView(this);
        setContentView(new SurfaceView(this));
        init();
    }

    protected void onResume() {

        super.onResume();
        game.onResume();
    }

    protected void init()
    {
        input=new AndroidInput();
        graphics=new AndroidGraphics(this,surf);
        game=new AndroidGame(this,graphics,input);
        logic=new SwitchDashLogic(game);
        game.setGameState(logic);
    }

    private AndroidGame game;
    private AndroidInput input;
    private AndroidGraphics graphics;
    private SwitchDashLogic logic;
    private SurfaceView surf;
}
