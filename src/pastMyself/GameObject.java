package pastMyself;


import org.newdawn.slick.GameContainer;

import org.newdawn.slick.Graphics;

/**
 * ゲームオブジェクトの抽象クラス.
 */
public abstract class GameObject
{
	/**
	 * インスタンス有効フラグ(falseならインスタンスは処理されない)
	 */
	public boolean active;

	/**
	 * 中心点のx座標
	 */
	public float abX;

	/**
	 * 中心点のy座標
	 */
	public float abY;

	/** 横幅 */
	public float width;

	/** 縦幅 */
	public float height;

	/**
	 * 画面に表示するx座標
	 */
	public float displayX;

	/**
	 * 画面に表示するy座標
	 */
	public float displayY;

	/**
	 * ステップごとの更新.
	 */
	public abstract void update(GameContainer gc, float cameraX, float cameraY);

	/**
	 * ステップごとの描画処理.
	 */
	public abstract void render(Graphics g, ImageManager im);

	void changeToDisplayPoint(float cameraX, float cameraY)
	{
		displayX = abX - cameraX + Play.DISPLAY_WIDTH / 2;
		displayY = abY - cameraY + Play.DISPLAY_HEIGHT / 2;
	}

	/**
	 * オブジェクトがプレイ領域内にいるかどうかを確認し,
	 * 領域外に出ている場合は,インスタンスを無効にする.
	 *
	 * @param mergin 余裕
	 */
	boolean checkLeaving(int mergin)
	{
		return  (displayX < - width / 2 - mergin
				|| displayX > Play.DISPLAY_WIDTH + width / 2 + mergin
				|| displayY < - height / 2 - mergin
				|| displayY > Play.DISPLAY_HEIGHT + height / 2 + mergin);
	}
}