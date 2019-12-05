using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Tile : MonoBehaviour
{
    [Tooltip("La imagen que tendra cuando no forme parte del camino")]
    public SpriteRenderer spriteUntoggled;

    [Tooltip("La imagen que tendra cuando forme parte del camino")]
    public SpriteRenderer spriteToggled;

    /* De momento unicamente solo querremos setTouch y unTouch*/
    public void setTouch()
    {
        spriteUntoggled.enabled = false;
        spriteToggled.enabled = true;
    }

    public void setUnTouch()
    {
        spriteUntoggled.enabled = true;
        spriteToggled.enabled = false;
    }
}
