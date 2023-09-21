package inputs;

import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;
    static public boolean eevent;
    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel=gamePanel;
    }

    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(Gamestate.state){
            case MENU :
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            case SCORE:
                gamePanel.getGame().getScore().keyPressed(e);
            case OPTIONS:
                gamePanel.getGame().getGameOptions().keyPressed(e);
            default:
                break;
        }
        if(e.getKeyCode() == KeyEvent.VK_E)
            eevent = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(Gamestate.state){
            case MENU :
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
