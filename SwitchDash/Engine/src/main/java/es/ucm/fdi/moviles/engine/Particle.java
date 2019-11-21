package es.ucm.fdi.moviles.engine;

public class Particle {

    public Particle(int posX,int posY, int velX,int velY,int width, int height,float alpha)
    {
        this.im_=ResourceMan.getImage("Balls");
        this.posX=posX;
        this.posY=posY;
        this.velX=velX;
        this.velY=velY;
        this.width=im_.getWidth()/width;
        this.height=im_.getHeight()/height;
        this.height=height;
        this.alpha=alpha;

    }

    public void update(float deltaTime)
    {
        posX+=velX*deltaTime;
        posY+=velY*deltaTime;
        alpha-=(float)(Math.floor(Math.random() * 220)/255)*deltaTime;
    }

    public int getX(){return posX; }
    public int getY(){return posY; }
    public float getAlpha(){return  alpha;}
    public int getWidth(){return  width;}
    public int getheight(){return height;}
    public Image getImage(){return im_;}


    private Image im_;
    private int posX;
    private int posY;
    private int velX;
    private int velY;
    private int width;
    private int height;
    private float alpha;
}
