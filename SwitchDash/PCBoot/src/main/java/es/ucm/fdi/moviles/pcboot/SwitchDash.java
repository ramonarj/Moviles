package es.ucm.fdi.moviles.pcboot;

import com.example.logic.SwitchDashLogic;

import javax.swing.JFrame;
import es.ucm.fdi.moviles.pcmodule.PCGame;
import es.ucm.fdi.moviles.pcmodule.PCGraphics;
import es.ucm.fdi.moviles.pcmodule.PCInput;

/**
 * Clase que hereda de JFrame y contiene lo necesario para pintar cosas en Java
 * Sacado en gran mayoría de los apuntes del profesor
 */
public class SwitchDash extends JFrame
{

    /**
     * Constructor.
     *
     * @param title Texto que se utilizará como título de la ventana
     * que se creará.
     */
    public SwitchDash(String title) {
        super(title);
    }


    /**
     * Realiza la inicialización del objeto (inicialización en dos pasos).
     * Se configura el tamaño de la ventana, se habilita el cierre de la
     * aplicación al cerrar la ventana, y se carga la imagen que se mostrará
     * en la ventana.
     *
     * Debe ser llamado antes de mostrar la ventana (con setVisible()).
     *
     * @return Cierto si todo fue bien y falso en otro caso (se escribe una
     * descripción del problema en la salida de error).
     */
    public boolean init() {

        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Añadido
        setIgnoreRepaint(true);
        setVisible(true);

        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return false;
        }
        else {
            // En "modo debug" podríamos querer escribir esto.
            //System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }
        return true;
    }

    public static void main(String[]args)
    {
        System.out.println("Ejecutando en PC");

        //1. CREAMOS LA VENTANA
        SwitchDash ventana = new SwitchDash("Switch Dash");
        if (!ventana.init())
            return;


        // Obtenemos el Buffer Strategy que se supone acaba de crearse.
        java.awt.image.BufferStrategy strategy = ventana.getBufferStrategy();

        //2. CREAMOS LOS SUBSISTEMAS Y SE LOS PASAMOS AL JUEGO
        PCInput input = new PCInput();
        //El graphics lo creamos referenciando la ventana
        PCGraphics graphics = new PCGraphics(ventana);
        //Al juego le pasamos los sistemas de input y gráficos además de la lógica
        PCGame game = new PCGame(graphics, input);

        //3. EL JUEGO Y LA LÓGICA TIENEN REFERENCIAS MUTUAS
        SwitchDashLogic logica = new SwitchDashLogic(game);
        game.setGameState(logica);



        //3. BUCLE PRINCIPAL
        long lastFrameTime = System.nanoTime();
        while(true)
        {
            //Calcular el deltaTime
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;

            //Update
            logica.update((float)elapsedTime);
            //Render

            logica.render();
        }
    }
}
