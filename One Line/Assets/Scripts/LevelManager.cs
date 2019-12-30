﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LevelManager : MonoBehaviour
{
    //Número de niveles de cada dificultad
    const int NUM_LEVELS = 100;
    const int NUM_COLS = 5;

    //Margenes
    const int MARGIN = 200;

    [Tooltip("El prefab del Nivel")]
    public GameObject levelPrefab;


    void Start()
    {
        //Número de nivel hasta el que queremos bloquear
        int levelNo = GameManager.instance.getLevelProgress(1);

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

                //Nombre y callback
                int c = count + 1; //NECESARIO PORQUE SI LE PASAS COUNT AL CALLBACK SE QUEDA CON EL VALOR DEL FINAL (100), 
                                    //Y SE INTENTA JUGAR AL NIVEL 101
                o.name = c.ToString();
                o.GetComponent<Button>().onClick.AddListener(() => GameManager.instance.GoToLevel(c));

                //Lo colocamos y lo ponemos oculto/nos
                o.transform.position = new Vector3(MARGIN + j * 175, 1600 - MARGIN - i * 175); //Posicion
                if (count <= levelNo)
                    o.transform.GetChild(1).GetComponent<UnityEngine.UI.Text>().text = c.ToString(); //Número que le corresponde
                else
                    o.transform.GetChild(2).gameObject.SetActive(true); //Candado

                count++;
            }
        }
    }
}