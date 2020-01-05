using System.Collections;
using System.Collections.Generic;
using UnityEngine;

/*
 * Este script existe para poder llamar a métodos del GameManager desde botones de la escena
 * sin depender de que esté añadido a un objeto/no.
 */
public class Callbacks : MonoBehaviour
{
    //Pulsación en el cuadradito del nivel
    public void OnClickedLevel(int level)
    {
        GameManager.instance.GoToLevel(level);
    }

    //Pulsación en el play (en el pop-up al completar un nivel)
    public void OnClickedNextLevel()
    {
        GameManager.instance.NextLevel();
        AdsManager.instance.ShowAd();
    }

    //Pulsación en home (en el pop-up al completar un nivel)
    public void OnClickedHome()
    {
        GameManager.instance.GoToMenu();
    }

    //Pulsación de un modo de juego (1-5)
    public void OnClickedGamemode(int gamemode)
    {
        GameManager.instance.GoToSeleccion(gamemode);
    }

    //Pulsación en home (para salir del juego en el menú)
    public void OnClickedExit()
    {
        GameManager.instance.QuitApp();
    }

    //Pulsación en el botón de los anuncios
    public void OnClickedViewAd()
    {
        AdsManager.instance.ShowRewardedAd();
    }

    //Pulsación en la pista (dentro del nivel)
    public void OnClickedViewHint(int coins)
    {
        if (GameManager.instance.getCoins() >= coins)
        {
            BoardManager.instance.showHint();
            GameManager.instance.addCoins(-coins);
        }
    }

    //Pulsación en el botón de reiniciar nivel
    public void OnClickedRestart()
    {
        BoardManager.instance.restartLevel();
    }

    //Pulsacion en el boton de challenge
    public void OnClickChallenge()
    {
        GameManager.instance.ShowChallengePanel();
    }

    //Pulsacion de comprar el modo challenge 
    public void OnClickpayChallenge()
    {
        GameManager.instance.playChallenge();
    }

    //Reproducción de un clip al pulsar
    public void OnSoundPlayed(AudioClip clip)
    {
        GameManager.instance.playSound(clip);
    }

    //Pulsación en el botón de volver (dentro del nivel)
    public void OnClickedBack()
    {
        if (!GameManager.instance.getChallenge())
            GameManager.instance.GoToSeleccion();
        else GameManager.instance.GoToMenu();
    }
}
