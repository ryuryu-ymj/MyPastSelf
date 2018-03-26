package pastMyself;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public class Player extends GameObject
{
	/** 速度のx成分 */
	private float speedX;
	/** 速度のy成分 */
	private float speedY;
	/** スペースキーが押されている時間 */
	private float keySpaceDownTime;
	/** 地面の上に接しているかどうか */
	private boolean isOnGround;
	/** 地面の下に接しているかどうか */
	private boolean isUnderGround;
	/** 地面の左に接しているかどうか */
	private boolean isLeftGround;
	/** 地面の右に接しているかどうか */
	private boolean isRightGround;
	private boolean isTryToMove;

	/** ジャンプの高さの最大値 */
	private final int JUMP_HEIGHT_MAX;
	/** 歩く速度の最大値 */
	private final int WALK_SPEED_MAX;

	/**
	 * コンストラクタ
	 * @param width 横幅
	 * @param height 縦幅
	 * @param jumpHeightMax ジャンプの高さの最大値
	 * @param walkSpeedMax 歩く速度の最大値
	 */
	Player(float width, float height, int jumpHeightMax, int walkSpeedMax)
	{
		this.width = width;
		this.height = height;
		this.JUMP_HEIGHT_MAX = jumpHeightMax;
		this.WALK_SPEED_MAX = walkSpeedMax;
		isOnGround = false;
		isLeftGround = false;
		isRightGround = false;
	}

	@Override
	public void update(GameContainer gc, float cameraX, float cameraY)
	{
		isTryToMove = false;
		if (isUnderGround)
		{
			speedY = 0;
		}
		speedY += 0.5; // 重力

		if (isOnGround)
		{
			speedY = 0;

			// ジャンプ
			if (gc.getInput().isKeyPressed(Input.KEY_SPACE))
			{
				keySpaceDownTime = 1;
			}
			else if (gc.getInput().isKeyDown(Input.KEY_SPACE) && keySpaceDownTime != 0)
			{
				keySpaceDownTime += 1.5;
			}
			else if (keySpaceDownTime > 0)
			{
				speedY = -keySpaceDownTime;
				isOnGround = false;
				keySpaceDownTime = 0;
			}
			if (keySpaceDownTime > JUMP_HEIGHT_MAX)
			{
				keySpaceDownTime = JUMP_HEIGHT_MAX;
				speedY = -keySpaceDownTime;
				isOnGround = false;
				keySpaceDownTime = 0;
			}
		}

		if (isLeftGround || isRightGround)
		{
			speedX = 0;
		}
		if (!isRightGround)
		{
			if (gc.getInput().isKeyDown(Input.KEY_LEFT) && speedX > -WALK_SPEED_MAX)
			{
				speedX -= 1;
				isTryToMove = true;
			}
			if (!gc.getInput().isKeyDown(Input.KEY_RIGHT) && speedX > 0)
			{
				speedX -= 0.5;
			}
		}
		if (!isLeftGround)
		{
			if (gc.getInput().isKeyDown(Input.KEY_RIGHT) && speedX < WALK_SPEED_MAX)
			{
				speedX += 1;
				isTryToMove = true;
			}
			if (!gc.getInput().isKeyDown(Input.KEY_LEFT) && speedX < 0)
			{
				speedX += 0.5;
			}
		}

		abX += speedX;
		abY += speedY;
		changeToDisplayPoint(cameraX, cameraY);
		//System.out.println(isOnGround + " " + isUnderGround + " " + isLeftGround + " " + isRightGround);
	}

	enum Position{ON, UNDER, RIGHT, LEFT};

	/**
	 * 地面へのめり込みを防ぐ
	 * @param position 地面に対してのプレイヤーの位置
	 * @param groundXorY 地面のx座標またはy座標
	 */
	public void adjustToGround(Position position, float groundXorY)
	{
		switch (position)
		{
			case ON:
				abY = groundXorY - width / 2;
				break;
			case UNDER:
				abY = groundXorY + width / 2;
				break;
			case LEFT:
				abX = groundXorY - width / 2;
				break;
			case RIGHT:
				abX = groundXorY + width / 2;
				break;
		}
	}

	@Override
	public void render(Graphics g, ImageManager im)
	{
		// TODO 自動生成されたメソッド・スタブ
		//g.setColor(Color.black);
		//g.drawRect(displayX - width / 2, displayY - height / 2, width, height);
		im.drawPlayer(displayX, displayY, width, height);
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
		speedX = 0;
		speedY = 0;
	}

	/*public boolean isOnGround()
	{
		return isOnGround;
	}*/

	public void setOnGround(boolean onGround)
	{
		isOnGround = onGround;
	}

	public void setUnderGround(boolean underGround)
	{
		isUnderGround = underGround;
	}

	public void setLeftGround(boolean leftGround)
	{
		isLeftGround = leftGround;
	}

	public void setRightGround(boolean rightGround)
	{
		isRightGround = rightGround;
	}

	public void setSpeedX(float speedX)
	{
		if (!isTryToMove)
		{
			this.speedX = speedX;
		}
	}

	public void setSpeedY(float speedY)
	{
		this.speedY = speedY;
	}
}
