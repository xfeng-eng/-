package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 * 说明：所有元素的基类
 * @author lenovo
 *
 */
public abstract class ElementObj {
	private int x;			//元素x值
	private int y;			//元素y值
	private int w;			//元素宽度
	private int h;			//元素高度
	private ImageIcon icon;//元素图片
	private boolean live = true;//元素生存状态live true-生存 false-死亡， 可以采用枚举值来定义生存状态(生存，死亡，隐身，无敌)
	private int HP = 10;//元素血量HP，默认血量为10(可改)
	
	public ElementObj() {}
	
	/**
	 * @说明 带参数的构造方法；可以由子类传输数据到父类
	 * @param x 左上角x坐标
	 * @param y 左上角y坐标
	 * @param w 宽度
	 * @param h 高度
	 * @param icon 图片
	 */
	public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.icon = icon;
	}
	
	/**
	 * @说明 抽象方法 显示元素
	 * @param g 画笔 用于进行绘画
	 */
	public abstract void showElement(Graphics g);
	
	/**
	 * @说明 键盘按钮响应事件方法(非强制重写方法)
	 * @param b1 点击的类型 true代表按下，false代表松开
	 * @param key 代表触发的键盘的keyCode值
	 */
	public void keyClick(boolean b1, int key) {
		System.out.println("测试使用");
	}
	
	
	
	/**
	 * @设计模式 模板模式；在模板模式中定义 对象执行方法的先后顺序，由子类选择性重写该方法
	 * 			1.移动  2.换装  3.子弹发射
	 */
	public final void model(long gameTime) {
		//先换装
		updateImage(gameTime);
		//在移动
		move(gameTime);
		//发射子弹
		add(gameTime);
//      射击
		attack(gameTime);
//		站立
		stand(gameTime);
//      状态切换
		stateSwitch(gameTime);
	}
	
	/**
	 * @说明 换装方法；需要换装的子类，需实现此方法
	 */
	protected void updateImage(long gameTime) {//long ... aaa 不定长数组，可以向该方法传输N个long类型的数据
		
	}
	
	/**
	 * @说明 移动方法；需要移动的子类，需实现此方法
	 */
	protected void move(long gameTime) {
		
	}
	
	/**
	 * @说明 添加道具方法；需要添加道具的子类，需实现此方法
	 */
	protected void add(long gameTime) {
		
	}
	
	//攻击射击方法，给子类继承的
	public void attack(long gameTime){}
	
	//站立更换图片
	public void stand(long gameTime){}
	
	//
	public void stateSwitch(long gameTime){}
	
	/**
	 * @说明 死亡方法；需要实现死亡特效等的子类，需实现此方法
	 */
	public void die(long gameTime) {
		
	}
	
	public ElementObj createElement(String str) {
		return null;
	}
	
	/**
	 * @说明 生成元素的碰撞矩形对象方法
	 * @return Rectangle 元素的碰撞矩形对象(实时返回)
	 */
	public Rectangle getRectangle() {
		//可以将此数据进行处理
		return new Rectangle(x,y,w,h);
	}
	
	/**
	 * @说明 碰撞检测方法
	 * @param obj 碰撞检测的元素对象
	 * @return boolean 返回true说明有碰撞，返回false说明没有碰撞
	 */
	public boolean pk(ElementObj obj) {
		return this.getRectangle().intersects(obj.getRectangle());
	}
	
	/**
	 * @说明 被攻击方法；若子类被攻击方法与父类不一致，则需要重写此方法
	 */
	public void beAttacted(int attack) {
		this.HP -= attack;
		if(this.HP <= 0) {
			//加入死亡特效
			this.setLive(false);
		}
	}
	
	/**
	 * 只要是VO类 就要为属性生成set和get方法
	 */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}
	
}
