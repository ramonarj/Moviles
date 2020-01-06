using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using Utils;

public class BoardManager : MonoBehaviour
{
    //Instancia del singleton
    public static BoardManager instance;

    //EDITOR
    //Prefabs para las pieles
    [Tooltip("El prefab del Tile")]
    public List<GameObject> tilePrefabs;
    [Tooltip("Imágenes que rodean el raton")]
    public List<GameObject> mousePrefabs;

    //GameObjects 
    [Tooltip("Panel que aparece al ganar")]
    public GameObject WinPanel;

    //Sonidos
    [Tooltip("Sonido al conectar tiles")]
    public AudioClip connectSound;
    [Tooltip("Sonido al completar el nivel")]
    public AudioClip winSound;
    [Tooltip("Sonido al reiniciar nivel")]
    public AudioClip restartSound;
    [Tooltip("Sonido al obtener una pista")]
    public AudioClip hintSound;
    [Tooltip("Sonido al no obtener una pista por falta de dinero")]
    public AudioClip brokeSound;

    //PRIVADO
    //Filas y columnas del tablero
    private int rows;
    private int cols;

    //Piel que se usará
    int skinNo;

    //Cola de casillas pulsadas
    private Stack<tilePosition> tilePath;
    //Tile por el que se empieza
    private tilePosition startingTile;
    //Numeor de tiles totales
    private int tileNo;

    //Array de booleanos para conocer el estado del tile
    private bool[,] touched;

    //Array de Tiles que componene el nivel
    private Tile[,] tiles;
    //Gameobject adjuntado al fondo del raton
    private GameObject ratonFondo_;
    //El camino para acabar el nivel actual
    private List<tilePosition> levelPath;
    //Ultima posicion de la ultima pista mostrada
    private int lastPos;

    public enum REF_SYSTEM { GLOBAL, LOCAL };

    //Tiempo del challenge
    private float timeLeft = 30.0f;

    //Para el singleton
    void Awake()
    {
        // Singleton
        if (instance == null)
            instance = this;
        else
            Destroy(this.gameObject);
    }

    /*Inicializamos los atributos necesarios*/
    void Start()
    {
        //Leemos los datos del nivel, inicializamos filas y columnas
        int level = GameManager.instance.getActualLevel(); 
        LevelData data = GameManager.instance.getLevelData(level);
        levelPath = data.path;
        if (data == null) //Si no se ha conseguido cargar el nivel
        {
            Debug.Log("No se pudo cargar el nivel");
            GameManager.instance.GoToMenu();
            return;
        }
         
        rows = data.layout.Count;
        cols = data.layout[0].Length;

        /*Inicializamos el array de booleanos, tiles y el path*/
        touched = new bool[cols, rows];
        tiles = new Tile[cols, rows];
        tilePath = new Stack<tilePosition>();

        /*Instanciamos los Tiles*/
        initTiles(data.layout);

        /*Inicialización de variables auxiliares*/
        tileNo = transform.childCount;
        ratonFondo_ = null;

        /*Ultima pista puesta*/
        lastPos = 0;

    
    }
    /*Anade al array todos los tiles de la matriz , ademas de encender el tile por donde se comienza*/
    private void initTiles(List<string> layout)
    {
        bool bigBoard = GameManager.instance.getActualDifficulty() > 2;//Para reescalar los tiles
        float tileSize = tilePrefabs[skinNo].transform.GetChild(1).GetComponent<Renderer>().bounds.size.x;

        //Vemos cuanto espacio hay disponible quitando los 2 canvas
        RectTransform upRect = GameObject.Find("UpLayout").GetComponent<RectTransform>();
        RectTransform downRect = GameObject.Find("DownLayout").GetComponent<RectTransform>();
        float availableHeight = Screen.currentResolution.height - upRect.rect.height + downRect.rect.height;
        float tileScale = availableHeight / (tileSize * 100f * 8f);
        Debug.Log(availableHeight);
        Debug.Log((tileSize * 100f * 8f));
        tileSize *= tileScale;

        //El color del tile es aleatorio de entre los disponibles
        System.Random rnd = new System.Random();
        skinNo = rnd.Next(0, tilePrefabs.Count);

        //Ponemos el tablero abajo a la izquierda
        transform.position = new Vector3((float)-cols / 2, (float)-rows / 2, -1f);

        //Offset para los tamaños pares
        float rowOff = 0; if (rows % 2 == 0) rowOff = -0.5f;
        float colOff = 0; if (cols % 2 == 0) colOff = 0.5f;
        for (int i = 0; i < rows; i++)
        {
            string rowLayout = layout[i];
            for (int j = 0; j < cols; j++)
            {
                if(rowLayout[j] != '0')
                {
                    //Creamos el objeto
                    GameObject o = Instantiate(tilePrefabs[skinNo], transform);
                    o.transform.position = new Vector3(j - (int)cols / 2 + colOff, -(i - (int)rows / 2) + rowOff, -1f);
                    //o.transform.Translate(new Vector3((tileSize * j / 10.0f, -(tileSize * i / 10.0f)); //Dejamos un espacio entre tiles
                    o.transform.localScale = new Vector3(tileScale, tileScale);
                    //TODO:escala


                    //Nos guardamos el tile en la matriz y lo ponemos gris
                    Tile tile = o.GetComponent<Tile>();
                    tiles[j, i] = tile;
                    if(rowLayout[j] == '2')
                    {
                        //Casilla por la que empezamos
                        startingTile = new tilePosition(j, i);
                        touched[startingTile.x, startingTile.y] = true;
                        tiles[startingTile.x, startingTile.y].setTouch();
                        tilePath.Push(startingTile);
                    }
                    else
                        tile.setUnTouch();
                }
            }
        }
    }

    /*Devuelve true si la posicion esta dentro de los limites,false en caso contrario*/
    private bool insideLimits(float x, float y)
    {
        return x > 0 && x < cols &&
            y > 0 && y < rows;
    }

    /*Devuelve true si el tile1 es adyacente a tile2 , false en caso contrario*/
    private bool isAdjacent(tilePosition tile1, tilePosition tile2)
    {
        //Comprobamos que sean adyacentes
        int verticalDist = Mathf.Abs(tile1.y - tile2.y);
        int horizontalDist = Mathf.Abs(tile1.x - tile2.x);

        return (verticalDist + horizontalDist == 1);
    }

    //Obtiene la dirección necesaria para ir de un tile a otro
    private Direction getDirFrom(tilePosition from, tilePosition to)
    {
        if (from.x < to.x)
            return new Direction(DirectionEnum.Right);
        else if (from.x > to.x)
            return new Direction(DirectionEnum.Left);
        else if (from.y > to.y)
            return new Direction(DirectionEnum.Up);
        else if (from.y < to.y)
            return new Direction(DirectionEnum.Down);
        else
            return new Direction(DirectionEnum.None);
    }

    //Obtiene la dirección necesaria para ir de un tile a otro
    private Direction getDirFromHint(tilePosition from, tilePosition to)
    {
        if (from.x < to.x)
            return new Direction(DirectionEnum.Down);
        else if (from.x > to.x)
            return new Direction(DirectionEnum.Up);
        else if (from.y > to.y)
            return new Direction(DirectionEnum.Left);
        else if (from.y < to.y)
            return new Direction(DirectionEnum.Right);
        else
            return new Direction(DirectionEnum.None);
    }

    //Presiona un tile
    private void pressTile(int x, int y)
    {
        //Marcamos en el array
        touched[x, y] = true;
        tiles[x, y].setTouch();

        //Dirección del anterior tile a este
        tilePosition prev = tilePath.Peek();
        tilePosition next = new tilePosition(x, y);
        Direction dir = getDirFrom(prev, next);
        tiles[prev.x, prev.y].setPath(dir);

        //Lo metemos en el path y pintamos el otro sprite de dirección
        tilePath.Push(next);
        tiles[next.x, next.y].setPath(dir.inverse());
    }

    //Vuelve al tile con la posición dada
    private void goBackToTile(tilePosition tilePos)
    {
        //Desencolar
        Tile tile = tiles[tilePos.x, tilePos.y];
        tilePosition t = tilePath.Peek();
        while (tiles[t.x, t.y] != tile)
        {
            //Sacar tile y desactivarlo
            t = tilePath.Pop();
            touched[t.x, t.y] = false;

            //Desactivar sprites del nuevo tile
            Direction dir = getDirFrom(t, tilePath.Peek());
            tiles[t.x, t.y].unsetPath(dir);
            tiles[t.x, t.y].setUnTouch();

            //Desactivar path del viejo tile (pero solo el path)
            t = tilePath.Peek();
            tiles[t.x, t.y].unsetPath(dir.inverse());
        }
    }

    //Transforma unas coordenadas de pantalla en globales/locales
    private Vector3 getPosition(Vector3 screenPos, REF_SYSTEM refSys)
    {
        //1. Pasamos de coordenadas de pantalla a coordenadas del mundo
        Vector3 worldPos = FindObjectOfType<Camera>().ScreenToWorldPoint(screenPos);
        if (refSys == REF_SYSTEM.GLOBAL)
            return worldPos;
        //2. Pasamos de coordenadas globales a coorenadas locales 
        else
            return transform.InverseTransformPoint(worldPos);
    }

    private void handlePress(Vector3 screenPos)
    {
        //Hemos hecho click anteriormente dentro del tablero
        if (ratonFondo_ != null)
        {
            //Actualizamos el círculo
            Vector3 worldPos = getPosition(screenPos, REF_SYSTEM.GLOBAL);
            ratonFondo_.transform.position = new Vector3(worldPos.x, worldPos.y, -2f);

            //Coordenadas locales del click
            Vector3 mousePos = getPosition(screenPos, REF_SYSTEM.LOCAL);

            //3. Si estamos dentro de los límites, vemos si podemos pulsar algo
            if (insideLimits(mousePos.x, mousePos.y))
            {
                //Pasamos a índices de la matriz (x = nºcolumna, y=nºfila)
                int x = Mathf.RoundToInt(mousePos.x - 0.5f);
                int y = Mathf.RoundToInt(mousePos.y - 0.5f);

                //Temporal
                y = rows - y - 1;

                //Tile que hemos pulsado
                Tile tile = tiles[x, y];
                tilePosition tilePos = new tilePosition(x, y);
                if (tile != null && tilePath.Peek() != tilePos)
                {
                    //1. Si ya estaba pulsado, desencolamos movimientos hasta que llegamos a ese tile
                    if (touched[x, y])
                        goBackToTile(tilePos);

                    //2. Si no, marcamos el nuevo tile con todo lo que conlleva
                    else if (isAdjacent(tilePath.Peek(), tilePos))
                        pressTile(x, y);
                    else
                        return;

                    //Sonido
                    GameManager.instance.playSound(connectSound);
                }
            }
        }
    }

    private void handlePressDown(Vector3 screenPos)
    {
        //Si pulsamos dentro del tablero, se crea el sprite del círculo y pasamos a poder hacer caminos
        Vector3 localPos = getPosition(screenPos, REF_SYSTEM.LOCAL);
        Vector3 worldPos = getPosition(screenPos, REF_SYSTEM.GLOBAL);
        if (insideLimits(localPos.x, localPos.y))
            ratonFondo_ = Instantiate(mousePrefabs[skinNo], new Vector3(worldPos.x, worldPos.y, -2f), Quaternion.identity).gameObject;
    }

    private void handleRelease()
    {
        //Eliminamos el circulo del fondo
        if (ratonFondo_ != null)
        {
            Destroy(ratonFondo_);
            ratonFondo_ = null;
        }

        //Comprobamos si hemos ganado
        if (tilePath.Count == tileNo)
        {
            if (!GameManager.instance.getChallenge())
            {
                //Avisamos al GM
                GameManager.instance.levelCompleted();
                GameManager.instance.playSound(winSound);

                //Activamos el panel
                WinPanel.transform.GetChild(0).GetComponent<Text>().text = GameManager.instance.getActualDifficultyName();
                WinPanel.transform.GetChild(1).GetComponent<Text>().text = GameManager.instance.getActualLevel().ToString();
                WinPanel.SetActive(true);
            }
            else
            {
                GameObject.Find("Canvas").transform.Find("WinChallenge").gameObject.SetActive(true);
                GameManager.instance.addChallengeCount();
            }
        }     
    }

    //Reinicia el nivel
    public void restartLevel()
    {
        goBackToTile(startingTile);
        GameManager.instance.playSound(restartSound);
    }

    public void showHint(bool allowed)
    {
        if (allowed)
        {
            //Primero volvemos a la posicion de salida
            goBackToTile(startingTile);
            //Mostramos los 5 siguientes tiles que no hayamos mostrado del camino
            int i = 0;
            while (i + 1 + lastPos < levelPath.Count && i < 5)
            {
                Direction dir = getDirFromHint(levelPath[i + lastPos], levelPath[i + 1 + lastPos]);
                int x = levelPath[i + lastPos].x;
                int y = levelPath[i + lastPos].y;
                //Mostramos el camino de la pista
                tiles[y, x].setHint(dir);
                i++;
            }
            lastPos += i;

            //Sonido
            GameManager.instance.playSound(hintSound);
        }
        else
            //Sonido
            GameManager.instance.playSound(brokeSound);
    }

    void Update()
    {
        //Si ya hemos completado el nivel no gestionamos nada de esto
        if (!WinPanel.active)
        {
#if !UNITY_EDITOR && (UNITY_ANDROID || UNITY_IOS)
        //TODO: multitouch
        //Pulsación del dedo
        if (Input.touchCount > 0)
        {
            Touch touch = Input.GetTouch(0);
            switch(touch.phase)
            {
                case TouchPhase.Began:
                    handlePressDown(touch.position);
                    break;
                case TouchPhase.Moved:
                    handlePress(touch.position);
                    break;
                case TouchPhase.Ended:
                    handleRelease();
                    break;
                default:
                    break;   
            }
        }
#else
            //Lo acabamos de pulsar
            if (Input.GetMouseButtonDown(0))
                handlePressDown(Input.mousePosition);
            //Lo estamos pulsando
            else if (Input.GetMouseButton(0))
                handlePress(Input.mousePosition);
            //Lo acabamos de soltar
            else if (Input.GetMouseButtonUp(0))
                handleRelease();
#endif
        }

        //Para el contador del challenge
        if (GameManager.instance.getChallenge())
        {
            timeLeft -= Time.deltaTime;

            //Vemos si ha pasado el tiempo o si has ganado 
            if (timeLeft <= 0)
            {
                //Hemos perdido
                return;
            }

            //Actualizamos el contador
            GameObject.Find("Canvas").transform.Find("Contador").GetComponent<Text>().text = "00:" + (int)timeLeft;
        }
    }
}
