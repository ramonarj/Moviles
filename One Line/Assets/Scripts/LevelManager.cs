using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LevelManager : MonoBehaviour
{
    //Número de niveles de cada dificultad
    const int NUM_LEVELS = 100;
    const int NUM_COLS = 5;

    //Margenes
    const int MARGIN = 115;
    const int GAP = 125;

    [Tooltip("El prefab del Nivel")]
    public GameObject levelPrefab;
    [Tooltip("Sonido al pulsar el nivel")]
    public AudioClip clickSound;

    void Start()
    {
        //Número de nivel hasta el que queremos bloquear
        int difficulty = GameManager.instance.getActualDifficulty();
        int maxLevel = GameManager.instance.getLevelProgress(difficulty);

        Transform rect = GetComponent<Transform>();
        int rows = NUM_LEVELS / NUM_COLS;
        int count = 0;

        //Creamos los sprites de los niveles
        for(int i = 0; i < rows; i++)
        {
            for (int j = 0; j < NUM_COLS; j++)
            {
                //Creamos el objeto
                GameObject o = Instantiate(levelPrefab, transform);

                //Nombre y posición
                int c = count + 1; //NECESARIO PORQUE SI LE PASAS COUNT AL CALLBACK SE QUEDA CON EL VALOR DEL FINAL (100), 
                                    //Y SE INTENTA JUGAR AL NIVEL 101
                o.name = c.ToString();
                o.transform.position = new Vector3(MARGIN + j * GAP, 1000 - MARGIN - i * GAP); //Posicion

                //Depende de si está desbloqueado o no
                if (count <= maxLevel)
                {
                    //Número que le corresponde
                    o.transform.GetChild(1).GetComponent<UnityEngine.UI.Text>().text = c.ToString("000"); 
                    //Callbacks
                    o.GetComponent<Button>().onClick.AddListener(() => GameManager.instance.GoToLevel(c)); 
                    o.GetComponent<Button>().onClick.AddListener(() => GameManager.instance.playSound(clickSound));
                }
                else
                    o.transform.GetChild(2).gameObject.SetActive(true); //Activamos el candado

                count++;
            }
        }
    }
}
