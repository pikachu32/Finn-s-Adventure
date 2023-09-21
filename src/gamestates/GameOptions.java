package gamestates;

import main.DataBase;
import main.Game;
import ui.MenuButton;
import ui.OptionsButtons;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GameOptions extends State implements Statemethods{
    private OptionsButtons[] buttons=new OptionsButtons[3];
    private BufferedImage backgroundImg, background;
    private int menuX, menuY, menuWidth, menuHeight;
    public GameOptions(Game game) {

        super(game);
        loadBackground();
        loadButtons();

    }

    private void loadBackground() {
        backgroundImg= LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_BACKGROUND);
        background= LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_MENU);
        menuWidth=(int)(backgroundImg.getWidth()*Game.TILES_SCALE);
        menuHeight=(int)(backgroundImg.getHeight()*Game.TILES_SCALE);
        menuX=Game.GAME_WIDTH/2-menuWidth/2;
        menuY=(int)(45*Game.TILES_SCALE);
    }

    private void loadButtons() {
        buttons[0]=new OptionsButtons(Game.GAME_WIDTH/2,(int)(150*Game.TILES_SCALE),0, Gamestate.SCORE);
        buttons[1]=new OptionsButtons(Game.GAME_WIDTH/2,(int)(220*Game.TILES_SCALE),1, Gamestate.OPTIONS);
        buttons[2]=new OptionsButtons(Game.GAME_WIDTH/2,(int)(290*Game.TILES_SCALE),2, Gamestate.MENU);
    }

    @Override
    public void update() {
        for(OptionsButtons mb:buttons){
            mb.update();
            //System.out.println("update option buttons");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background,0,0,null);
        g.drawImage(backgroundImg, menuX,menuY,menuWidth,menuHeight,null);
        for(OptionsButtons mb:buttons){
            mb.draw(g);
        }
        int []scor=new int[3];
        for(int i=0;i<3;i++){
            scor[i]=DataBase.descending("baza", "data", i+1);
        System.out.println(scor[i]);}
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(OptionsButtons mb:buttons){
            if(isIn(e,mb)){
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(OptionsButtons mb:buttons){
            if(isIn(e,mb)){
                if(mb.isMousePressed()) {
                    mb.applyGameState();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(OptionsButtons mb:buttons){
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(OptionsButtons mb:buttons){
            mb.setMouseOver(false);
        }
        for(OptionsButtons mb:buttons){
            if(isIn(e,mb)){
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            Gamestate.state=Gamestate.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
