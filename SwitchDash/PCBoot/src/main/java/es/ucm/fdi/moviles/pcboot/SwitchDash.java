package es.ucm.fdi.moviles.pcboot;

import com.example.logic.PlayState;

import es.ucm.fdi.moviles.pcmodule.PCGame;
import es.ucm.fdi.moviles.pcmodule.PCGraphics;
import es.ucm.fdi.moviles.pcmodule.PCInput;
import es.ucm.fdi.moviles.pcmodule.Window;

/**
 * Clase que arranca el juego para PC. Usa una Window (que en Android no es necesaria)
 * y usa las clases del motor específicas para PC
 */
public class SwitchDash
{
    public static void main(String[]args)
    {
        //1. CREAMOS LA VENTANA
        Window ventana = new Window("Switch Dash");
        if (!ventana.init(400, 600, false))
            return;

        //2. CREAMOS LOS SUBSISTEMAS Y SE LOS PASAMOS AL JUEGO
        PCInput input = new PCInput();
        ventana.addMouseListener(input);
        ventana.addMouseMotionListener(input);

        //El graphics lo creamos referenciando la ventana
        PCGraphics graphics = new PCGraphics(ventana);
        ventana.addComponentListener(graphics);
        //Al juego le pasamos los sistemas de input y gráficos además de la lógica
        PCGame game = new PCGame(graphics, input, ventana);

        //3. EL JUEGO Y LA LÓGICA TIENEN REFERENCIAS MUTUAS
        PlayState logica = new PlayState(game);
        logica.init();
        game.setGameState(logica);

        //4. EJECUTAR EL JUEGO
        game.run();
    }
}
