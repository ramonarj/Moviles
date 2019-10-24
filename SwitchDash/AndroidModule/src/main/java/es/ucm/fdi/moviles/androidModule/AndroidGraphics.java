package es.ucm.fdi.moviles.androidModule;

import es.ucm.fdi.moviles.interfacemodule.Graphics;
import es.ucm.fdi.moviles.interfacemodule.Image;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
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
        AndroidImage image = null;
        try {
            AssetManager assetManager = activity.getAssets();
            inputStream = assetManager.open(path);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            image = new AndroidImage(bitmap);
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

    @Override
    public void clear(int color) {

    }

    @Override
    public void drawImage(Image image) {

    }

    @Override
    public void drawImage(Image image, float alpha) {

    }

    @Override
    public void drawImage(Image image, float alpha, float scaleX, float scaleY) {

    }

    @Override
    public void drawImage(Image image, float alpha, float scaleX, float scaleY, float rectMin, float rectMax) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
