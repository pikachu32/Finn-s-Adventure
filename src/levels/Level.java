package levels;

import Objects.Coin;
import entity.Penguin;
import entity.Zombie;
import main.Game;
import utilz.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.Game.TILES_DEFAULT_SIZE;
import static utilz.HelpMethods.*;

public class Level {
    private BufferedImage lvlDataImg,background, lvlAtlas;
    private BufferedImage []levelSprite;
    private int [][] lvlData;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private int tiles_nr;
    private static int lvlIndex=0;
    private Point playerSpawn;
    private ArrayList<Coin> coins;
    private ArrayList<Zombie> zombies;
    private ArrayList<Penguin> penguins;


    public Level(BufferedImage lvlDataImg, BufferedImage background, BufferedImage lvlAtlas){

        this.lvlDataImg =lvlDataImg;
        this.background=background;
        this.lvlAtlas=lvlAtlas;
        tiles_nr=lvlAtlas.getWidth()/TILES_DEFAULT_SIZE;
        importSprites();
        createLevelData();
        createEnemies();
        createCoins();
        calcLvlOffset();
        calcPlayerSpawn();
        lvlIndex++;
    }

    private void createCoins() {
        coins= HelpMethods.GetCoins(lvlDataImg);
    }

    private void calcPlayerSpawn() {
       playerSpawn =GetPlayerSpawn(lvlDataImg);
    }

    private void importSprites() {
        levelSprite=new BufferedImage[tiles_nr+1];
        for(int i=0;i<tiles_nr;i++)
            levelSprite[i]=lvlAtlas.getSubimage(i*TILES_DEFAULT_SIZE, 0, TILES_DEFAULT_SIZE,TILES_DEFAULT_SIZE);
    }

    private void calcLvlOffset() {
        lvlTilesWide= lvlDataImg.getWidth();
        maxTilesOffset=lvlTilesWide-Game.TILES_IN_WIDTH;
        maxLvlOffsetX=Game.TILES_SIZE*maxTilesOffset;
    }

    private void createEnemies() {
        zombies=GetZombies(lvlDataImg);
        penguins=GetPenguins(lvlDataImg);
        System.out.println("zoMbies size: "+ zombies.size());
        System.out.println("PENGUINS size: "+ penguins.size());
    }

    private void createLevelData() {
        lvlData=GetLevelData(lvlDataImg, lvlIndex);
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
    public int[][] getLevelData(){
        return lvlData;
    }
   public int getLvlOffset(){
        return maxLvlOffsetX;
    }
    public BufferedImage getBackground(){
        return background;
    }
    public BufferedImage[] getLevelSprite(){
        return levelSprite;
    }
    public Point getPlayerSpawn(){
        return playerSpawn;
    }

    public ArrayList<Zombie> getZombies() {
        return zombies;
    }

    public ArrayList<Penguin> getPenguins() {
        return penguins;
    }
    public ArrayList<Coin> getCoins(){return coins;}

}
