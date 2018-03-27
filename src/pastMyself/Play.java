package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

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
	/** フレームカウンタ */
	public static int counter;
	/** プレイするステージの番号 0から */
	int stageNum;

	ObjectPool objectPool;
	StageDate stage;
	Time time;
	Font font;

	private enum PlayState
    {
        STAGE_TITLE,
        STRAT_FIRST_TRIAL,
        FIRST_TRIAL,
        START_SECOND_TRIAL,
        SECOND_TRIAL,
        GAMEOVER,
        ;
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
		font = new Font("res/font");
		time = new Time(font, DISPLAY_WIDTH - 110, 20);
	}

	/**
	 * 初期化処理.
	 */
	public void init(GameContainer gc)
			throws SlickException
	{
		objectPool.init();
        stageNum = 1;
        playState = PlayState.STRAT_FIRST_TRIAL;
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
                break;

            case STRAT_FIRST_TRIAL:
                stage.loadStageDate(stageNum);
                objectPool.startGate.activate(stage.getStartX(), stage.getStartY());
                objectPool.player.activate(stage.getStartX() + ObjectPool.CUBE_WIDTH, stage.getStartY());
                objectPool.warp.activate(stage.getWarpX(), stage.getWarpY());
                objectPool.goal.activate(stage.getGoalX(), stage.getGoalY());
                objectPool.initStage();
                counter = 0;
                time.setTimeLimit(stage.getTimeLimit());
                playState = PlayState.FIRST_TRIAL;
                break;

            case FIRST_TRIAL:
                for (int i = 0; i < stage.getGroundNum(); i++)
                {
                    if (objectPool.checkEntering(stage.getGroundX(i), stage.getGroundY(i), ObjectPool.CUBE_WIDTH, ObjectPool.CUBE_WIDTH))
                    {
                        if (!objectPool.isGroundDisplay[i])
                        {
                            if (objectPool.newGround(stage.getGroundX(i), stage.getGroundY(i), 0) != -1)
                            {
                                objectPool.isGroundDisplay[i] = true;
                            }
                            else
                            {
                                //System.out.println("groundの数が足りません" + stage.getGroundX(i) + " " + stage.getGroundY(i) + " " + i);
                            }
                        }
                    }
                    else if (objectPool.isGroundDisplay[i])
                    {
                        objectPool.isGroundDisplay[i] = false;
                    }
                    //System.out.print(stage.getGroundX(i) + " " + stage.getGroundY(i));
                    //System.out.println();
                }
                objectPool.collisionDetection();
                objectPool.update(gc);
                time.update(gc, counter);
                if (objectPool.isSecondTrial())
                {
                    playState = PlayState.START_SECOND_TRIAL;
                }
                break;

            case START_SECOND_TRIAL:
                objectPool.disactivateAll();
                objectPool.player.activate(stage.getStartX() + ObjectPool.CUBE_WIDTH, stage.getStartY());
                playState = PlayState.SECOND_TRIAL;
                break;

            case SECOND_TRIAL:
                for (int i = 0; i < stage.getGroundNum(); i++)
                {
                    if (objectPool.checkEntering(stage.getGroundX(i), stage.getGroundY(i), ObjectPool.CUBE_WIDTH, ObjectPool.CUBE_WIDTH))
                    {
                        if (!objectPool.isGroundDisplay[i])
                        {
                            if (objectPool.newGround(stage.getGroundX(i), stage.getGroundY(i), 0) != -1)
                            {
                                objectPool.isGroundDisplay[i] = true;
                            }
                            else
                            {
                                //System.out.println("groundの数が足りません" + stage.getGroundX(i) + " " + stage.getGroundY(i) + " " + i);
                            }
                        }
                    }
                    else if (objectPool.isGroundDisplay[i])
                    {
                        objectPool.isGroundDisplay[i] = false;
                    }
                    //System.out.print(stage.getGroundX(i) + " " + stage.getGroundY(i));
                    //System.out.println();
                }
                objectPool.collisionDetection();
                objectPool.update(gc);
                time.update(gc, counter);
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