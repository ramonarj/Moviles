using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour
{
    public static GameManager instance;

    //[Tooltip("Ruta del archivo donde están los niveles")]
    //public string levelsFile;

    //Número de dificultades que hay
    private const int DIFFICULTIES_NO = 5;

    //Número de monedas
    private int coinNo;

    //Número de niveles que nos hemos pasado en cada nivel de dificultad (5 enteros de 1-100)
    private List<int> levelprogress;

    //Lista de niveles, con su número, disposición y forma de ganar
    private LevelDataList levelDataList;

    void Awake()
    {
        // Singleton
        if (instance == null)
            instance = this;
        else
            Destroy(this.gameObject);

        //Output the Game data path to the console
        string inputJson = File.ReadAllText(Application.dataPath + "/Levels/Prueba.json");
        levelDataList = LevelDataList.CreateFromJSON(inputJson);
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

    public void levelCompleted(int levelNo)
    {
        //Vemos de qué dificultad y nivel se trata
        levelNo -= 1; //Empieza en 0 para el GameManager
        int difficulty = levelNo / 100;
        int number = levelNo % 100;

        //Nos tocaba pasarnos ese nivel
        if (levelprogress[difficulty] <= number) //Habría que cambiarlo por un ==
            levelprogress[difficulty] = number + 1; //y por un ++

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
        SceneManager.LoadScene("Nivel", LoadSceneMode.Single);
    }

    //Nos lleva a la pantalla de  menú
    public void GoToMenu()
    {
        SceneManager.LoadScene("Menu", LoadSceneMode.Single);
    }


    public int getLevelProgress(int difficulty)
    {
        return levelprogress[difficulty];
    }
}
