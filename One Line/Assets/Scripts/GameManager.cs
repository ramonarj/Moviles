using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public static GameManager instance;

    //Prefab del GO con el componente con AudioSource
    public GameObject reproductor;

    //Nombres de las dificultades
    public List<string> difficulties;

    [Tooltip("Ruta del archivo donde están los niveles")]
    public string levelsFile;

    //Número de monedas
    private int coinNo=0;

    //Número de niveles que nos hemos pasado en cada nivel de dificultad (5 enteros de 1-100)
    private List<int> levelprogress;

    //Datos de todos los niveles
    private LevelDataList levelDataList;

    //Nivel seleccionado para jugar
    private int actualLevel; // 1-100 
    private int actualDifficulty; //1-5

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
        string inputJson = File.ReadAllText(Application.dataPath + levelsFile);
        levelDataList = LevelDataList.CreateFromJSON(inputJson);

        //Para hacer pruebas
        actualLevel = 1;
        actualDifficulty = 1;

        Object.DontDestroyOnLoad(gameObject);
    }

    void Start()
    {
        coinNo = 0;
        levelprogress = new List<int>();

        for (int i = 0; i < difficulties.Count; i++)
            levelprogress.Add(0);

    }

    //Devuelve los datos del nivel especificado
    public LevelData getLevelData(int index)
    {
        //Hay que cubrirse las espaldas por si no están todos los niveles del 0 al "index"
        return levelDataList.levels.Find(x => x.index == index);
    }

    public int getActualLevel()
    {
        return actualLevel;
    }

    public int getActualDifficulty()
    {
        return actualDifficulty;
    }

    public string getActualDifficultyName()
    {
        return difficulties[actualDifficulty - 1];
    }

    public int getCoins()
    {
        return coinNo;
    }


    //Hemos completado el nivel que estábamos jugado
    public void levelCompleted()
    {
        //Si nos tocaba pasarnos ese nivel, actualizamos la lista
        if (levelprogress[actualDifficulty - 1] == actualLevel - 1)
            levelprogress[actualDifficulty - 1]++;
    }

    //Nos lleva a la pantalla de selección (seleccionando dificultad
    public void GoToSeleccion(int difficulty)
    {
        actualDifficulty = difficulty;
        SceneManager.LoadScene("Seleccion", LoadSceneMode.Single);
    }

    //Nos lleva a la pantalla de selección (sin cambiar la dificultad)
    public void GoToSeleccion()
    {
        SceneManager.LoadScene("Seleccion", LoadSceneMode.Single);
    }

    //Nos lleva a la pantalla de nivel
    public void GoToLevel(int levelNo)
    {
        //Nivel máximo al que podemos jugar
        int maxLevel = levelprogress[actualDifficulty - 1];
        //Comprobamos que está desbloqueado
        if (maxLevel >= levelNo - 1)
        {
            actualLevel = levelNo;
            GoToScene("Nivel");
        }
    }


    //Jugamos el siguiente nivel
    public void NextLevel()
    {
        actualLevel++;
        GoToScene("Nivel");
        SaveDataManager.instance.save(levelprogress, coinNo, 0, 0);
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
        //Cogemos el texto de las monedas
        coinNo += n;
        Text conisTetx = GameObject.Find("Numero").GetComponent<Text>();
        conisTetx.text = System.Convert.ToString(coinNo);
    }

    public void decreaseCoins(int n)
    {
        //Cogemos el texto de las monedas
        coinNo -= n;
        Text conisTetx = GameObject.Find("Numero").GetComponent<Text>();
        conisTetx.text = System.Convert.ToString(coinNo);
    }

    public int getLevelProgress(int difficulty)
    {
        return levelprogress[difficulty - 1];
    }

    //Instancia un clon del reproductor para que reproduzca el clip en cuestión
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
