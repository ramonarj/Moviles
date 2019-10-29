package es.ucm.fdi.moviles.interfacemodule;

public interface Game
{
    Graphics getGraphics();
    Input getInput();
    void setGameState(GameState state);
}
