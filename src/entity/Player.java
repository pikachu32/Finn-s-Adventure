package entity;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static main.Game.PLAYER_SCALE;
import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.GRAVITY;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity{
    private BufferedImage[] sprite;
    private boolean moving=false, attacking=false;
    private boolean left,right, jump;
    private int[][]lvlData;
    private float xDrawOffset=6* PLAYER_SCALE;
    private float yDrawOffset=7* PLAYER_SCALE;

    //Jumping /Gravity
    private float jumpSpeed =-2.25f * PLAYER_SCALE;
    private float fallSpeedAfterCollision=0.5f* PLAYER_SCALE;

    //status bar ui
    private BufferedImage statusBarImg;
    private int statusBarWidth=(int)(192*Game.TILES_SCALE);
    private int statusBarHeight=(int)(58*Game.TILES_SCALE);
    private int statusBarX=(int)(10*Game.TILES_SCALE);
    private int statusBarY =(int)(10*Game.TILES_SCALE);
    private int healthBarWidth=(int)(150*Game.TILES_SCALE);
    private int healthBarHeight=(int)(4*Game.TILES_SCALE);
    private int healthBarXStart=(int)(34*Game.TILES_SCALE);
    private int healthBarYStart=(int)(14*Game.TILES_SCALE);


    private int healthWidth=healthBarWidth;
    private int score=0;
    private int currentLvlScore=0;

    //atackbox
    private Rectangle2D.Float attackBox;

    private int flipX=0;
    private int flipW=1;
    private int lvlIndex;
    private boolean attackChecked=false;
    private Playing playing;

    public Player(float x, float y, int width, int height, int lvlIndex, Playing playing) {
        super(x, y, width,height);
        this.lvlIndex=lvlIndex;
        this.playing=playing;
        this.state=IDLE;
        this.maxHealth=100;
        this.walkSpeed=1.0f*Game.TILES_SCALE;
        currentHealth=maxHealth;
        loadAnimations();
        initHitbox( (int)(14), (int)(19));
        System.out.print(hitbox.width+"  "+hitbox.height);
        initAttackBox();
    }
    public void setSpawn(Point spawn){
        this.x=spawn.x;
        this.y=spawn.y;
        hitbox.x=x;
        hitbox.y=y;
    }

    private void initAttackBox() {
        attackBox=new Rectangle2D.Float(x,y,(int)(15*Game.TILES_SCALE), (int)(20*Game.TILES_SCALE));
    }

    public void update(){
        updateHealthBar();
        if(currentHealth<=0){
            playing.setGameOver(true);
            return;
        }
        updateAttackBox();
        updateHitBox();
        updatePosition();
        if(moving)
            checkObjectTouched(hitbox);
        if(attacking)
            checkAttack();
        updateAnimationTick();
        setAnimation();
    }

    private void checkObjectTouched(Rectangle2D.Float hitbox) {
        playing.checkObjectTouched(hitbox);
    }

    private void checkAttack() {
        if(attackChecked || aniIndex!=1)
            return;
        attackChecked=true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if(right){
            attackBox.x= hitbox.x+hitbox.width+(int)(Game.TILES_SCALE*5);
        }else if(left){
            attackBox.x= hitbox.x-hitbox.width;//-(int)(Game.TILES_SCALE*5);
        }
        attackBox.y=hitbox.y+Game.TILES_SCALE*10;
    }
    private void updateHitBox(){
        if(left){
            xDrawOffset=12*PLAYER_SCALE;
        }else if(right){
            xDrawOffset=6*PLAYER_SCALE;
        }

    }

    private void updateHealthBar() {
        healthWidth=(int)((currentHealth/(float)maxHealth)*healthBarWidth);
    }

    public void render(Graphics g, int lvlOffset){
        g.drawImage(sprite[state +aniIndex],(int)(hitbox.x-xDrawOffset)-lvlOffset+flipX, (int)(hitbox.y-yDrawOffset),width*flipW,height, null);
        //drawHitbox(g, lvlOffset);
        //drawAttackBox(g,lvlOffset);
        drawUI(g);
    }

    private void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.pink);
        g.drawRect((int)attackBox.x-lvlOffsetX,(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg,statusBarX, statusBarY,statusBarWidth,statusBarHeight, null);
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart+statusBarX,healthBarYStart+statusBarY,healthWidth,healthBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick>= ANI_SPEED){
            aniTick=0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(state)-1) {
                aniIndex = 0;
                attacking = false;
                attackChecked=false;
            }
        }
    }
    private void setAnimation(){
        int startAni= state;
        if (moving){
            state =RUNNING;
        }
        else {
            state = IDLE;
        }
        if(inAir){
            state =JUMP;
        }
        if(attacking)
            state =ATTACK;
        if(currentHealth<=0)
            state =DIE;
        if (startAni!= state){
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick=0;
        aniIndex=0;
    }
    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        sprite=new BufferedImage[28];
        for(int i=0;i<sprite.length;i++)
            sprite[i]=img.getSubimage(i*32, 0, 32,32);
        statusBarImg=LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void updatePosition(){
        moving=false;
        if(jump){
            jump();
        }
        if (!inAir) {
            if((!left && !right)||(left && right))
                return;
        }
        float xSpeed=0;
        if(left) {
            xSpeed -= walkSpeed;
            flipX=width;
            flipW=-1;
        }
        if(right) {
            xSpeed+= walkSpeed;
            flipX=0;
            flipW=1;
        }
        if(!inAir){
            if(!IsEntityOnFloor(hitbox,lvlData, lvlIndex)){
                inAir=true;
            }
        }

        if(inAir){
            if(CanMoveHere(hitbox.x, hitbox.y +airSpeed, hitbox.width,hitbox.height, lvlData,lvlIndex)){
                hitbox.y+=airSpeed;
                airSpeed+= GRAVITY;
                updateXPos(xSpeed);
            }else{
                hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox,airSpeed);
                if(airSpeed>0){
                    resetInAir();
                }else{
                    airSpeed=fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        }else{
            updateXPos(xSpeed);
        }
        moving=true;

    }

    private void jump() {
        if(inAir)
            return;
        else{
            inAir=true;
            airSpeed=jumpSpeed;
        }
    }

    private void updateXPos(float xSpeed) {
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y, hitbox.width, hitbox.height, lvlData,lvlIndex)) {
            hitbox.x += xSpeed;
        }
        else{
            hitbox.x=GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }
    public void changeHealth(int value){
        currentHealth+=value;
        if(currentHealth<=0){
            //gameover
        }else if(currentHealth>=maxHealth)
            currentHealth=maxHealth;
    }
    public void changePower(int value){
        //System.out.println("power");
        currentLvlScore++;
    }

    private void resetInAir() {
        inAir=false;
        airSpeed=0;
    }
    public void resetDirBooleans(){
        left=false;
        right=false;
    }
    public boolean isLeft() {return left;}
    public boolean isRight() {return right;}

    public void setLeft(boolean left) {this.left = left;}
    public void setRight(boolean right) {this.right = right;}
    public void setJump(boolean jump) {
        this.jump = jump;
    }
    public void setAttack(boolean attacking) {this.attacking=attacking;}


    public void loadLvlData(int[][] lvlData){
        this.lvlData=lvlData;
        /*System.out.println("player.lvlData:");
        for(int i=0;i<20;i++){
            for(int j=0;j<lvlData[0].length;j++)
                System.out.print(lvlData[i][j]+" ");
            System.out.print("\n");
        }*/
        if(!IsEntityOnFloor(hitbox,lvlData,lvlIndex)){
            inAir=true;
        }
    }
    public void resetAll(){
        resetDirBooleans();;
        inAir=false;
        attacking=false;
        moving=false;
        state =IDLE;
        currentHealth=maxHealth;
        currentLvlScore=0;

        hitbox.x=x;
        hitbox.y=y;

        if(!IsEntityOnFloor(hitbox,lvlData,lvlIndex))
            inAir=true;
    }
    public void setLvlIndex(int lvlIndex){
        this.lvlIndex=lvlIndex;
    }
    public int getScore(){return score;}
    public int getCurrentLvlScore(){return currentLvlScore;}

    public void setScore(int score) {
        this.score=score;
    }

    public void setCurrentLvlScore(int currentLvlScore) {
        this.currentLvlScore=currentLvlScore;
    }
}
