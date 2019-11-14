import es.ucm.fdi.moviles.engine.Game;
import es.ucm.fdi.moviles.engine.GameState;
import es.ucm.fdi.moviles.engine.Graphics;
import es.ucm.fdi.moviles.engine.Image;
import es.ucm.fdi.moviles.engine.Rect;
import es.ucm.fdi.moviles.engine.ResourceMan;

public class GameOverState implements GameState {


    //Objeto del juego
    private Game game;

    public GameOverState(Game game)
    {
        this.game = game;
    }

    @Override
    public boolean init() {
        gameOver = ResourceMan.getImage("GameOver");
        playAgain = ResourceMan.getImage("PlayAgain");
        return true;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render()
    {
        //Color de fondo (para las barras laterales)
        Graphics g = game.getGraphics();
        g.clear(0x000000);

        //Game Over
        Rect dstRect = new Rect(g.getWidth() / 3,364 ,
                g.getWidth() / 4,g.getHeight() / 8);
        //g.drawImage(gameOver, dstRect, 1f);

        //Play again
        dstRect = new Rect(g.getWidth() / 3,1396 , //950, 1464
                g.getWidth() / 3,g.getHeight() / 30);
        //g.drawImage(playAgain, dstRect, 1f);

    }

    private Image gameOver;
    private Image playAgain;
}
