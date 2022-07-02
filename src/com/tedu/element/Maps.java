package com.tedu.element;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.ImageIcon;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
/**
 * @说明 键盘监听，主角不动，移动背景和敌人
 * @author lenovo
 *
 */
public class Maps extends ElementObj{
	private ElementManager em = ElementManager.getManager();
	List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
	private int moveV=3;//地图移动速度
	private Boolean backgroudMove=false; //地图移动标记
	private String fx="right";//地图移动方向
	public Maps(){}//默认构造函数
	public Maps(int x, int y, int w, int h, ImageIcon icon){//带参数的构造甘函数
		super(x, y, w, h, icon);
	}
	/**
	 * @说明 绘制地图背景图片
	 */
	@Override
	public void showElement(Graphics g) {
		//绘制背景
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY()-10, this.getIcon().getIconWidth(), 324, null);
	}
	/**
	 * @说明 只要按下就监听键盘的按键，只需监听左右即可，上下不做任何处理；当松开按键，则地图移动立刻停止
	 */
	@Override
	public void keyClick(boolean b1, int key) {
		if(b1)//按下按键
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
		else//松开按键
		{
			switch(key) {
				case 65://A
//					System.out.println("松开A");
					if(this.fx != "right")
						backgroudMove = false;
					break;
				case 68://D
//					System.out.println("松开D");
					if(this.fx != "left")
						backgroudMove = false;
					break;
				default:
					break;
			}
		}
		
	}
	/**
	 * @说明 地图移动说明
	 */
	@Override
	protected void move(long gameTime) {
		if(backgroudMove){
			//限制图片边界
			if(this.getX() < -600) {
				this.setX(-600);
			}
			if(this.getX()>-5){
				this.setX(-5);
			}
			
			if(this.fx.equals("left")) {
				this.setX(this.getX() + 0);//改过0->distance
			}
			if(!play.isEmpty()) {
				if(this.fx.equals("right") && play.get(0).getX()>=390 && this.getX() > -600) {
					this.setX(this.getX() - this.moveV);//改过0->distance
				}
			}
		}
	}
	/**
	 * @说明 字符串形式为 x,y,fx
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
