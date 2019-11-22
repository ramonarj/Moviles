package com.example.logic;

class GameManager {
    private static GameManager ourInstance = null;

    /**
     * crea la instancia del GameManageer que sera posteriormente usada
     * hasta que acabe el juego
     */
    public static void initInstance()
    {
        ourInstance = new GameManager();
    }

    /**
     *
     * @return la instancia del GameManager
     */
    static GameManager getInstance() {
        return ourInstance;
    }

    private GameManager()
    {
    }

    public void setBackGroundNo(int backGroundNo){this.backGroundNo = backGroundNo;}
    public void setLateralColor(int lateralColor){this.lateralColor = lateralColor;}
    public void setBarsWidth(int barsWidth){this.barsWidth = barsWidth;}


    public int getBackGroundNo(){return backGroundNo;}
    public int getLateralColor(){return lateralColor;}
    public int getBarsWidth(){return barsWidth;}

    private int lateralColor;
    private int backGroundNo;
    private int barsWidth;

}
