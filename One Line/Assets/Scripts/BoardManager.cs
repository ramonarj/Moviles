using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BoardManager : MonoBehaviour
{
    [Tooltip("Número de filas del tablero")]
    public int rows;
    [Tooltip("Número de columnas del tablero")]
    public int cols;
    [Tooltip("Casilla que empezará marcada")]
    public Tile startingTile;

    //Casilla de arriba a la izquierda
    private Vector3 firstTile;
    //Tile que se está pulsando en el momento
    private Tile pressedTile;
    //Cola de casillas pulsadas
    private Stack<Tile> tilePath;

    void Start()
    {
        firstTile = transform.GetChild(0).transform.position;

        startingTile.setToggle(true);
        pressedTile = startingTile;

        tilePath = new Stack<Tile>();
        tilePath.Push(pressedTile);
    }

    private bool insideLimits(float x, float y)
    {
        return x > (float)cols / -2.0f && x < (float)cols / 2.0f &&
            y > (float)rows / -2.0f && y < (float)rows / 2.0f;
    }

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

                //Índice del hijo pulsado
                int childIndex = rows * y + x;

                //Llamar al toggle del hijo
                Tile tile = transform.GetChild(childIndex).GetComponent<Tile>();
                if (tile != null && tile != pressedTile && isAdjacent(pressedTile, tile))
                {
                    //Desencolamos movimientos hasta que volvemos a ese punto
                    if (tile.isToggled())
                    {
                        //Desencolar
                        while (tilePath.Peek() != tile)
                        {
                            Tile t = tilePath.Pop();
                            t.setToggle(false);
                        }
                    }

                    //Marcamos un nuevo tile
                    else
                        tile.setToggle(true); //Que haga sus cosas
                    pressedTile = tile;
                    tilePath.Push(tile);
                }
            }
        }
    }
}
