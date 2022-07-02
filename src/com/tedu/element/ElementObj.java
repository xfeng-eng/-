package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

/**
 * ˵��������Ԫ�صĻ���
 * @author lenovo
 *
 */
public abstract class ElementObj {
	private int x;			//Ԫ��xֵ
	private int y;			//Ԫ��yֵ
	private int w;			//Ԫ�ؿ��
	private int h;			//Ԫ�ظ߶�
	private ImageIcon icon;//Ԫ��ͼƬ
	private boolean live = true;//Ԫ������״̬live true-���� false-������ ���Բ���ö��ֵ����������״̬(���棬�����������޵�)
	private int HP = 10;//Ԫ��Ѫ��HP��Ĭ��Ѫ��Ϊ10(�ɸ�)
	
	public ElementObj() {}
	
	/**
	 * @˵�� �������Ĺ��췽�������������ഫ�����ݵ�����
	 * @param x ���Ͻ�x����
	 * @param y ���Ͻ�y����
	 * @param w ���
	 * @param h �߶�
	 * @param icon ͼƬ
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
	 * @˵�� ���󷽷� ��ʾԪ��
	 * @param g ���� ���ڽ��л滭
	 */
	public abstract void showElement(Graphics g);
	
	/**
	 * @˵�� ���̰�ť��Ӧ�¼�����(��ǿ����д����)
	 * @param b1 ��������� true�����£�false�����ɿ�
	 * @param key �������ļ��̵�keyCodeֵ
	 */
	public void keyClick(boolean b1, int key) {
		System.out.println("����ʹ��");
	}
	
	
	
	/**
	 * @���ģʽ ģ��ģʽ����ģ��ģʽ�ж��� ����ִ�з������Ⱥ�˳��������ѡ������д�÷���
	 * 			1.�ƶ�  2.��װ  3.�ӵ�����
	 */
	public final void model(long gameTime) {
		//�Ȼ�װ
		updateImage(gameTime);
		//���ƶ�
		move(gameTime);
		//�����ӵ�
		add(gameTime);
//      ���
		attack(gameTime);
//		վ��
		stand(gameTime);
//      ״̬�л�
		stateSwitch(gameTime);
	}
	
	/**
	 * @˵�� ��װ��������Ҫ��װ�����࣬��ʵ�ִ˷���
	 */
	protected void updateImage(long gameTime) {//long ... aaa ���������飬������÷�������N��long���͵�����
		
	}
	
	/**
	 * @˵�� �ƶ���������Ҫ�ƶ������࣬��ʵ�ִ˷���
	 */
	protected void move(long gameTime) {
		
	}
	
	/**
	 * @˵�� ��ӵ��߷�������Ҫ��ӵ��ߵ����࣬��ʵ�ִ˷���
	 */
	protected void add(long gameTime) {
		
	}
	
	//�������������������̳е�
	public void attack(long gameTime){}
	
	//վ������ͼƬ
	public void stand(long gameTime){}
	
	//
	public void stateSwitch(long gameTime){}
	
	/**
	 * @˵�� ������������Ҫʵ��������Ч�ȵ����࣬��ʵ�ִ˷���
	 */
	public void die(long gameTime) {
		
	}
	
	public ElementObj createElement(String str) {
		return null;
	}
	
	/**
	 * @˵�� ����Ԫ�ص���ײ���ζ��󷽷�
	 * @return Rectangle Ԫ�ص���ײ���ζ���(ʵʱ����)
	 */
	public Rectangle getRectangle() {
		//���Խ������ݽ��д���
		return new Rectangle(x,y,w,h);
	}
	
	/**
	 * @˵�� ��ײ��ⷽ��
	 * @param obj ��ײ����Ԫ�ض���
	 * @return boolean ����true˵������ײ������false˵��û����ײ
	 */
	public boolean pk(ElementObj obj) {
		return this.getRectangle().intersects(obj.getRectangle());
	}
	
	/**
	 * @˵�� �����������������౻���������븸�಻һ�£�����Ҫ��д�˷���
	 */
	public void beAttacted(int attack) {
		this.HP -= attack;
		if(this.HP <= 0) {
			//����������Ч
			this.setLive(false);
		}
	}
	
	/**
	 * ֻҪ��VO�� ��ҪΪ��������set��get����
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
