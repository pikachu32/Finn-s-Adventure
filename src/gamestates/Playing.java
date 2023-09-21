package gamestates;

import Objects.ObjectManager;
import entity.EnemyManager;
import entity.Player;
import levels.LevelManager;
import main.DataBase;
import main.Game;
import ui.GameCompletedOverlay;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static utilz.HelpMethods.*;

import static main.Game.GAME_HEIGHT;
import static main.Game.PLAYER_SCALE;

public class Playing extends State implements  Statemethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private GameOverOverlay gameOverOverlay;
    private ObjectManager objectManager;
    private boolean paused=false;
    private PauseOverlay pauseOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private GameCompletedOverlay gameCompletedOverlay;
    private int xLvlOffset;
    private int leftBorder=(int)(0.2*Game.GAME_WIDTH);
    private int rightBorder=(int)(0.8*Game.GAME_WIDTH);
    private int maxLvlOffsetX;//=maxTilesOffset*Game.TILES_SIZE;
    private boolean lvlCompleted=false;
    private boolean gameCompleted=false;
    private boolean gameOver=false;

    public Playing(Game game) {
        super(game);
        initClasses();
        calculateLvlOffset();
        loadStartLevel();
    }
    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        //enemyManager=new EnemyManager(this, levelManager.lvlIndex);
        //lvlCompleted=false;
    }

    public void resetAll() {
        gameOver=false;
        paused=false;
        lvlCompleted=false;
        player.resetAll();
        enemyManager.resetAllEnemyes();
        objectManager.resetAllObjects();
        gameCompleted=false;
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit(attackBox);
    }
    public void checkObjectTouched(Rectangle2D.Float hitbox){
        objectManager.checkObjectTouched(hitbox);
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calculateLvlOffset() {
        maxLvlOffsetX=levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager=new LevelManager(game);
        enemyManager=new EnemyManager(this, levelManager.lvlIndex);
        objectManager=new ObjectManager(this);
        player=new Player(50,100, (int)(32*PLAYER_SCALE), (int)(32*PLAYER_SCALE), levelManager.lvlIndex, this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        pauseOverlay=new PauseOverlay(this);
        gameOverOverlay=new GameOverOverlay(this);
        levelCompletedOverlay=new LevelCompletedOverlay(this);
        gameCompletedOverlay=new GameCompletedOverlay(this);
    }
    public void windowFocusLost(){
        player.resetDirBooleans();
    }
    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        if(paused){
            pauseOverlay.update();
        }else if(gameCompleted){
            gameCompletedOverlay.update();
        }else if(lvlCompleted){
            levelCompletedOverlay.update();
        }else if(!gameOver){
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
            checkGameOver();
        }
        }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g,xLvlOffset);
        if(paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }else if(gameCompleted){
            gameCompletedOverlay.draw(g);
        }else if(lvlCompleted)
            levelCompletedOverlay.draw(g);
        else if(gameOver) {
            gameOverOverlay.draw(g);
        }
            //Gamestate.state = Gamestate.GAME_OVER;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
    public void mouseDragged(MouseEvent e){
        if(paused)
            pauseOverlay.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver) {
            if (paused)
                pauseOverlay.mousePressed(e);
            else if(gameCompleted)
                gameCompletedOverlay.mousePressed(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver) {
            if (paused)
                pauseOverlay.mouseReleased(e);
            else if(gameCompleted)
                gameCompletedOverlay.mouseReleased(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver) {
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if(gameCompleted)
                gameCompletedOverlay.mouseMoved(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        }
    }
    public void setMaxLvlOffset(int lvlOffset){
        this.maxLvlOffsetX=lvlOffset;
    }
    public void unpauseGame(){
        paused=false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_LEFT:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_X:
                    player.setAttack(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused=!paused;

            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                player.setJump(false);
                break;
            case KeyEvent.VK_LEFT:
                player.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setRight(false);
                break;
        }
    }
    public void checkGameOver(){
        if(player.getHitbox().y>GAME_HEIGHT) {
            gameOver = true;
            //Gamestate.state = Gamestate.GAME_OVER;
        }
    }
    public void checkCloseToBorder(){
        int playerX=(int)player.getHitbox().x;
        int diff=playerX-xLvlOffset;
        if(diff>rightBorder){
            xLvlOffset+=diff-rightBorder;
        }else if(diff<leftBorder){
            xLvlOffset+=diff-leftBorder;
        }
        if(xLvlOffset>maxLvlOffsetX){
            xLvlOffset=maxLvlOffsetX;
        }else if(xLvlOffset<0){
            xLvlOffset=0;
        }
    }
    public void setGameOver(boolean gameOver){
        this.gameOver=gameOver;
    }
    public void setLvlCompleted(boolean lvlCompleted){
        this.lvlCompleted=lvlCompleted;
        player.setScore(player.getCurrentLvlScore()+ player.getScore());
        player.setCurrentLvlScore(0);
        if(levelManager.getLvlIndex()==2)
            setGameCompleted(true);
    }
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
    public ObjectManager getObjectManager(){return objectManager;}

    public void setGameCompleted(boolean gameCompleted) {
        this.gameCompleted = gameCompleted;
        if(gameCompleted)
            DataBase.insertB("baza", "data", player.getScore());
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }
}
