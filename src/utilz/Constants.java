package utilz;

import main.Game;

public class Constants {

    public static final float GRAVITY=0.04f* Game.TILES_SCALE;
    public static final int ANI_SPEED=25;
    public static class ObjectConstants{
        public static final int COIN=0;

        public static final int COIN_VALUE=5;
        public static final int COIN_WIDTH_DEFAULT=23;
        public static final int COIN_HEIGHT_DEFAULT=21;
        public static final int COIN_WIDTH=(int)(Game.TILES_SCALE*COIN_WIDTH_DEFAULT);
        public static final int COIN_HEIGHT=(int)(Game.TILES_SCALE*COIN_HEIGHT_DEFAULT);
        public static int GetSpriteAmount(int objectType){
            switch(objectType){
                case COIN:
                    return 7;
                default:
                    return 1;
            }
        }


    }
    public static class PlayerConstants{
        public static final int IDLE=0;
        public static final int RUNNING=9;
        public static final int JUMP=15;
        public static final int HIT=16;
        public static final int DIE=18;
        public static final int ATTACK=23;

        public static int GetSpriteAmount(int player_action){
            switch(player_action){
                case IDLE:
                    return 9;
                case RUNNING:
                    return 6;
                case JUMP:
                    return 1;
                case HIT:
                    return 2;
                case DIE, ATTACK:
                    return 5;

                default:
                    return 1;
            }
        }

    }
    public static class EnemyConstants {
        public static final int ZOMBIE=0;
        public static final int PENGUIN=2;
        public static final int IDLE_ZOMBIE =0;
        public static final int RUN_ZOMBIE =2;
        public static final int JUMP_ZOMBIE =6;
        public static final int DIE_ZOMBIE =14;

        public static final int ZOMBIE_WIDTH_DEFAULT=32;
        public static final int ZOMBIE_HEIGHT_DEFAULT=32;
        public static final int ZOMBIE_HEIGHT=(int)(ZOMBIE_HEIGHT_DEFAULT*Game.PLAYER_SCALE);
        public static final int ZOMBIE_WIDTH=(int)(ZOMBIE_WIDTH_DEFAULT*Game.PLAYER_SCALE);
        public static final int ZOMBIE_DRAWOFFSET_X=(int)(7*Game.PLAYER_SCALE);
        public static final int ZOMBIE_DRAWOFFSET_Y=(int)(18*Game.PLAYER_SCALE);

        public static final int IDLE_PENGUIN =0;
        public static final int RUN_PENGUIN  =8;
        //public static final int JUMP_PENGUIN  =6;
        public static final int DIE_PENGUIN =12;
        public static final int SLIDE_PENGUIN =19;

        public static final int PENGUIN_WIDTH_DEFAULT=32;
        public static final int PENGUIN_HEIGHT_DEFAULT=32;
        public static final int PENGUIN_HEIGHT=(int)(PENGUIN_HEIGHT_DEFAULT*Game.PLAYER_SCALE);
        public static final int PENGUIN_WIDTH=(int)(PENGUIN_WIDTH_DEFAULT*Game.PLAYER_SCALE);
        public static final int PENGUIN_DRAWOFFSET_X=(int)(12*Game.PLAYER_SCALE);//12
        public static final int PENGUIN_DRAWOFFSET_Y=(int)(20*Game.PLAYER_SCALE);//20

        public static int GetMaxHealth(int enemy_type){
            switch (enemy_type){
                case ZOMBIE :
                    return 10;
                case PENGUIN:
                    return 10;
                default:
                    return 1;
            }
        }
        public static int GetEnemyDmg(int enemy_type){
            switch (enemy_type){
                case ZOMBIE :
                    return 15;
                case PENGUIN:
                    return 15;
                default:
                    return  10;
            }
        }
        public static int GetSpriteAmount(int enemy_type, int enemy_state){
            switch(enemy_type){
                case ZOMBIE :
                    switch (enemy_state){
                        case IDLE_ZOMBIE:
                            return 2;
                        case RUN_ZOMBIE:
                            return 4;
                        case JUMP_ZOMBIE:
                            return 8;
                        case DIE_ZOMBIE:
                            return 11;
                    }
                case PENGUIN:
                    switch (enemy_state){
                        case IDLE_PENGUIN :
                            return 8;
                        case RUN_PENGUIN:
                            return 4;
                        case DIE_PENGUIN:
                            return 7;
                        case SLIDE_PENGUIN:
                            return 3;
                    }
            }
            return 0;
        }


    }


    public static class Directions{
        public static final int LEFT=0;
        public static final int UP=1;
        public static final int RIGHT=2;
        public static final int DOWN=3;
    }
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT=140;
            public static final int B_HEIGHT_DEFAULT=56;
            public static final int B_WIDTH=(int)(B_WIDTH_DEFAULT* Game.TILES_SCALE);
            public static final int B_HEIGHT=(int)(B_HEIGHT_DEFAULT* Game.TILES_SCALE);

        }
        public static class PauseButtons{
            public static final int SOUND_SIZE_DEFAULT=42;
            public static final int SOUND_SIZE=(int)(SOUND_SIZE_DEFAULT*Game.TILES_SCALE);
        }
        public static class UrmButtons{
            public static final int URM_DEFAULT_SIZE=56;
            public static final int URM_SIZE=(int)(URM_DEFAULT_SIZE*Game.TILES_SCALE);
        }
        public static class VolumeButtons{
            public static final int VOLUME_DEFAULT_WIDTH=28;
            public static final int VOLUME_DEFAULT_HEIGHT=44;
            public static final int SLIDER_DEFAULT_WIDTH=215;
            public static final int VOLUME_WIDTH=(int)(VOLUME_DEFAULT_WIDTH*Game.TILES_SCALE);
            public static final int VOLUME_HEIGHT=(int)(VOLUME_DEFAULT_HEIGHT*Game.TILES_SCALE);
            public static final int SLIDER_WIDTH=(int)(SLIDER_DEFAULT_WIDTH*Game.TILES_SCALE);


        }
    }
}
