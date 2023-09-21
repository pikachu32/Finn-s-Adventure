package gamestates;
import java.awt.*;


import main.Game;
import utilz.LoadSave;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GameOver extends State implements  Statemethods{
    private BufferedImage background;
    public GameOver(Game game) {
        super(game);
        loadBackground();
    }
    private void loadBackground(){
        background= LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_GAMEOVER);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background,0,0,null);
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        g.drawString("Game Over", (Game.GAME_WIDTH - g.getFontMetrics().stringWidth("Game Over"))/2, 200);


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

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
