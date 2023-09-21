package entity;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] zombieArray;
    private BufferedImage[] penguinArray;
    private ArrayList<Zombie> zombies=new ArrayList<>();
    private ArrayList<Penguin> penguins=new ArrayList<>();
    private int lvlIndex;
    public EnemyManager(Playing playing, int lvlIndex){
        this.playing=playing;
        this.lvlIndex=lvlIndex;
        loadEnemyImgs();
        //addEnemies();
    }
    public void loadEnemies(Level level){
        zombies=level.getZombies();
        penguins=level.getPenguins();
    }


    public void update(int[][]lvlData, Player player){
        boolean isAnyActive=false;
        for(Zombie z : zombies)
            if(z.isActive()) {
                z.update(lvlData, player);
                isAnyActive = true;
            }
        for(Penguin p : penguins)
            if(p.isActive()) {
                p.update(lvlData, player);
                isAnyActive = true;
            }
        if(!isAnyActive)
            playing.setLvlCompleted(true);
    }
    public void draw(Graphics g, int xLvlOffset){
        drawZombies(g, xLvlOffset);
        drawPenguins(g,xLvlOffset);
    }

    private void drawZombies(Graphics g, int xLvlOffset) {
        for(Zombie z : zombies)
            if(z.isActive()) {
                g.drawImage(zombieArray[z.getState() + z.getAniIndex()], (int) z.getHitbox().x - xLvlOffset-ZOMBIE_DRAWOFFSET_X+z.flipX(), (int) z.getHitbox().y-ZOMBIE_DRAWOFFSET_Y, ZOMBIE_WIDTH*z.flipW(), ZOMBIE_HEIGHT, null);
                //z.drawHitbox(g,xLvlOffset);
            }
    }
    private void drawPenguins(Graphics g, int xLvlOffset) {
        for(Penguin p: penguins)
            if(p.isActive()) {
                g.drawImage(penguinArray[p.getState() + p.getAniIndex()], (int) p.getHitbox().x - xLvlOffset-PENGUIN_DRAWOFFSET_X+p.flipX(), (int) p.getHitbox().y-PENGUIN_DRAWOFFSET_Y, PENGUIN_WIDTH*p.flipW(), PENGUIN_HEIGHT, null);
                //p.drawHitbox(g,xLvlOffset);
            }
    }

    private void loadEnemyImgs() {
        zombieArray=new BufferedImage[25];
        penguinArray=new BufferedImage[25];
        BufferedImage temp= LoadSave.GetSpriteAtlas(LoadSave.ZOMBIE_SPRITE);
        for(int i=0;i<zombieArray.length;i++)
            zombieArray[i]=temp.getSubimage(i*ZOMBIE_WIDTH_DEFAULT, 0, ZOMBIE_WIDTH_DEFAULT,ZOMBIE_HEIGHT_DEFAULT);
        temp= LoadSave.GetSpriteAtlas(LoadSave.PENGUIN_SPRITE);
        for(int i=0;i<penguinArray.length;i++)
            penguinArray[i]=temp.getSubimage(i*PENGUIN_WIDTH_DEFAULT, 0, PENGUIN_WIDTH_DEFAULT,PENGUIN_HEIGHT_DEFAULT);
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(Zombie z: zombies)
            if(z.isActive())
                if(attackBox.intersects(z.getHitbox())){
                    z.hurt(10);
                    return;
                }
        for(Penguin p:penguins)
            if(p.isActive())
                if(attackBox.intersects(p.getHitbox())){
                    p.hurt(10);
                    return;
                }
    }
    public void resetAllEnemyes(){
        for(Zombie z:zombies)
            z.resetEnemy();
        for(Penguin p:penguins)
            p.resetEnemy();
    }

}
