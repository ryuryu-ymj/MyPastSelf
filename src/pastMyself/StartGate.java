package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class StartGate extends GameObject
{
    StartGate(float width, float height)
    {
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        changeToDisplayPoint(cameraX, cameraY);
    }

    @Override
    public void render(Graphics g, ImageManager im)
    {
        im.drawStart(displayX, displayY, width, height);
    }

    /**
     * 初期化処理
     *
     * @param x X座標
     * @param y Y座標
     */
    public void activate(float x, float y)
    {
        active = true;
        this.abX = x;
        this.abY = y;
    }
}
