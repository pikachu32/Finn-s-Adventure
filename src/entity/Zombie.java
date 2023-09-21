package entity;
import main.Game;

import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.EnemyConstants.*;

public class Zombie extends Enemy{
    public Zombie(float x, float y, int lvlIndex) {
        super(x, y, ZOMBIE_WIDTH,ZOMBIE_HEIGHT, ZOMBIE, lvlIndex);
        initHitbox((int)(15),(int)(14));
    }
    private void updateBehaviour(int[][] lvlData, Player player){
        if(firstUpdate){
            firstUpdateCheck(lvlData);
        }
        if(inAir) {
            updateInAir(lvlData);
        }else{
            switch (state){
                case IDLE_ZOMBIE:
                    newState(RUN_ZOMBIE);
                    break;
                case RUN_ZOMBIE:
                    if(canSeePlayer(lvlData,player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player))
                            newState(JUMP_ZOMBIE);
                    }
                    move(lvlData);
                    break;
                case JUMP_ZOMBIE:
                    if(aniIndex==0)
                        attackChecked=false;
                    if(aniIndex==4 && !attackChecked)
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
