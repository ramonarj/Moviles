package es.ucm.fdi.moviles.engine;

public interface GameState {

    public boolean init();
    public void update(float deltaTime);
    public void render();
}
