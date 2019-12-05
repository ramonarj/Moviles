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

    /*Estructura que necesitaremos para tener la correlacion entre el array de booleanos y tiles*/
    struct tilePosition
    {
        public int x_;
        public int y_;
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

    
    /*Inicializamos los atributos necesarios*/
    void Start()
    {
        /*Esquina superior izquierda de la matriz*/
        firstTile = transform.GetChild(0).transform.position;

        /*Numero de elementos de la matriz*/
        tileNo = transform.childCount;

        startingTile.setTouch();
        pressedTile = startingTile;

        /*Inicializamos la pila */
        tilePath = new Stack<tilePosition>();

        /*Pusheamos la primera posicion del tile inical*/
        /*Pasamos a índices de la matriz (x = nºcolumna, y=nºfila)*/
        int x = (int)startingTile.transform.position.x;
        int y = (int)startingTile.transform.position.y;

        x += cols / 2;
        y += rows / 2;

        //Índice del hijo pulsado
        int matrizIndex = rows * y + x;
        tilePath.Push(new tilePosition { x_=x,y_=y});

        /*Inicializamos el array de booleanos*/
        touched = new bool[rows,cols];
        tiles = new Tile[rows, cols];

        /*Ponemos el primer Tile a marcado*/
        touched[x, y] = true;

        /*Rellenamos el vector de Tiles*/
        initTiles();
    }
    
    /*Anade al array todos los tiles de la matriz , ademas de encender el tile por donde se comienza*/
    private void initTiles()
    {
        for(int i=0;i<rows;i++)
            for(int j=0;j<cols;j++)
            {
                Tile aux= this.transform.GetChild(i * rows + j).GetComponent<Tile>();
                if (aux == pressedTile)
                    aux.setTouch();
                
                tiles[j, i] = aux;
            }
    }
    /*Devuelve true si la posicion esta dentro de los limites,false en caso contrario*/
    private bool insideLimits(float x, float y)
    {
        return x > (float)cols / -2.0f && x < (float)cols / 2.0f &&
            y > (float)rows / -2.0f && y < (float)rows / 2.0f;
    }

    /*Devuelve true si el tile1 es adyacente a tile2 , false en caso contrario*/
    private bool isAdjacent(Tile tile1, Tile tile2)
    {
        //Índices de los tiles, al ser hijos del tablero
        int index1 = tile1.transform.GetSiblingIndex();
        int index2 = tile2.transform.GetSiblingIndex();

        //Comprobamos que sean adyacentes
        bool verticalAdy = Mathf.Abs(index1 - index2) == cols;
        bool horizontalAdy = Mathf.Abs(index1 - index2) == 1 && 
           !((index1 % cols == 0 && index2 < index1) || (index2 % cols == 0 && index1 < index2));

        return verticalAdy || horizontalAdy;
    }

    /*Devuelve true si has ganado la partida, false en caso contrario*/
    private void checkWin()
    {
        if(tilePath.Count == tileNo)
        {
            Debug.Log("Gané gané");
        }
    }

    void Update()
    {
        //Pulsación del ratón 
        if (Input.GetMouseButton(0))
        {
            //Coorenadas locales del ratón (el 0,0 está en el medio del tablero)
            Vector3 mousePos = FindObjectOfType<Camera>().ScreenToWorldPoint(Input.mousePosition);

            if (insideLimits(mousePos.x, mousePos.y))
            {
                //Redondeamos para obtener el centro del tile donde se ha pulsado
                int x = Mathf.RoundToInt(mousePos.x);
                int y = - Mathf.RoundToInt(mousePos.y);

                //Pasamos a índices de la matriz (x = nºcolumna, y=nºfila)
                x += cols / 2;
                y += rows / 2;

                //Llamar al toggle del hijo
                Tile tile = tiles[x,y];
                if (tile != null && tile != pressedTile)
                {
                    //Desencolamos movimientos hasta que volvemos a ese punto
                    if (touched[x,y])
                    {
                        //Desencolar
                        while (tiles[tilePath.Peek().x_,tilePath.Peek().y_] != tile)
                        {
                            tilePosition t = tilePath.Pop();
                            tiles[t.x_, t.y_].setUnTouch();
                            touched[t.x_, t.y_] = false;
                        }
                        pressedTile = tile;
                    }

                    //Marcamos un nuevo tile
                    else if (isAdjacent(pressedTile, tile))
                    {
                        tilePath.Push(new tilePosition {x_=x,y_=y});
                        tiles[tilePath.Peek().x_, tilePath.Peek().y_].setTouch(); //Que haga sus cosas
                        pressedTile = tile;
                        touched[x, y] = true;
                        //Comprobamos si hemos ganado
                        checkWin();
                    }           
                }
            }
        }
    }
}
