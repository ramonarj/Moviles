package es.ucm.fdi.moviles.engine;

import java.util.Random;
import java.util.Vector;

public class ParticleGenerator {
    public ParticleGenerator(Graphics graphics)
    {
        this.particles_=new Vector();
        this.graphics=graphics;
        this.rand=new Random();
    }
    public void update(float f)
    {
        for(int i=0;i<particles_.size();i++)
        {
            //Actualizamos los valores de cada particula
            Particle par=(Particle)particles_.elementAt(i);
            par.update(f);

            //En caso de que sean invisibles , las quitamos del vector
            if(par.getAlpha()<=0)
            {
                particles_.remove(i);
            }
        }
    }

    public void Render()
    {
        for(int i=0;i<particles_.size();i++) {
            //Actualizamos los valores de cada particula
            Particle par = (Particle) particles_.elementAt(i);
            //Pintamos cada una de las particulas
            Rect srcRect = new Rect(0, 0,
                    par.getImage().getWidth() / 10, par.getImage().getHeight() / 2);

            Rect destRect = new Rect(par.getX(), par.getY(), par.getImage().getWidth() / 10, par.getImage().getHeight() / 2);

            Sprite particleSprite = new Sprite(par.getImage(), srcRect, graphics);
            particleSprite.draw(destRect,par.getAlpha());
        }
    }
    private void createParticle(int initialPositionX,int initialPositionY)
    {
        float alpha=(float)(Math.floor(Math.random() * 255)/255);
        int velX=rand.nextInt(250+250)-250;
        int velY=rand.nextInt(-15 + 150)-150;
        int widthDivison=rand.nextInt(10)+10;
        int heigthDivison=rand.nextInt(2)+2;
        Particle particle=new Particle(initialPositionX,initialPositionY,velX,velY,widthDivison,heigthDivison,alpha);
        particles_.add(particle);
    }

    public void createSimulation(int initialPositionX,int initialPositionY)
    {
        for(int i=0;i<5;i++)
        {
            createParticle(initialPositionX,initialPositionY);
        }
    }
    private Vector particles_;
    private Graphics graphics;
    private Random rand;
}