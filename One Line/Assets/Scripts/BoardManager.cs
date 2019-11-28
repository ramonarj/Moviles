using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BoardManager : MonoBehaviour
{
    [Tooltip("Número de filas del tablero")]
    public int rows;
    [Tooltip("Número de columnas del tablero")]
    public int cols;

    private Vector3 firstTile;

    void Start()
    {
        firstTile = transform.GetChild(0).transform.position;
    }


    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            //Ver que indice es
            Vector3 mousePos = FindObjectOfType<Camera>().ScreenToWorldPoint(Input.mousePosition);
            mousePos.x = Mathf.RoundToInt(mousePos.x);
            mousePos.y = Mathf.RoundToInt(mousePos.y);
            int difY = (int)Mathf.Abs(mousePos.y - firstTile.y);
            int difX = (int)Mathf.Abs(mousePos.x - firstTile.x);
               

            if (difX < cols && difY < rows) // comprbar que no estamos por arriba a la izuerda de la prmera
            {
                int childIndex = rows * (int)Mathf.Abs(mousePos.y - firstTile.y) + (int)Mathf.Abs(mousePos.x - firstTile.x);

                //Llamar al hijo
                Tile tile = transform.GetChild(childIndex).GetComponent<Tile>();
                if (tile != null)
                    tile.setToggle(!tile.isToggled());

                ////Comprobar que no nos salimos de los índices
                //if (childIndex < transform.childCount)
                //{

                //}
            }
        }
    }
}
