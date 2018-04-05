package pastMyself;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * ゲームで使う画像の管理、描画
 *
 * @author ryuryu
 */
public class ImageManager
{
    private Image player;
    private Image pastPlayer;
    private Image ground;
    private Image groundSpine;
    private Image groundAppear;
    private Image groundDisappear;
    private Image goal;
    private Image start;
    private Image warp;
    private Image background;
    /** 画像の余白の幅 */
    private final int MARGIN = 78;
    /** 背景の画像のcubeの列数 */
    public static final int BACKGROUND_X_NUM = 1;
    /** 背景の画像のcubeの段数 */
    public static final int BACKGROUND_Y_NUM = 1;

    ImageManager()
    {
        try
        {
            SpriteSheet ss = new SpriteSheet("res/img/cube.png", 390, 390);
            player = ss.getSubImage(0, 0);
            pastPlayer = ss.getSubImage(1, 0);
            //ground = ss.getSubImage(2, 0);
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

        try
        {
            SpriteSheet ss = new SpriteSheet("res/img/object.png", 390, 390);
            ground = ss.getSubImage(0, 0);
            goal = ss.getSubImage(1, 0);
            start = ss.getSubImage(2, 0);
            warp = ss.getSubImage(3, 0);
            background = ss.getSubImage(4, 0);
            groundSpine = ss.getSubImage(0, 1);
            groundDisappear = ss.getSubImage(1, 1);
            groundAppear = ss.getSubImage(2, 1);
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }

        /*try
        {
            background = new Image(("res/img/background.png"));
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }*/
    }

    /**
     * playerの画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawPlayer(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        player.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * pastPlayerの画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawPastPlayer(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        pastPlayer.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * groundの画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGround(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        ground.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * とげとげgroundの画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGroundSpine(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        groundSpine.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * 半点線groundの画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGroundAppear(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        groundAppear.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * 点線groundの画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGroundDisappear(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        groundDisappear.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * goalの画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawGoal(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        goal.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * startの画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawStart(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        start.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * warpの画像を表示する
     *
     * @param x      中心点のx座標
     * @param y      中心点のy座標
     * @param width  横幅
     * @param height 縦幅
     */
    public void drawWarp(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        warp.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * backgroundの画像を表示する
     *
     * @param x          中心点のx座標
     * @param y          中心点のy座標
     * @param cubeWidth  横幅
     * @param cubeHeight 縦幅
     */
    public void drawBackground(float x, float y, float cubeWidth, float cubeHeight)
    {
        float mergin = MARGIN * cubeWidth * BACKGROUND_X_NUM / (background.getWidth() - MARGIN * 2);
        background.draw(x - cubeWidth / 2 - mergin, y - cubeHeight / 2 - mergin,
                cubeWidth * BACKGROUND_X_NUM + mergin * 2, cubeHeight * BACKGROUND_Y_NUM + mergin * 2);
    }
}
