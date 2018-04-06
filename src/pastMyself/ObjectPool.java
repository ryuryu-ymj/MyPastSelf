package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * ゲームオブジェクトの管理クラス.
 * オブジェクトのインスタンスを持ち,
 * オブジェクト同士の相互作用(衝突処理など)を一括管理する.
 */
public class ObjectPool
{
    public Player player;
    public Camera camera;
    public Ground[] grounds;
    public PastPlayer pastPlayer;
    public StartGate startGate;
    public Warp warp;
    public Goal goal;
    public Background background;

    //public static int time;
    private boolean isPlayerWarp;
    private boolean isPlayerGoal;
    private boolean isPlayerDead;

    /**
     * 画面上におけるgroundの数の最大値
     */
    public static final int GROUND_MAX = 100;
    /**
     * playerやgroundの幅
     */
    public static final int CUBE_WIDTH = 55;

    /**
     * そのgroundが表示されたかどうか
     */
    public static boolean[] isGroundDisplay = new boolean[StageDate.GROUND_MAX];

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
        background = new Background(CUBE_WIDTH, CUBE_WIDTH);
        init();
    }

    /**
     * 初期化処理.
     */
    public void init()
    {
    }

    /**
     * ステージの初めの初期化処理
     */
    public void initFirst()
    {
        //time = 0;
        isPlayerWarp = false;
        isPlayerGoal = false;
        isPlayerDead = false;
        for (int i = 0; i < isGroundDisplay.length; i++)
        {
            isGroundDisplay[i] = false;
        }
        for (int i = 0; i < grounds.length; i++)
        {
            grounds[i].active = false;
        }
        pastPlayer.active = false;
        pastPlayer.initDate();
        player.active = false;
        startGate.active = false;
        goal.active = false;
        warp.active = false;
        Ground.startFirstTrial();
    }

    /**
     * ワープ後の初期化
     */
    public void initSecond()
    {
        pastPlayer.finishRecord();
        pastPlayer.activate();
        Ground.startSecondTrial();
    }

    /**
     * すべてのgroundを消す
     */
    public void disactivateGrounds()
    {
        for (int i = 0; i < isGroundDisplay.length; i++)
        {
            isGroundDisplay[i] = false;
        }
        for (int i = 0; i < grounds.length; i++)
        {
            grounds[i].active = false;
        }
    }

    /**
     * ステップごとの更新.
     */
    public void update(GameContainer gc)
    {
        if (!pastPlayer.active)
        {
            pastPlayer.recordPoint(player.abX, player.abY);
        }
        else
        {
            pastPlayer.update(gc, camera.x, camera.y);
        }
        if (player.active)
        {
            player.update(gc, camera.x, camera.y);
        }
        updateObjects(grounds, gc);
        startGate.update(gc, camera.x, camera.y);
        warp.update(gc, camera.x, camera.y);
        goal.update(gc, camera.x, camera.y);
        background.update(gc, camera.x, camera.y);
        camera.update(player.abX, player.abY);
        //System.out.println(/*time + " " + pastPlayer.active*/);
        //System.out.println(camera.x + " " + camera.y);
        //time++;
    }

    /**
     * ステップごとの描画処理.
     */
    public void render(Graphics g, ImageManager im)
    {
        if (background.active)
        {
            background.render(g, im);
        }
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
     * 新しいgroundを作る
     *
     * @param x    groundのx座標
     * @param y    groundのy座標
     * @param type groundのtype
     * @return groundsの配列番号　なかったら-1
     */
    public int newGround(int x, int y, Ground.Type type, int num)
    {
        for (int i = 0; i < GROUND_MAX; i++)
        {
            if (!grounds[i].active)
            {
                grounds[i].activate(x, y, type, num);
                return i;
            }
        }
        return -1;
    }

    public void moveGround(int groundNum, int[] groundXs, int[] groundYs, Ground.Type[] groundTypes)
    {
        for (int i = 0; i < groundNum; i++)
        {
            if (checkEntering(groundXs[i], groundYs[i], CUBE_WIDTH, CUBE_WIDTH))
            {
                if (!isGroundDisplay[i])
                {
                    if (newGround(groundXs[i], groundYs[i], groundTypes[i], i) != -1)
                    {
                        isGroundDisplay[i] = true;
                    }
                    else
                    {
                        System.out.println("groundの数が足りません" + groundXs[i] + " " + groundYs[i] + " " + i);
                    }
                }
            }
        }
    }

    /**
     * オブジェクトが画面内に存在するかの判定
     *
     * @param x      オブジェクトの中心点のx座標
     * @param y      オブジェクトの中心点のy座標
     * @param width  オブジェクトの横幅
     * @param height オブジェクトの縦幅
     * @return オブジェクトが画面内に存在するか
     */
    public boolean checkEntering(int x, int y, int width, int height)
    {
        if (x + width / 2 > camera.x - Play.DISPLAY_WIDTH / 2
                && x - width / 2 < camera.x + Play.DISPLAY_WIDTH / 2
                && y + height / 2 > camera.y - Play.DISPLAY_HEIGHT / 2
                && y - height / 2 < camera.y + Play.DISPLAY_HEIGHT / 2)
            return true;
        return false;
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
                    && player.abY + player.height / 2 /* 判定を甘く */ >= pastPlayer.abY - pastPlayer.height / 2
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
            check:
            if (ground.active)
            {
                if ((ground.getType() == Ground.Type.TO_DISAPPEAR && isPlayerWarp == true)
                        || (ground.getType() == Ground.Type.TO_APPEAR && isPlayerWarp == false))
                {
                    break check;
                }

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

                    if (ground.getType() == Ground.Type.SPINE)
                    {
                        isPlayerDead = true;
                    }
                }
            }
        }
        //System.out.println();

        // playerとwarpの衝突
        if (player.abX + player.width / 2 > warp.abX && player.abX - player.width / 2 < warp.abX
                && player.abY + player.height / 2 > warp.abY && player.abY - player.height / 2 < warp.abY)
        {
            isPlayerWarp = true;
        }

        // playerとgoalの衝突
        if (player.abX + player.width / 2 > goal.abX && player.abX - player.width / 2 < goal.abX
                && player.abY + player.height / 2 > goal.abY && player.abY - player.height / 2 < goal.abY)
        {
            //System.out.println("goal");
            isPlayerGoal = true;
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
        for (GameObject obj : object)
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

    public boolean isPlayerWarp()
    {
        return isPlayerWarp;
    }

    public boolean isPlayerGoal()
    {
        return isPlayerGoal;
    }

    public boolean isPlayerDead()
    {
        return isPlayerDead;
    }
}
