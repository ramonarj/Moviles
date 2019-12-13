using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BoardManager : MonoBehaviour
{
    //PÚBLICO
    [Tooltip("Número de filas del tablero")]
    public int rows;
    [Tooltip("Número de columnas del tablero")]
    public int cols;
    [Tooltip("Casilla que empezará marcada")]
    public Tile startingTile;
    [Tooltip("Iimagen que rodea el raton")]
    public SpriteRenderer fondoRaton_;

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
        touched = new bool[rows, cols];
        tiles = new Tile[rows, cols];
        tilePath = new Stack<tilePosition>();

        /*Inicialización de variables auxiliares*/
        firstTile = transform.GetChild(0).transform.position;
        tileNo = transform.childCount;
        pressedTile = startingTile;
        pressedTile.setTouch();

        /*Pusheamos la primera posicion del tile inical*/
        /*Pasamos a índices de la matriz (x = nºcolumna, y=nºfila)*/
        int x = (int)startingTile.transform.position.x;
        int y = (int)startingTile.transform.position.y;

        x += cols / 2;
        y += rows / 2;
        tilePath.Push(new tilePosition(x, y));

        /*Ponemos el primer Tile a marcado*/
        touched[x, y] = true;

        /*Rellenamos el vector de Tiles*/
        initTiles();

        ratonFondo_ = null;
    }
    
    /*Anade al array todos los tiles de la matriz , ademas de encender el tile por donde se comienza*/
    private void initTiles()
    {
        for (int i=0;i<rows;i++)
            for(int j=0;j<cols;j++)
                tiles[j, i] = transform.GetChild(i * rows + j).GetComponent<Tile>();
    }
    /*Devuelve true si la posicion esta dentro de los limites,false en caso contrario*/
    private bool insideLimits(float x, float y)
    {
        return x > (float)cols / -2.0f && x < (float)cols / 2.0f &&
            y > (float)rows / -2.0f && y < (float)rows / 2.0f;
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
        if(tilePath.Count == tileNo)
        {
            Debug.Log("Gané gané");
            //GameManager.instance.levelCompleted();
        }
    }

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

    private void goBackToTile(int x, int y)
    {
        //Desencolar
        Tile tile = tiles[x, y];
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

    private void handlePress(Vector3 screenPos)
    {
        //Hemos hecho click anteriormente dentro del tablero
        if (ratonFondo_ != null)
        {
            //Coorenadas locales del ratón (el 0,0 está en el medio del tablero)
            Vector3 mousePos = FindObjectOfType<Camera>().ScreenToWorldPoint(screenPos);

            //Actualizamos el círculo
            ratonFondo_.transform.position = new Vector3(mousePos.x, mousePos.y, 0);

            //Si estamos dentro de los límites, vemos si podemos pulsar algo
            if (insideLimits(mousePos.x, mousePos.y))
            {
                //Redondeamos para obtener el centro del tile donde se ha pulsado
                int x = Mathf.RoundToInt(mousePos.x);
                int y = -Mathf.RoundToInt(mousePos.y);

                //Pasamos a índices de la matriz (x = nºcolumna, y=nºfila)
                x += cols / 2;
                y += rows / 2;

                //Tile que hemos pulsado
                Tile tile = tiles[x, y];
                if (tile != null && tile != pressedTile)
                {
                    //1. Si ya estaba pulsado, desencolamos movimientos hasta que llegamos a ese tile
                    if (touched[x, y])
                        goBackToTile(x, y);

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
        Vector3 mousePos = FindObjectOfType<Camera>().ScreenToWorldPoint(screenPos);
        if (insideLimits(mousePos.x, mousePos.y))
            ratonFondo_ = Instantiate(fondoRaton_, new Vector3(mousePos.x, mousePos.y, 10), Quaternion.identity).gameObject;
    }

    private void handleRelease()
    {
        //Eliminamos el circulo del fondo
        if(ratonFondo_ != null)
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
