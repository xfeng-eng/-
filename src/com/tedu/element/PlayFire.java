package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @˵�� ����ӵ��࣬�����ʵ�����������Ҷ�����úʹ���
 * @author lenovo
 * @����Ŀ�������
 * 		1.�̳�Ԫ�ػ��࣬��дshowElement����
 * 		2.��������ѡ������д�������������磺move������
 *		3.˼���������������е�����
 */
public class PlayFire extends ElementObj {

	private int attack = 1;//��������Ĭ��Ϊ1
	private int moveSpeed = 6;//�ƶ��ٶ�ֵ��Ĭ��Ϊ3
	private  String fx;//�ӵ��������
	//������չ�������ӵ������⣬�����ȵȡ�(�������Ҫӵ�и��ӵ�����)
	
	public PlayFire(){}
	
//	private PlayFire(int x, int y, int w, int h, ImageIcon icon, String fx) {
//		super(x, y, w, h, icon);
//		this.attack = 1;//һ���ӵ�һ������
//		this.moveSpeed = 3;//�ӵ��ƶ��ٶ�
//		this.fx = fx;
//	}
	
	//�Դ����ö���Ĺ��̽��з�װ�����ֻ��Ҫ�����Ҫ��Լ������������ֵΪ����ʵ��
	@Override	//{x:3,y:5,fx:up} ����
	public ElementObj createElement(String str) {//�����ַ����Ĺ���
		String[] split = str.split(",");
		for(String str1 : split) {
			String[] split2 = str1.split(":");//0�±���x y fx 1�±���ֵ
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
	 * �����ӵ���˵��1.���߽� 2.��ײ 3.��ҷű���(ȫ�����ӵ�...)
	 * ����ʽ���ǣ����ﵽ����������ʱ��ֻ�����޸�����״̬�Ĳ���
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
