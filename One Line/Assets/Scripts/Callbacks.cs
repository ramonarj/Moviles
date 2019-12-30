using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Este script existe para poder llamar a métodos del GameManager
 * sin depender de que esté añadido a un objeto en la escena.
 */
public class Callbacks : MonoBehaviour
{
    public void OnClickedLevel(int level)
    {
        GameManager.instance.GoToLevel(level);
    }

    public void OnClickedNextLevel()
    {
        GameManager.instance.NextLevel();
    }

    public void OnClickedHome()
    {
        GameManager.instance.GoToMenu();
    }

    public void OnClickedGamemode(int gamemode)
    {
        GameManager.instance.GoToSeleccion(gamemode);
    }

    public void OnClickedExit()
    {
        GameManager.instance.QuitApp();
    }

    public void OnClickedViewAd(int coinNo)
    {
        GameManager.instance.addCoins(coinNo);
    }

    public void OnClickedViewHint()
    {
        BoardManager.instance.showHint();
    }

    public void OnClickedRestart()
    {
        BoardManager.instance.restartLevel();
    }
}
