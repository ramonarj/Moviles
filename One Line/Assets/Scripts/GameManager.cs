﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameManager : MonoBehaviour
{
    public static GameManager instance;

    void Awake()
    {
        // Singleton
        if (instance == null)
            instance = this;
        else
            Destroy(this.gameObject);
    }
}
