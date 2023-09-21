package levels;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static main.Game.*;
public class LevelManager {
    private Game game;
    private ArrayList<Level> levels;
    public int lvlIndex=0;
    public LevelManager(Game game){
        this.game=game;
        levels=new ArrayList<Level>();
        buildAllLevels();
    }
    public void loadNextLevel(){
        lvlIndex++;
        if(lvlIndex>=levels.size()){
            lvlIndex=0;
            System.out.println("no more levels");
            Gamestate.state=Gamestate.MENU;

        }
        game.getPlaying().getPlayer().setLvlIndex(lvlIndex);
        Level newLevel=levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }
    private void buildAllLevels() {
        BufferedImage[] allLevelsData=LoadSave.GetAllLevelsData();
        BufferedImage[] allLevelsBackground=LoadSave.GetAllLevelsBackground();
        BufferedImage[] allLevelsAtlas=LoadSave.GetAllLevelsAtlas();
        levels.add(new Level(allLevelsData[0],allLevelsBackground[0],allLevelsAtlas[0]));
        levels.add(new Level(allLevelsData[1],allLevelsBackground[1],allLevelsAtlas[1]));
        levels.add(new Level(allLevelsData[2],allLevelsBackground[2],allLevelsAtlas[2]));
        System.out.println("levels size: "+levels.size());
    }
    public void draw(Graphics g, int lvlOffset){
        g.drawImage(levels.get(lvlIndex).getBackground(),0,0,null);
        for(int j=0;j<Game.TILES_IN_HEIGHT;j++)
            for(int i=0;i<levels.get(lvlIndex).getLevelData()[0].length;i++){
                int index=levels.get(lvlIndex).getSpriteIndex(i,j);
                if(lvlIndex == 1 && index == 104)
                    index = 58;
                else if(lvlIndex == 2 && index == 255)
                    index = 51;
                g.drawImage(levels.get(lvlIndex).getLevelSprite()[index],i*TILES_SIZE-lvlOffset, j*TILES_SIZE,TILES_SIZE,TILES_SIZE,null);
            }

    }
    public void update(){

    }
    public Level getCurrentLevel(){
        return levels.get(lvlIndex);
    }
    public int getAmountOfLevels(){
        return levels.size();
    }

    public int getLvlIndex() {
        return lvlIndex;
    }
}
