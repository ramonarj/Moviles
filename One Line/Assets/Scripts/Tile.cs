using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Tile : MonoBehaviour
{
    [Tooltip("La imagen que tendra cuando no forme parte del camino")]
    public SpriteRenderer spriteUntoggled;

    [Tooltip("La imagen que tendra cuando forme parte del camino")]
    public SpriteRenderer spriteToggled;

    bool toggled = false;


    public void setToggle(bool t)
    {
        Debug.Log("e");
        toggled = t;
        spriteUntoggled.enabled = !toggled;
        spriteToggled.enabled = toggled;
    }

    public bool isToggled() { return toggled; }
}
