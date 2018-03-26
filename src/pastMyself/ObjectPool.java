package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * ゲームオブジェクトの管理クラス.
 * オブジェクトのインスタンスを持ち,
 * オブジェクト同士の相互作用(衝突処理など)を一括管理する.
 */
public class ObjectPool
{
	Player player;
	Camera camera;
	Ground[] grounds;
	PastPlayer pastPlayer;
	StartGate startGate;
	Warp warp;
	Goal goal;

	public static int time;
	private boolean isSecondTrial;

	/** 画面上におけるgroundの数の最大値 */
	public static final int GROUND_MAX = 100;
	/** playerやgroundの幅 */
	public static final int CUBE_WIDTH = 55;

	ObjectPool()
	{
		player = new Player(CUBE_WIDTH, CUBE_WIDTH, 13, 7);
		pastPlayer = new PastPlayer(CUBE_WIDTH, CUBE_WIDTH);
		camera = new Camera();
		grounds = new Ground[GROUND_MAX];
		for (int i = 0; i < grounds.length; i++)
		{
			grounds[i] = new Ground(CUBE_WIDTH, CUBE_WIDTH);
		}
		startGate = new StartGate(CUBE_WIDTH, CUBE_WIDTH);
		warp = new Warp(CUBE_WIDTH, CUBE_WIDTH);
		goal = new Goal(CUBE_WIDTH, CUBE_WIDTH);
		init();
	}

	/**
	 * 初期化処理.
	 */
	public void init()
	{
		time = 0;
		isSecondTrial = false;
	}

	/**
	 * ステップごとの更新.
	 */
	public void update(GameContainer gc)
	{
	    if (isSecondTrial && !pastPlayer.active)
        {
            pastPlayer.finishRecord(time);
            time = 0;
            pastPlayer.activate();
        }
        if (time >= pastPlayer.getFinalCount() && pastPlayer.active)
        {
            time = 0;
        }
		if (player.active)
        {
            player.update(gc, camera.x, camera.y);
        }
        if (!pastPlayer.active)
        {
            pastPlayer.recordPoint(player.abX, player.abY);
        }
        else
        {
            pastPlayer.update(gc, camera.x, camera.y);
        }
		updateObjects(grounds, gc);
		startGate.update(gc, camera.x, camera.y);
		warp.update(gc, camera.x, camera.y);
		goal.update(gc, camera.x, camera.y);
		camera.update(player.abX, player.abY);
        System.out.println(time + " " + pastPlayer.active);
		time++;
	}

	/**
	 * ステップごとの描画処理.
	 */
	public void render(Graphics g, ImageManager im)
	{
		renderObjects(grounds, g, im);
        if (warp.active)
        {
            warp.render(g, im);
        }
        if (startGate.active)
        {
            startGate.render(g, im);
        }
        if (goal.active)
		{
			goal.render(g, im);
		}
		if (player.active)
        {
            player.render(g, im);
        }
        if (pastPlayer.active)
        {
            pastPlayer.render(g, im);
        }
	}

	/**
	 * 新しいカードを作る
	 * @param x groundのx座標
	 * @param y groundのy座標
	 * @param type groundのtype
	 * @return groundsの配列番号　なかったら-1
	 */
	public int newGround(int x, int y, int type)
	{
		for (int i = 0; i < GROUND_MAX; i++)
		{
			if (!grounds[i].active)
			{
				grounds[i].activate(x, y, type, i);
				return i;
			}
		}
		return -1;
	}

	/**
	 * 衝突判定
	 */
	public void collisionDetection()
	{
        player.setOnGround(false);
        player.setUnderGround(false);
        player.setLeftGround(false);
        player.setRightGround(false);
        // playerとpastPlayerの衝突
        if (pastPlayer.active)
        {
            if ((player.abX + player.width / 2 >= pastPlayer.abX - pastPlayer.width / 2
                    && player.abX - player.width / 2 <= pastPlayer.abX + pastPlayer.width / 2)
                    && player.abY + player.height / 2 + 4 /* 判定を甘く */ >= pastPlayer.abY - pastPlayer.height / 2
                    && player.abY - player.height / 2 <= pastPlayer.abY + pastPlayer.height / 2)
            {
                float distanceX = player.abX - pastPlayer.abX;
                float distanceY = player.abY - pastPlayer.abY;
                float slope = distanceY / distanceX;
                if (slope < 1 && slope > -1)
                {
                    if (distanceX > 0)
                    {
                        player.adjustToGround(Player.Position.RIGHT, pastPlayer.abX + pastPlayer.width / 2);
                        player.setRightGround(true);
                    }
                    else if (distanceX < 0)
                    {
                        player.adjustToGround(Player.Position.LEFT, pastPlayer.abX - pastPlayer.width / 2);
                        player.setLeftGround(true);
                    }
                }
                else if (slope > 1 || slope < -1)
                {
                    if (distanceY > 0)
                    {
                        player.adjustToGround(Player.Position.UNDER, pastPlayer.abY + pastPlayer.height / 2);
                        player.setUnderGround(true);
                    }
                    else if (distanceY < 0)
                    {
                        player.adjustToGround(Player.Position.ON, pastPlayer.abY - pastPlayer.height / 2);
                        player.setOnGround(true);
                        player.setSpeedX(pastPlayer.getSpeedX());
                    }
                }
            }
        }

	    // playerとgroundの衝突
		for (Ground ground : grounds)
		{
			if (ground.active)
			{
				if ((player.abX + player.width / 2 >= ground.abX - ground.width / 2
						&& player.abX - player.width / 2 <= ground.abX + ground.width / 2)
						&& player.abY + player.height / 2 >= ground.abY - ground.height / 2
						&& player.abY - player.height / 2 <= ground.abY + ground.height / 2)
				{
					float distanceX = player.abX - ground.abX;
					float distanceY = player.abY - ground.abY;
					float slope = distanceY / distanceX;
					if (slope < 1 && slope > -1)
					{
						if (distanceX > 0)
						{
							player.adjustToGround(Player.Position.RIGHT, ground.abX + ground.width / 2);
							player.setRightGround(true);
							//System.out.print(ground.num + "right ");
						}
						else if (distanceX < 0)
						{
							player.adjustToGround(Player.Position.LEFT, ground.abX - ground.width / 2);
							player.setLeftGround(true);
							//System.out.print(ground.num + "left ");
						}
					}
					else if (slope > 1 || slope < -1)
					{
						if (distanceY > 0)
						{
							player.adjustToGround(Player.Position.UNDER, ground.abY + ground.height / 2);
							player.setUnderGround(true);
							//System.out.print(ground.num + "under ");
						}
						else if (distanceY < 0)
						{
							player.adjustToGround(Player.Position.ON, ground.abY - ground.height / 2);
							player.setOnGround(true);
							//System.out.print(ground.num + "on ");
						}
					}
				}
			}
		}
		//System.out.println();

        // playerとwarpの衝突
        if (player.abX + player.width / 2 > warp.abX && player.abX - player.width / 2 < warp.abX
                && player.abY + player.height / 2 > warp.abY && player.abY - player.height / 2 < warp.abY)
        {
            isSecondTrial = true;
        }

        // playerとgoalの衝突
        if (player.abX + player.width / 2 > goal.abX && player.abX - player.width / 2 < goal.abX
                && player.abY + player.height / 2 > goal.abY && player.abY - player.height / 2 < goal.abY)
        {
            System.out.println("goal");
        }
	}

	/**
	 * 配列内のすべてのインスタンスを無効にする.
	 *
	 * @param object ゲームオブジェクトの配列
	 */
	private void deactivateObjects(GameObject[] object)
	{
		for (GameObject obj : object)
		{
			obj.active = false;
		}
	}

	/**
	 * 配列内のインスタンスのうち,有効な物のみを更新する.
	 *
	 * @param object ゲームオブジェクトの配列
	 */
	private void updateObjects(GameObject[] object, GameContainer gc)
	{
		for (GameObject obj: object)
		{
			if (obj.active)
			{
				obj.update(gc, camera.x, camera.y);
			}
		}
	}

	/**
	 * 配列内のインスタンスのうち,有効な物のみを描画する.
	 *
	 * @param object ゲームオブジェクトの配列
	 */
	private void renderObjects(GameObject[] object, Graphics g, ImageManager im)
	{
		for (GameObject obj : object)
		{
			if (obj.active)
			{
				obj.render(g, im);
			}
		}
	}

	public static int getTime() {
		return time;
	}
}
