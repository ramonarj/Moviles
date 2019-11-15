package es.ucm.fdi.moviles.engine;

public interface Game
{
    Graphics getGraphics();
    Input getInput();
    void setGameState(GameState state);
    void run();
    public void GameOver();
}
