package es.ucm.fdi.moviles.SwitchDash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;

import es.ucm.fdi.moviles.androidModule.AndroidGraphics;
import es.ucm.fdi.moviles.androidModule.AndroidInput;

public class MainActivity extends AppCompatActivity {

    //Callback para cuando se crea
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void init()
    {
        AndroidInput input=new AndroidInput();
        AndroidGraphics graphics=new AndroidGraphics(this,new SurfaceView(null));
        

    }
}
