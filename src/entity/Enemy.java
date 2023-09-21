package entity;
import main.Game;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.GRAVITY;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;
public abstract class Enemy extends Entity{
    protected int  enemyType;
    protected boolean firstUpdate=true;
    protected boolean inAir=false;
    protected int lvlIndex;
    protected int walkDir=LEFT;
    protected int tileY;
    protected float attackDistance=Game.TILES_SIZE;
    protected boolean active=true;
    protected boolean attackChecked;
    public Enemy(float x, float y, int width, int height, int enemyType, int lvlIndex) {
        super(x, y, width, height);
        this.enemyType=enemyType;
        this.lvlIndex=lvlIndex;
        this.walkSpeed=0.35f*Game.TILES_SCALE;

        maxHealth=GetMaxHealth(enemyType);
        currentHealth=maxHealth;
    }
    protected void firstUpdateCheck(int[][]lvlData){
        if(!IsEntityOnFloor(hitbox,lvlData,lvlIndex))
            inAir=true;
        firstUpdate=false;
    }
    protected void updateInAir(int[][] lvlData){
        if(CanMoveHere(hitbox.x, hitbox.y+airSpeed, hitbox.width, hitbox.height, lvlData,lvlIndex)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else{
            inAir=false;
            hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox,airSpeed);
            tileY=(int)(hitbox.y/Game.TILES_SIZE);
        }
    }
    protected void move(int[][]lvlData){
        float xSpeed=0;
        if(walkDir==LEFT)
            xSpeed-=walkSpeed;
        else
            xSpeed+=walkSpeed;
        if(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width,hitbox.height,lvlData,lvlIndex))
            if(IsFloor(hitbox, xSpeed,lvlData,lvlIndex)){
                hitbox.x+=xSpeed;
                return;
            }
        changeWalkDir();
    }
    protected void turnTowardsPlayer(Player player){
        if(player.hitbox.x> hitbox.x)
            walkDir=RIGHT;
        else
            walkDir=LEFT;
    }
    protected boolean canSeePlayer(int[][]lvlData, Player player){
        int playerTileY=(int)(player.getHitbox().y/Game.TILES_SIZE);
        if(playerTileY==tileY)
            if(isPlayerInRange(player)){
                if(IsSightClear(lvlData, hitbox,player.hitbox,tileY, lvlIndex)){
                    //System.out.println("can see");
                    return  true;
                }

            }
        return false;
    }



    protected boolean isPlayerInRange(Player player) {
        int absValue=(int)Math.abs(player.hitbox.x- hitbox.x);
        /*if(absValue<=attackDistance*5)
            System.out.println("is in range");*/
        return absValue<=attackDistance*5;
    }
    protected boolean isPlayerCloseForAttack(Player player){
        int absValue=(int)Math.abs(player.hitbox.x- hitbox.x);
        return absValue<=attackDistance;
    }

    protected void newState(int enemyState){
        this.state =enemyState;
        aniTick=0;
        aniIndex=0;
    }
    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick>= ANI_SPEED){
            aniTick=0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(enemyType, state)){
                aniIndex=0;
                switch (enemyType) {
                    case ZOMBIE :
                        if (state == JUMP_ZOMBIE)
                            state = IDLE_ZOMBIE;
                        else if (state == DIE_ZOMBIE)
                            active = false;
                    case PENGUIN:
                        if (state == SLIDE_PENGUIN)
                            state = IDLE_PENGUIN;
                        else if (state == DIE_PENGUIN)
                            active = false;
                }
            }
        }
    }
    public void hurt(int amount){
        currentHealth-=amount;
        if(currentHealth<=0)
            switch (enemyType) {
                case ZOMBIE :
                    newState(DIE_ZOMBIE);
                case PENGUIN:
                    newState(DIE_PENGUIN);
            }
    }
    protected void checkEnemyHit(Player player) {
        //System.out.println("check hit");
        if(hitbox.intersects(player.hitbox)) {
            player.changeHealth(-GetEnemyDmg(enemyType));
            //System.out.println("colision");
        }
        attackChecked=true;
    }

    protected void changeWalkDir() {
        if(walkDir==LEFT)
            walkDir=RIGHT;
        else
            walkDir=LEFT;
    }



    public boolean isActive(){
        return active;
    }
    public void resetEnemy(){
        hitbox.x=x;
        hitbox.y=y;
        firstUpdate=true;
        currentHealth=maxHealth;
        newState(IDLE_ZOMBIE);
        active=true;
        airSpeed=0;
    }
}
