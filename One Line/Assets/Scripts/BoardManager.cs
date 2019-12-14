using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BoardManager : MonoBehaviour
{
    public enum REF_SYSTEM { GLOBAL, LOCAL };
    //PÚBLICO
    [Tooltip("Número de filas del tablero")]
    public int rows;
    [Tooltip("Número de columnas del tablero")]
    public int cols;
    [Tooltip("Iimagen que rodea el raton")]
    public SpriteRenderer fondoRaton_;
    [Tooltip("El prefab del Tile")]
    public GameObject tilePrefab;

    /*Estructura que necesitaremos para tener la correlacion entre el array de booleanos y tiles*/
    struct tilePosition
    {
        public int x_;
        public int y_;

        //Constructora
        public tilePosition(int x, int y)
        {
            x_ = x;
            y_ = y;
        }

        //Operadores de igualdad y desigualdad
        public static bool operator ==(tilePosition lhs, tilePosition rhs)
        {
            return (lhs.x_ == rhs.x_ && lhs.y_ == rhs.y_);
        }
        public static bool operator !=(tilePosition lhs, tilePosition rhs)
        {
            return !(lhs == rhs);
        }
    }

    //PRIVADO
    [Tooltip("Casilla de arriba a la izquierda")]
    private Vector3 firstTile;
    [Tooltip("Tile que se esta pulsando en el momento")]
    private Tile pressedTile;
    [Tooltip("Cola de casillas pulsadas")]
    private Stack<tilePosition> tilePath;
    [Tooltip("Numeor de tiles totales")]
    private int tileNo;

    [Tooltip("Array de booleanos para conocer el estado del tile")]
    private bool[,] touched;

    [Tooltip("Array de Tiles que componene el nivel")]
    private Tile[,] tiles;
    [Tooltip("Gameobject adjuntado al fondo del raton")]
    private GameObject ratonFondo_;


    /*Inicializamos los atributos necesarios*/
    void Start()
    {
        /*Inicializamos el array de booleanos, tiles y el path*/
        touched = new bool[cols, rows];
        tiles = new Tile[cols, rows];
        tilePath = new Stack<tilePosition>();

        /*Creamos el vector de Tiles*/
        initTiles();

        /*Inicialización de variables auxiliares*/
        firstTile = tiles[0, 0].transform.position;
        tileNo = transform.childCount;

        //Casilla por la que empezamos
        tilePosition starting = new tilePosition(0, 0);
        tilePath.Push(starting);

        /*La marcamos y la activamos*/
        touched[starting.x_, starting.y_] = true;

        pressedTile = tiles[starting.x_, starting.y_];
        pressedTile.setTouch();

        //Círculo desactivado
        ratonFondo_ = null;
    }

    /*Anade al array todos los tiles de la matriz , ademas de encender el tile por donde se comienza*/
    private void initTiles()
    {
        //Ponemos el tablero abajo a la izquierda
        transform.position = new Vector3((float)-cols / 2, (float)-rows / 2, 0);

        //Offset para los tamaños pares
        float rowOff = 0; if (rows % 2 == 0) rowOff = -0.5f;
        float colOff = 0; if (cols % 2 == 0) colOff = 0.5f;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                GameObject o = Instantiate(tilePrefab, transform);
                o.transform.position = new Vector3(j - (int)cols / 2 + colOff, -(i - (int)rows / 2) + rowOff); //+colOff, +rowOff
                //TODO: moverlo
                tiles[j, i] = o.GetComponent<Tile>();
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
        int verticalDist = Mathf.Abs(tile1.y_ - tile2.y_);
        int horizontalDist = Mathf.Abs(tile1.x_ - tile2.x_);

        return (verticalDist + horizontalDist == 1);
    }

    /*Devuelve true si has ganado la partida, false en caso contrario*/
    private void checkWin()
    {
        if (tilePath.Count == tileNo)
        {
            Debug.Log("Gané gané");
            //GameManager.instance.levelCompleted();
        }
    }

    //Obtiene la dirección necesaria para ir de un tile a otro
    private Utils.Direction getDirFrom(tilePosition from, tilePosition to)
    {
        if (from.x_ < to.x_)
            return new Utils.Direction(Utils.DirectionEnum.Right);
        else if (from.x_ > to.x_)
            return new Utils.Direction(Utils.DirectionEnum.Left);
        else if (from.y_ > to.y_)
            return new Utils.Direction(Utils.DirectionEnum.Up);
        else if (from.y_ < to.y_)
            return new Utils.Direction(Utils.DirectionEnum.Down);
        else
            return new Utils.Direction(Utils.DirectionEnum.None);
    }

    //Presiona un tile
    private void pressTile(int x, int y)
    {
        //Marcamos en el array
        touched[x, y] = true;
        tiles[x, y].setTouch();

        //Dirección del anterior tile a este
        tilePosition t = new tilePosition(x, y);
        Utils.Direction dir = getDirFrom(tilePath.Peek(), t);
        pressedTile.setPath(dir);

        //Lo metemos en el path y actualizamos el tile pulsado
        tilePath.Push(new tilePosition(x, y));
        pressedTile = tiles[x, y];

        //La dirección del nuevo tile es la inversa que la del otro
        pressedTile.setPath(dir.inverse());
    }

    //Vuelve al tile con la posición dada
    private void goBackToTile(tilePosition tilePos)
    {
        //Desencolar
        Tile tile = tiles[tilePos.x_, tilePos.y_];
        tilePosition t = tilePath.Peek();
        while (tiles[t.x_, t.y_] != tile)
        {
            t = tilePath.Pop();
            touched[t.x_, t.y_] = false;
            //Desactivar sprites del nuevo tile
            Utils.Direction dir = getDirFrom(t, tilePath.Peek());
            tiles[t.x_, t.y_].unsetPath(dir);
            tiles[t.x_, t.y_].setUnTouch();

            //Desactivar path del nuevo tile
            t = tilePath.Peek();
            tiles[t.x_, t.y_].unsetPath(dir.inverse());
        }
        pressedTile = tile;
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
            ratonFondo_.transform.position = new Vector3(worldPos.x, worldPos.y, 10.0f);

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
                if (tile != null && tile != pressedTile)
                {
                    //1. Si ya estaba pulsado, desencolamos movimientos hasta que llegamos a ese tile
                    if (touched[x, y])
                        goBackToTile(new tilePosition(x, y));

                    //2. Si no, marcamos el nuevo tile con todo lo que conlleva
                    else if (isAdjacent(tilePath.Peek(), new tilePosition(x, y)))
                        pressTile(x, y);
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
            ratonFondo_ = Instantiate(fondoRaton_, new Vector3(worldPos.x, worldPos.y, 10.0f), Quaternion.identity).gameObject;
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
            Debug.Log("Gané gané");
            //GameManager.instance.levelCompleted();
        }
    }


    void Update()
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
}
