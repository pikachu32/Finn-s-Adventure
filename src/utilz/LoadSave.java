package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class LoadSave {
    public static final String PLAYER_ATLAS="FinnSprite.png";
    public static final String LEVEL_ONE_ATLAS = "lvls/atlas/1.png";
    public static final String LEVEL_TWO_ATLAS = "lvls/atlas/2.png";
    public static final String LEVEL_ONE_DATA= "lvls/data/1.png";
    public static final String LEVEL_TWO_DATA= "lvls/data/2.png";
    public static final String MENU_BUTTONS="button_atlas.png";
    public static final String MENU_BACKGROUND="menu_background.png";

    public static final String OPTIONS_BACKGROUND="options_background.png";

    public static final String OPTION_BUTTONS="options_buttons.png";
    public static final String BACKGROUND_MENU="background_menu.png";
    public static final String LEVEL_ONE_BACKGROUND= "lvls/background/1.png";
    public static final String LEVEL_TWO_BACKGROUND= "lvls/background/2.png";
    public static final String BACKGROUND_GAMEOVER="game_over_background.png";
    public static final String PAUSE_BACKGROUND="pause_menu.png";
    public static final String SOUND_BUTTONS="sound_button.png";
    public static final String URM_BUTTONS="urm_buttons.png";
    public static final String COMPLETED_IMG="completed_sprite.png";
    public static final String GAME_COMPLETED="game_completed.png";
    public static final String ZOMBIE_SPRITE="ZombieToast.png";
    public static final String PENGUIN_SPRITE="GunterSprite.png";

    public static final String VOLUME_BUTTONS="volume_buttons.png";
    public static final String STATUS_BAR="health_power_bar.png";
    public static final String COLECTIBLE="colectible.png";
    public static final String SCORE_BACKGROUND="score_background.png";

    public static BufferedImage GetSpriteAtlas(String filename){
        BufferedImage img=null;
        InputStream is=LoadSave.class.getResourceAsStream("/"+filename );
        try {
            img = ImageIO.read(is);

            }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                is.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevelsData(){
        URL url=LoadSave.class.getResource("/lvls/data");
        File file=null;

        try {
            file=new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        File[] files=file.listFiles();
        File[] filesSorted=new File[files.length];
        for(int i=0;i<filesSorted.length;i++)
            for(int j=0;j< files.length;j++)
                if(files[j].getName().equals((i+1)+".png"))
                    filesSorted[i]=files[j];


        /*for(File f:files)
            System.out.println("file "+f.getName());*/

        BufferedImage[] imgs=new BufferedImage[filesSorted.length];
        for(int i=0;i<imgs.length;i++) {
            try {
                imgs[i]=ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imgs;
     }
    public static BufferedImage[] GetAllLevelsBackground(){
        URL url=LoadSave.class.getResource("/lvls/background");
        File file=null;

        try {
            file=new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        File[] files=file.listFiles();
        File[] filesSorted=new File[files.length];
        for(int i=0;i<filesSorted.length;i++)
            for(int j=0;j< files.length;j++)
                if(files[j].getName().equals((i+1)+".png"))
                    filesSorted[i]=files[j];


       /* for(File f:files)
            System.out.println("file "+f.getName());*/

        BufferedImage[] imgs=new BufferedImage[filesSorted.length];
        for(int i=0;i<imgs.length;i++) {
            try {
                imgs[i]=ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imgs;
    }
    public static BufferedImage[] GetAllLevelsAtlas(){
        URL url=LoadSave.class.getResource("/lvls/atlas");
        File file=null;

        try {
            file=new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        File[] files=file.listFiles();
        File[] filesSorted=new File[files.length];
        for(int i=0;i<filesSorted.length;i++)
            for(int j=0;j< files.length;j++)
                if(files[j].getName().equals((i+1)+".png"))
                    filesSorted[i]=files[j];


        /*for(File f:files)
            System.out.println("file "+f.getName());*/

        BufferedImage[] imgs=new BufferedImage[filesSorted.length];
        for(int i=0;i<imgs.length;i++) {
            try {
                imgs[i]=ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imgs;
    }
}
