using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LevelManager : MonoBehaviour
{
    const int NUM_LEVELS = 100;
    const int NUM_COLS = 5;

    const int MARGIN = 200;

    [Tooltip("El prefab del Nivel")]
    public GameObject levelPrefab;

    // Start is called before the first frame update
    void Start()
    {
        //Número de nivel hasta el que queremos bloquear
        int levelNo = GameManager.instance.getLevelProgress(1);

        Transform rect = GetComponent<Transform>();

        int rows = NUM_LEVELS / NUM_COLS;
        int count = 0;

        for(int i = 0; i < rows; i++)
        {
            for (int j = 0; j < NUM_COLS; j++)
            {
                //Creamos el objeto
                GameObject o = Instantiate(levelPrefab, transform);
                o.name = (i * NUM_COLS + j + 1).ToString();
                o.transform.position = new Vector3(MARGIN + j * 175, 1600 - MARGIN - i * 175); //Posicion
                if (i * NUM_COLS + j <= levelNo)
                    o.transform.GetChild(1).GetComponent<UnityEngine.UI.Text>().text = (i * NUM_COLS + j + 1).ToString(); //Número que le corresponde
                else
                    o.transform.GetChild(2).gameObject.SetActive(true);
            }
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
