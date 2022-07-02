package com.tedu.element;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
/**
 * @˵�� ���̼��������ǲ������ƶ������͵���
 * @author lenovo
 *
 */
public class Maps extends ElementObj{
	private ElementManager em = ElementManager.getManager();
	List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
	private int moveV=3;//��ͼ�ƶ��ٶ�
	private Boolean backgroudMove=false; //��ͼ�ƶ����
	private String fx="right";//��ͼ�ƶ�����
	public Maps(){}//Ĭ�Ϲ��캯��
	public Maps(int x, int y, int w, int h, ImageIcon icon){//�������Ĺ���ʺ���
		super(x, y, w, h, icon);
	}
	/**
	 * @˵�� ���Ƶ�ͼ����ͼƬ
	 */
	@Override
	public void showElement(Graphics g) {
		//���Ʊ���
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY()-10, this.getIcon().getIconWidth(), 324, null);
	}
	/**
	 * @˵�� ֻҪ���¾ͼ������̵İ�����ֻ��������Ҽ��ɣ����²����κδ������ɿ����������ͼ�ƶ�����ֹͣ
	 */
	@Override
	public void keyClick(boolean b1, int key) {
		if(b1)//���°���
		{
			switch(key){
			case 65://A
				backgroudMove = true;
				this.fx = "left";
				break;
			case 68://D
				backgroudMove = true;
				this.fx = "right";
				break;
			default:
				break;
				
			}
		}
		else//�ɿ�����
		{
			switch(key) {
				case 65://A
//					System.out.println("�ɿ�A");
					if(this.fx != "right")
						backgroudMove = false;
					break;
				case 68://D
//					System.out.println("�ɿ�D");
					if(this.fx != "left")
						backgroudMove = false;
					break;
				default:
					break;
			}
		}
		
	}
	/**
	 * @˵�� ��ͼ�ƶ�˵��
	 */
	@Override
	protected void move(long gameTime) {
		if(backgroudMove){
			//����ͼƬ�߽�
			if(this.getX() < -600) {
				this.setX(-600);
			}
			if(this.getX()>-5){
				this.setX(-5);
			}
			
			if(this.fx.equals("left")) {
				this.setX(this.getX() + 0);//�Ĺ�0->distance
			}
			if(!play.isEmpty()) {
				if(this.fx.equals("right") && play.get(0).getX()>=390 && this.getX() > -600) {
					this.setX(this.getX() - this.moveV);//�Ĺ�0->distance
				}
			}
		}
	}
	/**
	 * @˵�� �ַ�����ʽΪ x,y,fx
	 */
	@Override
	public ElementObj createElement(String str) {
		ImageIcon img = new ImageIcon("image/backgroud/backimage1.gif");
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		this.fx = split[2];
		this.setIcon(img);
		return this;
	}
	
	public String getFx() {
		return fx;
	}
		
}
