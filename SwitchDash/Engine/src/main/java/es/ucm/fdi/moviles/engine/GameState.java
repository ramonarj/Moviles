package es.ucm.fdi.moviles.engine;

public interface GameState {
    public void update(float deltaTime);
    public void render();
}
