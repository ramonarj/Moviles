using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    public static GameManager instance;

    //Prefab del GO con el componente con AudioSource
    public GameObject reproductor;

    //[Tooltip("Ruta del archivo donde están los niveles")]
    //public string levelsFile;

    //Número de dificultades que hay
    private const int DIFFICULTIES_NO = 5;

    //Número de monedas
    private int coinNo;

    //Número de niveles que nos hemos pasado en cada nivel de dificultad (5 enteros de 1-100)
    private List<int> levelprogress;

    //Datos de todos los niveles
    private LevelDataList levelDataList;

    //Nivel seleccionado para jugar
    private int actualLevel;

    //Audio source que se crea solo para reproducir sonidos
    private GameObject source;

    void Awake()
    {
        // Singleton
        if (instance == null)
            instance = this;
        else
            Destroy(this.gameObject);

        //Leemos los niveles
        string inputJson = File.ReadAllText(Application.dataPath + "/Levels/Prueba.json");
        levelDataList = LevelDataList.CreateFromJSON(inputJson);
        actualLevel = 1;

        Object.DontDestroyOnLoad(gameObject);
    }

    void Start()
    {
        coinNo = 0;
        levelprogress = new List<int>();

        for (int i = 0; i < DIFFICULTIES_NO; i++)
            levelprogress.Add(0);
    }

    //Devuelve los datos del nivel especificado
    public LevelData getLevelData(int index)
    {
        return levelDataList[index - 1];
    }

    public int getActualLevel()
    {
        return actualLevel;
    }


    //Hemos completado el nivel que estábamos jugado
    public void levelCompleted()
    {
        //Vemos de qué dificultad y nivel se trata
        int levelNo = actualLevel - 1; //Empieza en 0 para el GameManager
        int difficulty = levelNo / 100;
        int number = levelNo % 100;

        //Nos tocaba pasarnos ese nivel
        if (levelprogress[difficulty] == number) //Habría que cambiarlo por un ==
            levelprogress[difficulty]++; //y por un ++

        Debug.Log("Has completado el nivel " + (number+1) + " de la dificultad " + (difficulty+1));
    }

    //Nos lleva a la pantalla de selección
    public void GoToSeleccion(int difficulty)
    {
        SceneManager.LoadScene("Seleccion", LoadSceneMode.Single);
    }

    //Nos lleva a la pantalla de nivel
    public void GoToLevel(int levelNo)
    {
        //TODO: comprobar que toca jugar ese nivel
        actualLevel = levelNo;
        GoToScene("Nivel");
    }

    //Jugamos el siguiente nivel
    public void NextLevel()
    {
        actualLevel++;
        GoToScene("Nivel");
    }

    //Nos lleva a la pantalla de  menú
    public void GoToMenu()
    {
        GoToScene("Menu");
    }

    //Sale de la aplicación
    public void QuitApp()
    {
        Debug.Log("Quitting...");
        Application.Quit();
    }

    public void addCoins(int n)
    {
        //TODO: actualizar el GUI
        Debug.Log("+" + n + " monedas");
        coinNo += n;
    }

    public int getLevelProgress(int difficulty)
    {
        return levelprogress[difficulty - 1];
    }

    public void playSound(AudioClip clip)
    {
        source = Instantiate(reproductor);
        source.GetComponent<AudioSource>().PlayOneShot(clip);
        Destroy(source, clip.length);
    }

    //Corutina para ir a una escena esperando antes que termine cualquier sonido que estuviéramos reproduciendo
    private void GoToScene(string scene)
    {
        SceneManager.LoadScene(scene, LoadSceneMode.Single);
    }
}
