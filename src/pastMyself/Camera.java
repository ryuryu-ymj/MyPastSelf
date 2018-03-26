package pastMyself;

public class Camera
{
	/**
	 * 画面の中心座標
	 */
	float x, y;
	boolean active;

	Camera()
	{
		x = 0;
		y = 0;
		active = true;
	}

	void update(float playerX, float playerY)
	{
		if (active)
		{
			x = playerX;
			y = playerY - 100;
		}
	}
}
