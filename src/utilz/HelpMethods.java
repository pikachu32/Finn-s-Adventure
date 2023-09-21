package utilz;

import Objects.Coin;
import entity.Penguin;
import entity.Zombie;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static inputs.KeyboardInputs.eevent;
import static utilz.Constants.EnemyConstants.PENGUIN;
import static utilz.Constants.EnemyConstants.ZOMBIE;
import static utilz.Constants.ObjectConstants.COIN;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][]lvlData, int lvlIndex){
        if(!IsSolid(x,y,lvlData, lvlIndex))
            if(!IsSolid(x+width,y+height,lvlData, lvlIndex))
                if(!IsSolid(x+width,y, lvlData,lvlIndex))
                    if(!IsSolid(x, y+height,lvlData,lvlIndex))
                        return true;
        return false;
    }
    private static boolean IsSolid(float x , float y, int [][]lvlData, int lvlIndex){
        int maxWidth = lvlData[0].length*Game.TILES_SIZE;
        if(x<0 || x>=maxWidth)
            return true;
        if(y<0)
            return true;
        if(y>=Game.GAME_HEIGHT-(Game.PLAYER_SCALE*19))
            return false;

        float xIndex=x/Game.TILES_SIZE;
        float yIndex=y/Game.TILES_SIZE;

        return IsTileSolid((int)xIndex, (int)yIndex, lvlData, lvlIndex);
    }
    public static boolean IsTileSolid(int xTile, int yTile, int [][]lvlData, int lvlIndex){
        int value=lvlData[(int)yTile][(int)xTile];

        if((value<81 && value>=0 && lvlIndex==0) || (value<55 && value>0 && lvlIndex==1) ||(value<40 && value>=0 && lvlIndex==2)){
            //System.out.println("is solid"+lvlIndex);
            return true;
        }
        return false;
    }
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){
        int currentTile=(int)(hitbox.x/Game.TILES_SIZE);
        if(xSpeed>0){
            //right
            int tileXPos=currentTile*Game.TILES_SIZE;
            int xOffset =(int)(Game.TILES_SIZE-hitbox.width);
            return tileXPos+xOffset -1;
        }else{
            //left
            return currentTile*Game.TILES_SIZE;
        }
    }
    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
        int currentTile=(int)(hitbox.y/Game.TILES_SIZE);
        if(airSpeed>0){
            //falling
            int tileYPos=currentTile*Game.TILES_SIZE;
            int yOffset=(int)(Game.TILES_SIZE-hitbox.height);
            return tileYPos+yOffset-1;
        }else{
            //jumping
            return currentTile*Game.TILES_SIZE;
        }
    }
    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][]lvlData ,int lvlIndex){
        //check the pixel below bottom left and bottom right
        if(eevent)
            eevent = false;
        if(!IsSolid(hitbox.x, hitbox.y+ hitbox.height+1,lvlData, lvlIndex))
            if(!IsSolid(hitbox.x+ hitbox.width, hitbox.y+ hitbox.height+1,lvlData,lvlIndex))
                return false;
        return true;
    }
    public static int[][] GetLevelData(BufferedImage img, int lvlIndex){

        //BufferedImage img=GetSpriteAtlas(LEVEL_ONE_DATA);
        int[][] lvlData=new int[img.getHeight()][img.getWidth()];//[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];

        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getRed();
                if((value<0 || value>=104)&&lvlIndex==0)
                    value=104;
                else if((value<0 || value>=94) && lvlIndex==1)
                    value=58;
                lvlData[j][i]=value;
            }
        /*for(int i=0;i<20;i++){
            for(int j=0;j<56;j++)
                System.out.print(lvlData[i][j]+" ");
            System.out.print("\n");
        }*/

        return lvlData;
    }
    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][]lvlData, int lvlIndex){
        if(xSpeed>0)
            return IsSolid(hitbox.x+xSpeed+hitbox.width, hitbox.y+hitbox.height+1, lvlData, lvlIndex);
        return IsSolid(hitbox.x+xSpeed, hitbox.y+hitbox.height+1, lvlData, lvlIndex);
    }
    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData, int lvlIndex) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData, lvlIndex))
                return false;
            if (!IsTileSolid(xStart + i, y + 1, lvlData, lvlIndex))
                return false;
        }
        return true;
    }
    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile, int lvlIndex) {
        int firstXTile=(int)(firstHitbox.x/Game.TILES_SIZE);
        int secondXTile=(int)(secondHitbox.x/Game.TILES_SIZE);
        if(firstXTile>secondXTile){
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData, lvlIndex);

        }else{
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData,lvlIndex);
        }
    }
    public static Point GetPlayerSpawn(BufferedImage img){
        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getGreen();
                if(value == 3)
                    return new Point(i*Game.TILES_SIZE,j*Game.TILES_SIZE);
            }
        return new Point(1*Game.TILES_SIZE,1*Game.TILES_SIZE);
    }
    public static ArrayList<Zombie> GetZombies(BufferedImage img){
        ArrayList<Zombie> list=new ArrayList<>();

        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getGreen();
                if(value == ZOMBIE)
                    list.add(new Zombie(i* Game.TILES_SIZE,j*Game.TILES_SIZE, 1));
            }
        return list;
    }
    public static ArrayList<Penguin> GetPenguins(BufferedImage img){
        ArrayList<Penguin> list=new ArrayList<>();

        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getGreen();
                if(value == PENGUIN)
                    list.add(new Penguin(i* Game.TILES_SIZE,j*Game.TILES_SIZE, 2));
            }
        return list;
    }
    public static ArrayList<Coin> GetCoins(BufferedImage img){
        ArrayList<Coin> list=new ArrayList<>();

        for(int j=0;j<img.getHeight();j++)
            for(int i=0;i<img.getWidth();i++){
                Color color=new Color(img.getRGB(i,j));
                int value=color.getBlue();
                if(value == COIN)
                    list.add(new Coin(i* Game.TILES_SIZE,j*Game.TILES_SIZE, COIN));
            }
        return list;
    }
}
