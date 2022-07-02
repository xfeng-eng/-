package com.tedu.element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Prop extends ElementObj{
	private ElementManager em = ElementManager.getManager();
	List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
	private String propType;//道具种类
	private int treatment;//治疗量
	private int mapX = 0;
	private int moveV=3;//地图移动速度
	private Boolean backgroudMove=false; //地图移动标记
	private String fx="right";//地图移动方向
	
	
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	public Prop(){}
	public Prop(int x, int y, int w, int h, ImageIcon icon) {
			super(x, y, w, h, icon);	
	}

	@Override
	protected void move(long gameTime) {
		if(!map.isEmpty()) {
			int x = map.get(0).getX();
			if(this.mapX != x) {
				this.mapX = x;
				switch(((Maps)map.get(0)).getFx()) {
					case "left":
						this.setX(this.getX() + this.moveV);
						break;
					case "right":
						this.setX(this.getX() - this.moveV);
						break;
					default:
						break;
				}
			}
		}
	}
	
	@Override  //"200,200,propType"
	public ElementObj createElement(String str) {
				//x,y,fx,icon,
				String[] split = str.split(",");
				this.setX(Integer.parseInt(split[0]));
				this.setY(Integer.parseInt(split[1]));
				this.setPropType(split[2]);
				this.setTreatment(Integer.parseInt(split[3]));
				
				String key1 = "prop_"+this.propType;			
				List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//获取敌人run图片
				ImageIcon icon=listImageIcon1.get(0);				
				this.setIcon(icon);
				this.setW(icon.getIconWidth());
				this.setH(icon.getIconHeight());
				System.out.println("prop: "+this.getX()+" "+this.getY()+" "+this.getW()+" "+this.getH());
				return this; //注意别忘啦 刚刚返回的是父类
			}
	
	
	
	public String getPropType() {
		return propType;
	}
	public void setPropType(String propType) {
		this.propType = propType;
	}
	public int getTreatment() {
		return treatment;
	}
	public void setTreatment(int treatment) {
		this.treatment = treatment;
	}
	
}
