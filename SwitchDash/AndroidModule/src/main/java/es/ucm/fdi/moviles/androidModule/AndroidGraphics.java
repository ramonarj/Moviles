package es.ucm.fdi.moviles.androidModule;

import es.ucm.fdi.moviles.engine.AbstractGraphics;
import es.ucm.fdi.moviles.engine.Image;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class AndroidGraphics extends AbstractGraphics
{
    //Actividad de Android
    AppCompatActivity activity;
    SurfaceView surfaceview_;
    Canvas canvas;

    public AndroidGraphics(AppCompatActivity activity, SurfaceView surfaceview)
    {
        this.activity = activity;
        this.surfaceview_=surfaceview;
        canvas=null;
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
        lockCanvas();
        canvas.drawColor(color);
        unLockCanvas();
    }

    @Override
    public void setCanvasSize(int x, int y) {

    }

    @Override
    public void drawImage(Image image, int posX, int posY) {
        super.drawImage(image, posX, posY);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, es.ucm.fdi.moviles.engine.Rect srcRect) {

    }

    @Override
    public void drawImage(Image image, int posX, int posY, float alpha) {

    }

    @Override
    public void drawImage(Image image, es.ucm.fdi.moviles.engine.Rect destRect, float alpha) {

    }

    @Override
    public void drawImage(Image image, es.ucm.fdi.moviles.engine.Rect srcRect, es.ucm.fdi.moviles.engine.Rect destRect, float alpha) {

    }


    @Override
    public void drawRealImage(Image image,int posX, int posY) {
        //Bloqueamos el canavas para pintar la imagen e inmediatamente
        //despues lo liberamos
        lockCanvas();
        if(image!=null) {
            AndroidImage im=(AndroidImage)image;
            canvas.drawBitmap(((AndroidImage) image).sprite,posX,posY,null);
        }
        unLockCanvas();
    }

    @Override
    public void drawRealImage(Image image, int posX, int posY, float alpha) {
        //Bloqueamos el canavas para pintar la imagen e inmediatamente
        //despues lo liberamos,creamos un Paint local el cual usaremos
        //para modificar el alpha de la imagen
      lockCanvas();
        if(image!=null) {
            AndroidImage im=(AndroidImage)image;
            Paint paint=new Paint();
            paint.setAlpha((int)alpha);
            canvas.drawBitmap(im.sprite,posX,posY,paint);
        }
      unLockCanvas();
    }

    @Override
    public void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY) {
        //Tamaño del rectángulo que se va a pintar (en píxeles)
        int tamX = (int)((float)image.getWidth() * scaleX);
        int tamY = (int)((float)image.getHeight() * scaleY);

        Paint paint=new Paint();
        paint.setAlpha((int)alpha);

        Rect src=new Rect(0,0,image.getWidth()-1,image.getHeight()-1);
        Rect dest=new Rect(0,0,tamX-1,tamY-1);

        //Pintamos
        lockCanvas();
        canvas.drawBitmap(((AndroidImage) image).sprite,src,dest,paint);
        unLockCanvas();

    }

    @Override
    public void drawRealImage(Image image, int posX, int posY, float alpha, float scaleX, float scaleY, int rectMin, int rectMax) {

    }

    @Override
    public int getWidth() {
        lockCanvas();
        int width=canvas.getWidth();
        unLockCanvas();
        return width;
    }

    @Override
    public int getHeight() {
        lockCanvas();
        int height=canvas.getHeight();
        unLockCanvas();
        return height;
    }

    private void lockCanvas()
    {
        while(!surfaceview_.getHolder().getSurface().isValid())
            ;
        canvas = surfaceview_.getHolder().lockCanvas();
    }
    private void unLockCanvas()
    {
        surfaceview_.getHolder().unlockCanvasAndPost(canvas);
    }
}
