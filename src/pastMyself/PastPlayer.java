package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class PastPlayer extends GameObject
{
    /**
     * 速度のx成分
     */
    private float speedX;
    /**
     * 速度のy成分
     */
    private float speedY;
    private float[] recordXs;
    private float[] recordYs;
    public final int X_MAX = 5000;
    private int counter;
    private int finalCount;

    /**
     * コンストラクタ
     *
     * @param width  横幅
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
        counter = 0;
    }

    /**
     * プレイヤーの座標を記録する
     *
     * @param playerX プレイヤーのx座標
     * @param playerY プレイヤーのy座標
     */
    public void recordPoint(float playerX, float playerY)
    {
        recordXs[counter] = playerX;
        recordYs[counter] = playerY;
        //System.out.println(counter);
        counter++;
    }

    /**
     * プレイヤーの座標の記録を終える
     */
    public void finishRecord()
    {
        finalCount = counter;
    }

    public int getFinalCount()
    {
        return finalCount;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        if (counter + 1 < finalCount)
        {
            speedX = recordXs[counter + 1] - recordXs[counter];
            speedY = recordYs[counter + 1] - recordYs[counter];
            abX = recordXs[counter];
            abY = recordYs[counter];
        }
        else
        {
            speedX = 0;
            speedY = 0;
        }
        if (counter > finalCount + 120)
        {
            activate();
        }
        changeToDisplayPoint(cameraX, cameraY);
        System.out.println(counter);
        counter++;
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
        counter = 0;
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
