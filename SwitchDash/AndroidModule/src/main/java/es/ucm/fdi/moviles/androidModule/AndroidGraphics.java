package es.ucm.fdi.moviles.androidModule;

import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.util.Log;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class AndroidGraphics implements Graphics
{
    //Actividad de Android
    AppCompatActivity activity;
    SurfaceView surfaceview_;

    public AndroidGraphics(AppCompatActivity activity, SurfaceView surfaceview_)
    {
        this.activity = activity;
        this.surfaceview_=surfaceview_;
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
        //Bloqueamos el canavas para limpiar la pantalla e inmediatamente
        //despues lo liberamos
        Canvas canvas=surfaceview_.getHolder().lockCanvas();
        canvas.drawColor(color);
        surfaceview_.getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void drawImage(Image image,int posX, int posY) {
        //Bloqueamos el canavas para pintar la imagen e inmediatamente
        //despues lo liberamos
        Canvas canvas=surfaceview_.getHolder().lockCanvas();
        if(image!=null) {
            AndroidImage im=(AndroidImage)image;
            canvas.drawBitmap(im.sprite,posX,posY,null);
        }
        surfaceview_.getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha) {
        //Bloqueamos el canavas para pintar la imagen e inmediatamente
        //despues lo liberamos,creamos un Paint local el cual usaremos
        //para modificar el alpha de la imagen
        Canvas canvas=surfaceview_.getHolder().lockCanvas();
        if(image!=null) {
            AndroidImage im=(AndroidImage)image;
            Paint paint=new Paint();
            paint.setAlpha((int)alpha*10);
            canvas.drawBitmap(im.sprite,posX,posY,paint);
        }
        surfaceview_.getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY) {

    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY, int rectMin, int rectMax) {

    }



    @Override
    public int getWidth() {
        Canvas canvas=surfaceview_.getHolder().lockCanvas();
        int width=canvas.getWidth();
        surfaceview_.getHolder().unlockCanvasAndPost(canvas);
        return width;

    }

    @Override
    public int getHeight() {
        Canvas canvas=surfaceview_.getHolder().lockCanvas();
        int height=canvas.getHeight();
        surfaceview_.getHolder().unlockCanvasAndPost(canvas);
        return height;
    }
}
