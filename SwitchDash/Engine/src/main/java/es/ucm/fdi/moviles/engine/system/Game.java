package es.ucm.fdi.moviles.engine.system;

import es.ucm.fdi.moviles.engine.graphics.Graphics;
import es.ucm.fdi.moviles.engine.input.Input;

public interface Game
{
    Graphics getGraphics();
    Input getInput();
    void setGameState(GameState state);
    void run();
    public void GameOver();
}
