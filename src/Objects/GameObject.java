package Objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static main.Game.PLAYER_SCALE;
import static main.Game.TILES_SCALE;
import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.ObjectConstants.*;

public class GameObject {
    protected int x, y;
    protected Rectangle2D.Float hitbox;
    protected int objectType;
    protected boolean doAnimation, active=true;
    protected int aniTick, aniIndex, xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objectType){
        this.x=x;
        this.y=y;
        this.objectType=objectType;
    }
    protected void initHitbox( int width, int height) {
        hitbox=new Rectangle2D.Float(x,y,(int)(width* TILES_SCALE),(int)( height* TILES_SCALE));
    }
    public void drawHitbox(Graphics g, int xLvlOffset){
        //for debugging
        g.setColor(Color.pink);
        g.drawRect((int)hitbox.x-xLvlOffset,(int)hitbox.y,(int)hitbox.width,(int)hitbox.height);
    }
    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick>= ANI_SPEED){
            aniTick=0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(objectType)){
                aniIndex=0;
            }
        }
    }
    public void reset(){
        aniIndex=0;
        aniTick=0;
        active=true;
        doAnimation=true;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getObjectType() {
        return objectType;
    }

    public boolean isDoAnimation() {
        return doAnimation;
    }

    public boolean isActive() {
        return active;
    }

    public int getAniTick() {
        return aniTick;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public void setHitbox(Rectangle2D.Float hitbox) {
        this.hitbox = hitbox;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public void setDoAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAniTick(int aniTick) {
        this.aniTick = aniTick;
    }

    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
    }

    public void setxDrawOffset(int xDrawOffset) {
        this.xDrawOffset = xDrawOffset;
    }

    public void setyDrawOffset(int yDrawOffset) {
        this.yDrawOffset = yDrawOffset;
    }
}
