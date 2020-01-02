using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System.Text;

[System.Serializable]
public class SaveDataManager : MonoBehaviour
{
    public static SaveDataManager instance;
    private string jsonSavePath;

    /*Atributos que serializaremos*/
    [SerializeField] private List<int>levels;
    [SerializeField] private int coins;
    [SerializeField] private int premium;
    [SerializeField] private int challenge;
    [SerializeField] private string hash;

    void Awake()
    {
        // Singleton
        if (instance == null)
            instance = this;
        else
            Destroy(this.gameObject);
        Object.DontDestroyOnLoad(gameObject);
    }

    /*Configuramos el path segun donde se encuentre la app*/
    void Start()
    {
        jsonSavePath = Application.persistentDataPath + "/save.json";   
    }

    /*Serializamos la clase*/
    public void save(List<int> levels_,int coins_,int premium_,int challenge_)
    {
        Debug.Log(jsonSavePath);
        levels = levels_;
        coins = coins_;
        premium = premium_;
        challenge = challenge_;

        //Cremos el hash concantenando el contenido de la clase y anadiedo una sal al final
        hash = createHash(System.Convert.ToString(concatenateLevels(levels) + coins + premium + challenge+createSalt()));
        //Rellenamos el json y lo guardamos
        string jsonData = JsonUtility.ToJson(this, true);
        File.WriteAllText(jsonSavePath, jsonData);
        
    }

    public static string createHash(string str)
    {
        System.Security.Cryptography.SHA256Managed crypt = new System.Security.Cryptography.SHA256Managed();
        System.Text.StringBuilder hash = new System.Text.StringBuilder();
        byte[] crypto = crypt.ComputeHash(Encoding.UTF8.GetBytes(str), 0, Encoding.UTF8.GetByteCount(str));
        foreach (byte bit in crypto)
        {
            hash.Append(bit.ToString("x2"));
        }
        return hash.ToString().ToLower();
    }
    public string createSalt()
    {
        const string glyphs = "abcdefghijklmnopqrstuvwxyz0123456789";
        int charAmount = Random.Range(2, 10);
        string salt = "";
        for (int i = 0; i < charAmount; i++)
        {
            salt += glyphs[Random.Range(0, glyphs.Length)];
        }
        return salt;
    }

    private string concatenateLevels(List<int>levelss)
    {
        string s = "";
        for (int i=0;i<levelss.Count;i++)
        {
            s += levelss[i];
        }
        return s;
    }
}
