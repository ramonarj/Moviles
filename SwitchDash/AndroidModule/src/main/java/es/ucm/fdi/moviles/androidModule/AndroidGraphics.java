package es.ucm.fdi.moviles.androidModule;

import es.ucm.fdi.moviles.interfacemodule.Graphics;
import es.ucm.fdi.moviles.interfacemodule.Image;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class AndroidGraphics implements Graphics
{
    //Actividad de Android
    AppCompatActivity activity;

    public AndroidGraphics(AppCompatActivity activity)
    {
        this.activity = activity;
    }

    //Laod image from file
    public Image newImage(String path)
    {
        InputStream inputStream = null;
        AndroidImage image = new AndroidImage();
        try {
            AssetManager assetManager = activity.getAssets();
            inputStream = assetManager.open(path);
            image.sprite = BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException io) {
            Log.e("MainActivity", "Error lectura");
        }
        finally {
            try
            {
                inputStream.close();
            } catch (Exception io) {
            }
        }
        return image;
    }
}
