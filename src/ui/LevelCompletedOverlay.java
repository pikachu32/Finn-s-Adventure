package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.UrmButtons.URM_SIZE;

public class LevelCompletedOverlay {
    private Playing playing;
    private UrmButton menu, next;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;
    public LevelCompletedOverlay(Playing playing){
        this.playing=playing;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX=(int)(552*Game.TILES_SCALE);
        int nextX=(int)(667*Game.TILES_SCALE);
        int y=(int)(195*Game.TILES_SCALE);
        next=new UrmButton(nextX,y,URM_SIZE,URM_SIZE,0);
        menu=new UrmButton(menuX,y,URM_SIZE,URM_SIZE,2);
    }

    private void initImg() {
        img= LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMG);
        bgW=(int)(img.getWidth()* Game.TILES_SCALE);
        bgH=(int)(img.getHeight()* Game.TILES_SCALE);
        bgX=Game.GAME_WIDTH/2-bgW/2;
        bgY=(int)(75*Game.TILES_SCALE);
    }
    public void update(){
        next.update();
        menu.update();

    }
    public void draw(Graphics g){
        g.drawImage(img, bgX,bgY,bgW,bgH,null);
        next.draw(g);
        menu.draw(g);
    }
    private boolean isIn(UrmButton b, MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }
    public void mouseMoved(MouseEvent e){
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if(isIn(menu,e))
            menu.setMouseOver(true);
        else if(isIn(next,e))
            next.setMouseOver(true);

    }
    public void mouseReleased(MouseEvent e){
        if(isIn(menu,e)) {
            if (menu.isMousePressed()) {
                //System.out.println("menu!");
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        }else if(isIn(next,e)){
            if(next.isMousePressed()) {
                //System.out.println("next");
                playing.loadNextLevel();
            }
        }
        menu.resetBools();
        next.resetBools();
    }
    public void mousePressed(MouseEvent e){
        if(isIn(menu,e))
            menu.setMousePressed(true);
        else if(isIn(next,e))
            next.setMousePressed(true);
    }
}
