package pastMyself;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * ゲームで使う画像の管理、描画
 * @author ryuryu
 *
 */
public class ImageManager
{
	private Image player;
	private Image pastPlayer;
	private Image ground;
	private Image goal;
	private Image start;
	private Image warp;
	/* 画像の余白の幅 */
	private final int MARGIN = 78;

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
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
	}

    /**
     * playerの画像を表示する
     * @param x 中心点のx座標
     * @param y 中心点のy座標
     * @param width 横幅
     * @param height 縦幅
     */
    public void drawPlayer(float x, float y, float width, float height)
    {
    	float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        player.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * pastPlayerの画像を表示する
     * @param x 中心点のx座標
     * @param y 中心点のy座標
     * @param width 横幅
     * @param height 縦幅
     */
    public void drawPastPlayer(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        pastPlayer.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * pastPlayerの画像を表示する
     * @param x 中心点のx座標
     * @param y 中心点のy座標
     * @param width 横幅
     * @param height 縦幅
     */
    public void drawGround(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        ground.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * goalの画像を表示する
     * @param x 中心点のx座標
     * @param y 中心点のy座標
     * @param width 横幅
     * @param height 縦幅
     */
    public void drawGoal(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        goal.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * startの画像を表示する
     * @param x 中心点のx座標
     * @param y 中心点のy座標
     * @param width 横幅
     * @param height 縦幅
     */
    public void drawStart(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        start.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }

    /**
     * warpの画像を表示する
     * @param x 中心点のx座標
     * @param y 中心点のy座標
     * @param width 横幅
     * @param height 縦幅
     */
    public void drawWarp(float x, float y, float width, float height)
    {
        float mergin = MARGIN * width / (player.getWidth() - MARGIN * 2);
        warp.draw(x - width / 2 - mergin, y - height / 2 - mergin, width + mergin * 2, height + mergin * 2);
    }
}
