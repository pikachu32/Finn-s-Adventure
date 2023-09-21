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

public class Score extends State implements Statemethods{
    private BufferedImage backgroundImg, background;
    public Score(Game game) {

        super(game);
        loadBackground();

    }

    private void loadBackground() {
        background= LoadSave.GetSpriteAtlas(LoadSave.SCORE_BACKGROUND);
    }



    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background,0,0,null);
        int []scor=new int[3];
        for(int i=0;i<3;i++){
            scor[i]=DataBase.descending("baza", "data", i+1);}
        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString("HighScores ",Game.GAME_WIDTH/2,150);
        for(int i=0;i<3;i++)
            g.drawString(scor[i]+" ", Game.GAME_WIDTH/2-200,(200+i*50));
        g.drawString("Press esc to enter main menu", Game.GAME_WIDTH/2-200,500);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            Gamestate.state=Gamestate.OPTIONS;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
