using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Monetization;


public class AdsManager : MonoBehaviour
{
    public static AdsManager instance;
    //Id del juego
    private string gameId = "12345";
    //Modo test por defecto
    private bool testMode = true;
    //Numero de veces que ha querido ver un anuncio
    private int rewardCout = 0;

    public int coinsRewarded = 0;

    //Ids de los tipos de anuncios
    string placementIdVideo = "video";
    string placementIdRewardedVideo = "rewardedVideo";

    void Awake()
    {
        // Singleton
        if (instance == null)
            instance = this;
        else
            Destroy(this.gameObject);
        Object.DontDestroyOnLoad(gameObject);
    }

    void Start()
    {
        Monetization.Initialize(gameId, testMode);
    }
    /*Anuncios normales*/
    public void ShowAd()
    {
        StartCoroutine(WaitForAd());
    }

    /*Anuncios con recompensa*/
    public void ShowRewardedAd()
    {
        StartCoroutine(WaitForAd(true));
    }

    IEnumerator WaitForAd(bool rewarded=false)
    {
        string placementId = rewarded ? placementIdRewardedVideo : placementIdVideo;

        while (!Monetization.IsReady(placementId))
        {
            yield return null;
        }

        ShowAdPlacementContent ad = null;
        ad = Monetization.GetPlacementContent(placementId) as ShowAdPlacementContent;

        /*En caso de que el anuncio no sea nulo*/
        if (ad != null)
        {
            /*Si es un anuncio de recompensa, comprobaremos si lo ha terminado de ver*/
            if (rewarded)
                ad.Show(AdFinished);
            else
                ad.Show();
        }
    }

    void AdFinished(ShowResult result)
    {
        /*Si ha terminado de ver el anuncio , le recompensamos dandole monedas*/
        if (result == ShowResult.Finished)
        {
            GameManager.instance.addCoins(coinsRewarded);
        }
    }
}
