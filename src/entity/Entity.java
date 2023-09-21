package entity;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static main.Game.PLAYER_SCALE;

public abstract class Entity {
    protected float x, y;
    protected int width=32, height=32;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex;
    protected int state;
    protected float airSpeed=0f;
    protected boolean inAir=false;
    protected int maxHealth;
    protected int currentHealth;
    protected float walkSpeed;
    public Entity(float x, float y, int width, int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    protected void drawHitbox(Graphics g, int xLvlOffset){
        //for debugging
        g.setColor(Color.pink);
        g.drawRect((int)hitbox.x-xLvlOffset,(int)hitbox.y,(int)hitbox.width,(int)hitbox.height);
    }
    protected void initHitbox( int width, int height) {
        hitbox=new Rectangle2D.Float(x,y,(int)(width* PLAYER_SCALE),(int)( height* PLAYER_SCALE));
    }
    /*protected void updateHitbox(){
        hitbox.x=(int)x;
        hitbox.y=(int)y;
    }*/
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

    public float getX() {return x;}
    public float getY() {return y;}
    public int getState(){
        return state;
    }
    public int getAniIndex() {
        return aniIndex;
    }
}
