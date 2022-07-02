package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @说明 玩家子弹类，本类的实体对象是由玩家对象调用和创建
 * @author lenovo
 * @子类的开发步骤
 * 		1.继承元素基类，重写showElement方法
 * 		2.按照需求选择性重写其他方法，例如：move方法等
 *		3.思考并定义子类特有的属性
 */
public class PlayFire extends ElementObj {

	private int attack = 1;//攻击力，默认为1
	private int moveSpeed = 6;//移动速度值，默认为3
	private  String fx;//子弹射击方向
	//可以扩展出多种子弹：激光，导弹等等。(玩家类需要拥有该子弹类型)
	
	public PlayFire(){}
	
//	private PlayFire(int x, int y, int w, int h, ImageIcon icon, String fx) {
//		super(x, y, w, h, icon);
//		this.attack = 1;//一颗子弹一个敌人
//		this.moveSpeed = 3;//子弹移动速度
//		this.fx = fx;
//	}
	
	//对创建该对象的过程进行封装，外界只需要传输必要的约定参数，返回值为对象实体
	@Override	//{x:3,y:5,fx:up} 解析
	public ElementObj createElement(String str) {//定义字符串的规则
		String[] split = str.split(",");
		for(String str1 : split) {
			String[] split2 = str1.split(":");//0下标是x y fx 1下标是值
			switch(split2[0]) {
				case "x":
					this.setX(Integer.parseInt(split2[1]));
					break;
				case "y":
					this.setY(Integer.parseInt(split2[1]));
					break;
				case "w":
					this.setW(Integer.parseInt(split2[1]));
					break;
				case "h":
					this.setH(Integer.parseInt(split2[1]));
					break;
				case "fx":
					this.fx = split2[1];
					break;
				default:
					break;
			}
		}
		this.attack = 1;
		this.moveSpeed = 6;
		return this;
	}
	
	@Override
	public void showElement(Graphics g) {
		g.setColor(Color.red);// new Color(255, 255, 255)
		g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());
	}
	
	/**
	 * 对于子弹来说：1.出边界 2.碰撞 3.玩家放保险(全屏清子弹...)
	 * 处理方式就是，当达到死亡的条件时，只进行修改死亡状态的操作
	 */
	@Override
	protected void move(long gameTime) {
		if(this.getX() < 0 || this.getX() > 900 || this.getY() < 0 || this.getY() >600) {
//			if(this.fx.equals("right")) {
//				this.fx = "left";
//			} else {
				this.setLive(false);
				return;
//			}
		}
		switch(this.fx) {
			case "left":
				this.setX(this.getX() - this.moveSpeed);
				break;
			case "up":
				this.setY(this.getY() - this.moveSpeed);
				break;
			case "right":
				this.setX(this.getX() + this.moveSpeed);
				break;
			case "down":
				this.setY(this.getY() + this.moveSpeed);
				break;
			default:
				break;
		}
	}

	public int getAttack() {
		return attack;
	}

}
