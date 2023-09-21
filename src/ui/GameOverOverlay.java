package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static utilz.LoadSave.GetSpriteAtlas;

public class GameOverOverlay {
    Playing playing;
    private BufferedImage background=GetSpriteAtlas(LoadSave.BACKGROUND_GAMEOVER);
    public GameOverOverlay(Playing playing){
        this.playing=playing;
    }
    public void draw(Graphics g){
        g.drawImage(background,0,0,null);
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        g.drawString("Game Over", (Game.GAME_WIDTH - g.getFontMetrics().stringWidth("Game Over"))/2, 200);

        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        g.setColor(Color.white);
        g.drawString("Score: "+(playing.getPlayer().getScore()+playing.getPlayer().getCurrentLvlScore()), Game.GAME_WIDTH/2-200,400);
        g.drawString("Press esc to enter main menu", Game.GAME_WIDTH/2-200,500);
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            playing.resetAll();
            Gamestate.state=Gamestate.MENU;
        }
    }
}
