using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LevelManager : MonoBehaviour
{
    //Número de niveles de cada dificultad
    [Tooltip("El número de niveles seleccionables")]
    public int NUM_LEVELS;
    [Tooltip("El número de columnas a mostrar")]
    public int NUM_COLS;

    //Resolución por defecto del juego
    const float DEFAULT_RES = 16f / 9f;

    //Margenes
    int MARGIN;
    int GAP;

    [Tooltip("El prefab del Nivel")]
    public GameObject levelPrefab;
    [Tooltip("Sonido al pulsar el nivel")]
    public AudioClip clickSound;

    void Start()
    {
        //Número de nivel hasta el que queremos bloquear
        int difficulty = GameManager.Instance().getActualDifficulty();
        int maxLevel = GameManager.Instance().getLevelProgress(difficulty);

        int rows = NUM_LEVELS / NUM_COLS;
        int count = 0;
    
        float actualRes = (float)Screen.height / (float)Screen.width;

        //Márgenes
        MARGIN = Screen.width / 5;
        GAP = Screen.width / (NUM_COLS + 1);

        //Creamos los sprites de los niveles
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < NUM_COLS; j++)
            {
                //Creamos el objeto
                GameObject o = Instantiate(levelPrefab, transform);
                o.transform.localScale = new Vector3(DEFAULT_RES / actualRes, DEFAULT_RES / actualRes);

                //Nombre y posición
                int c = count + 1; //NECESARIO PORQUE SI LE PASAS COUNT AL CALLBACK SE QUEDA CON EL VALOR DEL FINAL (100), 
                                    //Y SE INTENTA JUGAR AL NIVEL 101
                o.name = c.ToString();
                o.transform.position = new Vector3(MARGIN + j * GAP, (4f * Screen.height / 5f) - MARGIN - i * GAP); //Posicion

                //Depende de si está desbloqueado o no
                if (count <= maxLevel)
                {
                    //Número que le corresponde
                    o.transform.GetChild(1).GetComponent<UnityEngine.UI.Text>().text = c.ToString("000"); 
                    //Callbacks
                    o.GetComponent<Button>().onClick.AddListener(() => GameManager.Instance().GoToLevel(c)); 
                    o.GetComponent<Button>().onClick.AddListener(() => GameManager.Instance().playSound(clickSound));
                }
                else
                    o.transform.GetChild(2).gameObject.SetActive(true); //Activamos el candado

                count++;
            }
        }
    }
}
