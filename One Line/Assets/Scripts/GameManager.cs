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
        if (instance != null && instance != this)
            Destroy(gameObject);
        else
        {
            instance = this;

            //Leemos los niveles
            string inputJson = File.ReadAllText(Application.dataPath + levelsFile);
            levelDataList = LevelDataList.CreateFromJSON(inputJson);

            //Para hacer pruebas
            actualLevel = 1;
            actualDifficulty = 1;

            //Start
            coinNo = 0;
            levelprogress = new List<int>();
            for (int i = 0; i < difficulties.Count; i++)
                levelprogress.Add(0);

            SceneManager.sceneLoaded += OnSceneLoaded;
            Object.DontDestroyOnLoad(gameObject);
        }
    }

    void Start()
    {

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
        GameObject numero = GameObject.Find("Numero");
        if (numero != null)
        {
            Text conisTetx = GameObject.Find("Numero").GetComponent<Text>();
            conisTetx.text = System.Convert.ToString(coinNo);
        }
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
        SceneManager.LoadScene(scene);
    }

    IEnumerator GoToSceneDelay(string scene, float delay)
    {
        yield return new WaitForSeconds(delay);
        SceneManager.LoadScene(scene);
    }

    //Este método se llama siempre que se carga una escena nueva y sirve sobre todo para inicializar GO de la escena (como los textos)
    //en función de variables del código
    private void OnSceneLoaded(Scene scene, LoadSceneMode mode)
    {
        GameObject monedas = GameObject.Find("Numero");
        GameObject dificultad = GameObject.Find("Dificultad");
        switch (scene.name)
        {
                //Pantalla de carga
            case "Loading":
                StartCoroutine(GoToSceneDelay("Menu", 1f));
                break;
                //Menu
            case "Menu":
                GameObject gamemodes = GameObject.Find("Gamemodes");
                if (gamemodes != null)
                    for (int i = 0; i < gamemodes.transform.childCount - 1; i++)
                        gamemodes.transform.GetChild(i).GetChild(1).GetComponent<Text>().text = levelprogress[i].ToString() + "/100";
                if(monedas != null)
                    monedas.GetComponent<Text>().text = coinNo.ToString();
                break;
                //Selección de nivel
            case "Seleccion": 
                if (dificultad != null)
                    dificultad.GetComponent<Text>().text = difficulties[actualDifficulty - 1];
                break;
                //Nivel
            case "Nivel":
                if (dificultad != null)
                    dificultad.GetComponent<Text>().text = difficulties[actualDifficulty - 1] + " " + actualLevel.ToString();
                if (monedas != null)
                    monedas.GetComponent<Text>().text = coinNo.ToString();
                break;
            default:
                break;
        }
    }
}
