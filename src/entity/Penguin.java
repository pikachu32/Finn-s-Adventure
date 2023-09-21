package entity;
import main.Game;

import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;

public class Penguin extends Enemy{
    public Penguin(float x, float y, int lvlIndex) {
        super(x, y, PENGUIN_WIDTH,PENGUIN_HEIGHT, PENGUIN, lvlIndex);
        initHitbox((int)(9),(int)(12));
    }
    private void updateBehaviour(int[][] lvlData, Player player){
        if(firstUpdate){
            firstUpdateCheck(lvlData);
        }
        if(inAir) {
            updateInAir(lvlData);
        }else{
            switch (state){
                case IDLE_PENGUIN:
                    newState(RUN_PENGUIN);
                    break;
                case RUN_PENGUIN:
                    if(canSeePlayer(lvlData,player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(SLIDE_PENGUIN);
                    }
                    move(lvlData);
                    break;
                case SLIDE_PENGUIN:
                    if(aniIndex==0)
                        attackChecked=false;
                    if(aniIndex==2 && !attackChecked)
                        checkEnemyHit(player);
                    move(lvlData);
            }
        }
    }
    public int flipX(){
        if(walkDir==RIGHT)
            return width;
        else return 0;
    }
    public int flipW(){
        if(walkDir==RIGHT)
            return -1;
        else return 1;
    }
    public void update(int [][]lvlData, Player player){
        updateBehaviour(lvlData, player);
        updateAnimationTick();
    }
}
