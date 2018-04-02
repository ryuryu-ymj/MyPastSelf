package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Background extends GameObject
{
    Background(int width, int height)
    {
        this.width = width;
        this.height = height;
        active = true;
    }

    public void update(GameContainer gc, float cameraX, float cameraY)
    {
        abX = 0;
        abY = 0;
        changeToDisplayPoint(cameraX, cameraY);
        displayX = displayX % ObjectPool.CUBE_WIDTH;
        displayY = displayY % ObjectPool.CUBE_WIDTH;
    }

    public void render(Graphics g, ImageManager im)
    {
        float x = displayX;
        float y = displayY;
        while (x < Play.DISPLAY_WIDTH)
        {
            while (y < Play.DISPLAY_HEIGHT)
            {
                im.drawBackground(x, y, width, height);
                //System.out.print("(" + x + " " + y + " " + width + " " + height + ")");
                y += ObjectPool.CUBE_WIDTH * ImageManager.BACKGROUND_Y_NUM;
            }
            y = displayY;
            x += ObjectPool.CUBE_WIDTH * ImageManager.BACKGROUND_X_NUM;
        }
        // System.out.println();
    }
}
