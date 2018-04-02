package pastMyself;

import org.newdawn.slick.*;

/**
 * プレイ画面の更新,描画を行うクラス.
 */
public class Play extends GameState
{
    /**
     * 画面の横幅
     */
    public static final int DISPLAY_WIDTH = 1400;
    /**
     * 画面の縦幅
     */
    public static final int DISPLAY_HEIGHT = 900;
    /**
     * フレームカウンタ
     */
    public static int counter;
    /**
     * プレイするステージの番号 0から
     */
    int stageNum;

    ObjectPool objectPool;
    StageDate stage;
    Time time;
    Font fontLarge;
    Font fontSmall;
    StageTitle stageTitle;

    private enum PlayState
    {
        STAGE_TITLE,
        START_FIRST_TRIAL,
        FIRST_TRIAL,
        START_SECOND_TRIAL,
        SECOND_TRIAL,
        GAMEOVER,;
    }

    private PlayState playState;

    /**
     * コンストラクタ
     */
    Play()
    {
        super();
        objectPool = new ObjectPool();
        stage = new StageDate();
        fontLarge = new Font("res/font/fontLarge");
        fontSmall = new Font("res/font/fontSmall");
        time = new Time(fontLarge, DISPLAY_WIDTH - 110, 20);
        stageTitle = new StageTitle(fontLarge, fontSmall, DISPLAY_WIDTH / 2, DISPLAY_HEIGHT / 2);
    }

    /**
     * 初期化処理.
     */
    public void init(GameContainer gc)
            throws SlickException
    {
        objectPool.init();
        stageNum = 0;
        playState = PlayState.STAGE_TITLE;
    }

    /**
     * ステップごとの更新.
     */
    public void update(GameContainer gc, int delta)
            throws SlickException
    {
        switch (playState)
        {
            case STAGE_TITLE:
                if (gc.getInput().isKeyPressed(Input.KEY_SPACE))
                {
                    playState = PlayState.START_FIRST_TRIAL;
                }
                break;

            case START_FIRST_TRIAL:
                objectPool.initStage();
                objectPool.disactivateGrounds();
                stage.loadStageDate(stageNum);
                objectPool.startGate.activate(stage.getStartX(), stage.getStartY());
                objectPool.player.activate(stage.getStartX() + ObjectPool.CUBE_WIDTH, stage.getStartY());
                objectPool.warp.activate(stage.getWarpX(), stage.getWarpY());
                objectPool.goal.activate(stage.getGoalX(), stage.getGoalY());
                counter = 0;
                time.setTimeLimit(stage.getTimeLimit());
                playState = PlayState.FIRST_TRIAL;
                break;

            case FIRST_TRIAL:
                objectPool.moveGround(stage.getGroundNum(), stage.getGroundXs(), stage.getGroundYs(), stage.getGroundTypes());
                objectPool.collisionDetection();
                objectPool.update(gc);
                time.update(gc, counter);
                if (objectPool.isPlayerWarp())
                {
                    playState = PlayState.START_SECOND_TRIAL;
                }
                break;

            case START_SECOND_TRIAL:
                objectPool.disactivateGrounds();
                objectPool.player.activate(stage.getStartX() + ObjectPool.CUBE_WIDTH, stage.getStartY());
                playState = PlayState.SECOND_TRIAL;
                break;

            case SECOND_TRIAL:
                objectPool.moveGround(stage.getGroundNum(), stage.getGroundXs(), stage.getGroundYs(), stage.getGroundTypes());
                objectPool.collisionDetection();
                objectPool.update(gc);
                time.update(gc, counter);
                if (objectPool.isPlayerGoal())
                {
                    stageNum++;
                    playState = PlayState.STAGE_TITLE;
                }
                break;

            case GAMEOVER:
                break;

        }

        counter++;
    }

    /**
     * ステップごとの描画処理.
     */
    public void render(GameContainer gc, Graphics g, ImageManager im)
            throws SlickException
    {
        switch (playState)
        {
            case STAGE_TITLE:
                stageTitle.render(stageNum, counter);
                break;
            case FIRST_TRIAL:
                objectPool.render(g, im);
                time.render(g, im);
                break;
            case SECOND_TRIAL:
                objectPool.render(g, im);
                time.render(g, im);
                break;
            case GAMEOVER:
                break;
        }
    }
}