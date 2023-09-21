package Objects;

import main.Game;

public class Coin extends GameObject{
    public Coin(int x, int y, int objectType) {
        super(x, y, objectType);
        doAnimation=true;
        initHitbox(11,8);
        xDrawOffset=(int)(6* Game.TILES_SCALE);
        yDrawOffset=(int)(8*Game.TILES_SCALE);
    }
    public void update(){
        updateAnimationTick();
    }
}
