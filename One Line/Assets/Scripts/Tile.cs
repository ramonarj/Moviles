using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Utils;

public class Tile : MonoBehaviour
{
    [Tooltip("La imagen que tendra cuando no forme parte del camino")]
    public SpriteRenderer spriteUntoggled;

    [Tooltip("La imagen que tendra cuando forme parte del camino")]
    public SpriteRenderer spriteToggled;

    [Tooltip("Líneas discontinuas en las 4 direcciones que forman un camino más visual")]
    public SpriteRenderer upPath;
    public SpriteRenderer downPath;
    public SpriteRenderer leftPath;
    public SpriteRenderer rightPath;

    /* De momento unicamente solo querremos setTouch y unTouch*/
    public void setTouch(Direction d) //d = dirección de la que se viene
    {
        //Sprites del camino (depende de la dirección de la que se venga)
        switch (d)
        {
            case Direction.Up:
                upPath.enabled = true;
                break;
            case Direction.Down:
                downPath.enabled = true;
                break;
            case Direction.Left:
                leftPath.enabled = true;
                break;
            case Direction.Right:
                rightPath.enabled = true;
                break;
            default:
                break;
        }
        //Swap
        spriteUntoggled.enabled = false;
        spriteToggled.enabled = true;
    }

    public void setUnTouch()
    {
        //Swap
        spriteUntoggled.enabled = true;
        spriteToggled.enabled = false;

        //Sprites del camino
        upPath.enabled = false;
        downPath.enabled = false;
        leftPath.enabled = false;
        rightPath.enabled = false;
    }
}
