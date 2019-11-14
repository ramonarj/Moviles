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
    public void drawRealImage(Image image, es.ucm.fdi.moviles.engine.Rect destRect, float alpha) {

        Paint paint=new Paint();
        paint.setAlpha((int)alpha);

        Rect src=new Rect(0,0,image.getWidth(),image.getHeight());
        Rect dest=new Rect(destRect.x1(),destRect.y1(),destRect.x2(),destRect.y2());

        //Pintamos
        lockCanvas();
        canvas.drawBitmap(((AndroidImage) image).sprite,src,dest,paint);
        unLockCanvas();
    }

    @Override
    public void drawRealImage(Image image, es.ucm.fdi.moviles.engine.Rect destRect) {

        Rect src=new Rect(0,0,image.getWidth(),image.getHeight());
        Rect dest=new Rect(destRect.x1(),destRect.y1(),destRect.x2(),destRect.y2());

        //Pintamos
        lockCanvas();
        canvas.drawBitmap(((AndroidImage) image).sprite,src,dest,null);
        unLockCanvas();
    }

    @Override
    public void drawRealImage(Image image, es.ucm.fdi.moviles.engine.Rect srcRect, es.ucm.fdi.moviles.engine.Rect destRect, float alpha) {

        Paint paint=new Paint();
        paint.setAlpha((int)alpha);

        Rect src=new Rect(srcRect.x1(),srcRect.y1(),srcRect.x2(),srcRect.y2());
        Rect dest=new Rect(destRect.x1(),destRect.y1(),destRect.x2(),destRect.y2());

        //Pintamos
        lockCanvas();
        canvas.drawBitmap(((AndroidImage) image).sprite,src,dest,paint);
        unLockCanvas();
    }

    @Override
    public void drawRealImage(Image image, es.ucm.fdi.moviles.engine.Rect srcRect, es.ucm.fdi.moviles.engine.Rect destRect) {

        Rect src=new Rect(srcRect.x1(),srcRect.y1(),srcRect.x2(),srcRect.y2());
        Rect dest=new Rect(destRect.x1(),destRect.y1(),destRect.x2(),destRect.y2());

        //Pintamos
        lockCanvas();
        canvas.drawBitmap(((AndroidImage) image).sprite,src,dest,null);
        unLockCanvas();
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

    @Override
    public int getLogicalWidth() {
        return this.logicalWidth;
    }

    @Override
    public int getLogicalHeight() {
        return this.logicalHeight;
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
