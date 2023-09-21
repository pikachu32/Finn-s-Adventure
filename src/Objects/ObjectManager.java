package Objects;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstants.*;

public class ObjectManager {
    private Playing playing;
    private BufferedImage[] coinImg;
    private ArrayList<Coin> coins;
    public ObjectManager(Playing playing){
        this.playing=playing;
        loadImgs();
        //coins=new ArrayList<>();
    }
    public void loadObjects(Level newLevel){
        coins=newLevel.getCoins();
    }

    private void loadImgs() {
        BufferedImage coinSprite= LoadSave.GetSpriteAtlas(LoadSave.COLECTIBLE);
        coinImg=new BufferedImage[7];
        for(int i=0;i<coinImg.length;i++)
            coinImg[i]=coinSprite.getSubimage(23*i,0,23, 21);
    }
    public void update(){
        for(Coin c: coins)
            if(c.isActive())
                c.update();

    }
    public void draw(Graphics g, int xLvlOffset){
        drawCoins(g, xLvlOffset);
    }

    private void drawCoins(Graphics g, int xLvlOffset) {
        for(Coin c: coins)
            if(c.isActive()){
                int type=0;
                g.drawImage(coinImg[type+ c.getAniIndex()], (int)(c.getHitbox().x)-c.getxDrawOffset()-xLvlOffset, (int)(c.getHitbox().y-c.getyDrawOffset()), COIN_WIDTH,COIN_HEIGHT, null);
                //c.drawHitbox(g, xLvlOffset);
            }

    }
    public void checkObjectTouched(Rectangle2D.Float hitbox){
        for(Coin c: coins)
            if(c.isActive())
                if(hitbox.intersects(c.getHitbox())) {
                    c.setActive(false);
                    applyEffectyToPlayer(c);
                }
    }
    public void applyEffectyToPlayer(Coin c){
        playing.getPlayer().changePower(COIN_VALUE);
    }
    public void resetAllObjects(){
        for(Coin c: coins)
            c.reset();
    }
}
