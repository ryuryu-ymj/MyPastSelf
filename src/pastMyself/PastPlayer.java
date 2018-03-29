package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class PastPlayer extends GameObject
{
    /** 速度のx成分 */
    private float speedX;
    /** 速度のy成分 */
    private float speedY;
    private float[] recordXs;
    private float[] recordYs;
    public final int X_MAX = 5000;
    private int finalCount;

    /**
     * コンストラクタ
     * @param width 横幅
     * @param height 縦幅
     */
    PastPlayer(float width, float height)
    {
        active = false;
        recordXs = new float[X_MAX];
        recordYs = new float[X_MAX];
        this.width = width;
        this.height = height;
        initDate();
    }

    /**
     * 座標データの初期化
     */
    public void initDate()
    {
        for (int i = 0; i < recordXs.length; i++)
        {
            recordXs[i] = 0;
            recordYs[i] = 0;
        }
        finalCount = X_MAX;
    }

    /**
     * プレイヤーの座標を記録する
     * @param playerX プレイヤーのx座標
     * @param playerY プレイヤーのy座標
     */
    public void recordPoint(float playerX, float playerY)
    {
        recordXs[ObjectPool.time] = playerX;
        recordYs[ObjectPool.time] = playerY;
    }

    /**
     * プレイヤーの座標の記録を終える
     * @param finalCount
     */
    public void finishRecord(int finalCount)
    {
        this.finalCount = finalCount;
    }

    public int getFinalCount() {
        return finalCount;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (ObjectPool.time + 1 < finalCount)
        {
            speedX = recordXs[ObjectPool.time + 1] - recordXs[ObjectPool.time];
            speedY = recordYs[ObjectPool.time + 1] - recordYs[ObjectPool.time];
            abX = recordXs[ObjectPool.time];
            abY = recordYs[ObjectPool.time];
        }
        else
        {
            speedX = 0;
            speedY = 0;
        }
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        //g.setColor(Color.gray);
        //g.drawRect(displayX - width / 2, displayY - height / 2, width, height);
        im.drawPastPlayer(displayX, displayY, width, height);
    }

    /**
     * 初期化処理
     */
    public void activate()
    {
        active = true;
    }

    public float getSpeedX()
    {
        return speedX;
    }

    public float getSpeedY()
    {
        return speedY;
    }
}
