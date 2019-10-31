package es.ucm.fdi.moviles.interfacemodule;

import com.sun.xml.internal.ws.server.provider.SyncProviderInvokerTube;

public class Sprite {
    public Sprite(Image image, Rect rect)
    {
        this.img_=image;
        this.rect_=rect;
    }

    Image img_;
    Rect rect_;
}
