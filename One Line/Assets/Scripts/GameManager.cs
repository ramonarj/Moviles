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

    [Tooltip("NÚMERO DE NIVELES POR DIFICULTAD")]
    public int LEVELS_PER_DIFFICULTY;

    //Número de monedas
    private int coinNo=0;

    //Número de niveles que nos hemos pasado en cada nivel de dificultad (5 enteros de 1-100)
    private List<int> levelprogress;

    //Datos de todos los niveles
    private List<LevelDataList> levelDataLists;

    //Nivel seleccionado para jugar
    private int actualLevel; // 1-100 
    private int actualDifficulty; //1-5
    private int challengeCount;

    //Audio source que se crea solo para reproducir sonidos
    private GameObject source;
    //Cogemos el nombre del Script reproductor
    public string getString() { return reproductor.GetComponent<Reproductor>().getReproductorName(); }
    public int getNumber() { return levelprogress.Count; }

    //Conocer si nos encontramos en modo challenge
    private bool challenge;


    void Awake()
    {
        if (instance != null && instance != this)
            Destroy(gameObject);
        else
        {
            instance = this;

            //Leemos los niveles (usammos WWw)
            levelDataLists = new List<LevelDataList>();

            //Hay un archivo por cada dificultad
            for(int i= 0; i < difficulties.Count; i++)
            {
                string filePath = Application.streamingAssetsPath + "/" + difficulties[i] + ".json";

                //Funciona en PC y en Android (no hace falta poner macros)
                UnityEngine.Networking.UnityWebRequest www = UnityEngine.Networking.UnityWebRequest.Get(filePath);
                www.SendWebRequest();
                while (!www.isDone) { }
                string inputJson = www.downloadHandler.text;

                //Una vez tenemos la cadena con el json, leemos los niveles
                levelDataLists.Add(LevelDataList.CreateFromJSON(inputJson));
            }

            //Para cubrirnos las espaldas
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

        //Intentamos cargar el archivo de progreso, y si lo hemos conseguido, comprobamos el hash
        if (SaveDataManager.instance.load())
            if (compareHashes(SaveDataManager.instance.getGame()))
            {
                levelprogress = SaveDataManager.instance.getGame().levels;
                coinNo = SaveDataManager.instance.getGame().coins;
                challengeCount = SaveDataManager.instance.getGame().challenge;
                Debug.Log("Juego cargado correctamente");
            }
            else Debug.Log("Juego reiniciado debido a una modificacion del archivo de carga");

        //Empezamos con challenge a false
        challenge = false;

    }
    private bool compareHashes(GameSaving game)
    {
        string levelsConcatenate = SaveDataManager.instance.concatenateLevels(game.levels);
        string String = SaveDataManager.instance.getString(getString(), getNumber());
        string hash = SaveDataManager.instance.createHash(levelsConcatenate+ game.coins+game.premium+String+game.challenge);
        return (hash == game.hash);

    }
    //Devuelve los datos del nivel especificado
    public LevelData getLevelData(int index)
    {
        //Van del 0-500 repartidos en 5 dificultades
        int trueIndex = index + (actualDifficulty - 1) * 100;
        //Hay que cubrirse las espaldas por si no están todos los niveles del 0 al "index"
        return levelDataLists[actualDifficulty - 1].levels.Find(x => x.index == trueIndex);
    }

    /*GETTERS*/
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

    public bool getChallenge()
    {
        return challenge;
    }

    public int getLevelProgress(int difficulty)
    {
        return levelprogress[difficulty - 1];
    }

    public void setChallenge(bool challenge_) { challenge = challenge_; }
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
        challenge = false;
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
        SaveDataManager.instance.save(levelprogress, coinNo, 0, challengeCount);
    }

    public void playChallenge()
    {
        //Cogemos un nivel y dificultad aleatorios
        actualDifficulty = Random.Range(1, 1);
        actualLevel = Random.Range(1,10);
        //Ponemos el modo challenge a true
        challenge = !challenge;
        //Vamos a la escena del nivel , cuyo canvas cambiaremos 
        //depenediendo del booleano challenge
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

    public void addChallengeCount()
    {
        challengeCount++;
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

    //Instancia un clon del reproductor para que reproduzca el clip en cuestión
    public void playSound(AudioClip clip)
    {
        source = Instantiate(reproductor);
        source.GetComponent<AudioSource>().PlayOneShot(clip);
        Destroy(source, clip.length);
    }

    public void ShowChallengePanel()
    {
        GameObject.Find("Canvas").transform.Find("PopUpPanel").gameObject.SetActive(true);
        GameObject.Find("Canvas").transform.Find("PopUpPanel").transform.Find("Present").gameObject.SetActive(false);
        GameObject.Find("Canvas").transform.Find("PopUpPanel").transform.Find("Challenge").gameObject.SetActive(true);
        GameObject.Find("Canvas").transform.Find("PopUpPanel").transform.Find("BigGift").gameObject.SetActive(false);
    }

    public void ShowGiftPanel()
    {
        GameObject.Find("Canvas").transform.Find("PopUpPanel").gameObject.SetActive(true);
        GameObject.Find("Canvas").transform.Find("PopUpPanel").transform.Find("Present").gameObject.SetActive(true);
        GameObject.Find("Canvas").transform.Find("PopUpPanel").transform.Find("Challenge").gameObject.SetActive(false);
        GameObject.Find("Canvas").transform.Find("PopUpPanel").transform.Find("BigGift").gameObject.SetActive(false);
    }

    public void ShowGift()
    {
        GameObject.Find("Canvas").transform.Find("PopUpPanel").gameObject.SetActive(true);
        GameObject.Find("Canvas").transform.Find("PopUpPanel").transform.Find("Present").gameObject.SetActive(false);
        GameObject.Find("Canvas").transform.Find("PopUpPanel").transform.Find("Challenge").gameObject.SetActive(false);
        GameObject.Find("Canvas").transform.Find("PopUpPanel").transform.Find("BigGift").gameObject.SetActive(true);
    }

    public void challengeMode()
    {
        //Escogemos una dificultad aleatoria
        actualDifficulty = Random.Range(3, difficulties.Count);
        //Escogemos un nivel aleatorio
        actualLevel = Random.Range(1, LEVELS_PER_DIFFICULTY);
        //Y volvemos a recargar la escena para cargar el nivel seleccionado
        GoToScene("Nivel");
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
                {
                    for (int i = 0; i < gamemodes.transform.childCount - 1; i++)
                        gamemodes.transform.GetChild(i).GetChild(1).GetComponent<Text>().text = levelprogress[i].ToString() + "/100";
                    gamemodes.transform.GetChild(gamemodes.transform.childCount - 1).GetChild(2).GetComponent<Text>().text = challengeCount.ToString();
                }
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
                if (dificultad != null && !challenge)
                    dificultad.GetComponent<Text>().text = difficulties[actualDifficulty - 1] + " " + actualLevel.ToString();
                if (monedas != null && !challenge)
                    monedas.GetComponent<Text>().text = coinNo.ToString();
                else
                {
                    dificultad.GetComponent<Text>().text = "CHALLENGE";

                    //Elementos que desactivamos del canvas
                    GameObject monedasCanvas = GameObject.Find("Monedas");
                    monedasCanvas.SetActive(false);

                    GameObject ReiniciarCanvas = GameObject.Find("Reiniciar");
                    ReiniciarCanvas.SetActive(false);

                    GameObject anunciosCanvas = GameObject.Find("Anuncio");
                    anunciosCanvas.SetActive(false);

                    GameObject pistaCanvas = GameObject.Find("Pista");
                    pistaCanvas.SetActive(false);

                    //Hacemos visible el contador
                    GameObject.Find("Canvas").transform.Find("Contador").gameObject.SetActive(true);


                }
                break;
            default:
                break;
        }
    }
}
