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
	Stage stage;
	Time time;
	Font font;

	/**
	 * コンストラクタ
	 */
	Play()
	{
		super();
		objectPool = new ObjectPool();
		stage = new Stage();
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
		stageNum = 0;
		startStage();
	}

	/**
	 * ステップごとの更新.
	 */
	public void update(GameContainer gc, int delta)
			throws SlickException
	{
		objectPool.collisionDetection();
		objectPool.update(gc);
		time.update(gc, counter);

		counter++;
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(GameContainer gc, Graphics g, ImageManager im)
			throws SlickException
	{
		objectPool.render(g, im);
		time.render(g, im);
	}

	private void startStage()
    {
        stage.loadStageDate(stageNum);
        for (int i = 0; i < stage.getGroundNum(); i++)
        {
            objectPool.newGround(stage.getGroundX(i), stage.getGroundY(i), 0);
            //System.out.print(stage.getGroundX(i) + " " + stage.getGroundY(i));
            //System.out.println();
        }
        objectPool.startGate.activate(stage.getStartX(), stage.getStartY());
        objectPool.player.activate(stage.getStartX() + ObjectPool.CUBE_WIDTH, stage.getStartY());
        objectPool.warp.activate(stage.getWarpX(), stage.getWarpY());
        objectPool.goal.activate(stage.getGoalX(), stage.getGoalY());
        counter = 0;
        time.setTimeLimit(stage.getTimeLimit());
    }

    private void startSecondTrial()
    {

    }
}