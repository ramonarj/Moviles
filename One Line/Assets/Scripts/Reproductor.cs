using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Reproductor : MonoBehaviour
{
    void Awake()
    {
        Object.DontDestroyOnLoad(gameObject);
    }

}
