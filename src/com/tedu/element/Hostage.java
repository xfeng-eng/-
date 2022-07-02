package com.tedu.element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Hostage extends ElementObj{
	private ElementManager em = ElementManager.getManager();
	List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
	List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
	private Boolean backgroudMove=false; //��ͼ�ƶ����
	private String fx="right";//��ͼ�ƶ�����
	private int moveV=3 ;
	private long imgtime1 = 0;//standͼƬ
	private int imgNum1 = 0;
	
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	public Hostage(){}
	public Hostage(int x, int y, int w, int h, ImageIcon icon) {
			super(x, y, w, h, icon);
		}
	@Override  //"200,200,enemyType,MoveV,attackNum,Hp,attackDistance"
	public ElementObj createElement(String str) {
				//x,y,fx,icon,
				String[] split = str.split(",");
				this.setX(Integer.parseInt(split[0]));
				this.setY(Integer.parseInt(split[1]));
				this.setMoveV(Integer.parseInt(split[2]));
				
			
				
				this.setHP(Integer.parseInt(split[3]));//����Ѫ����Ĭ��Ѫ��5(�ɸ�)
				
				String key1 = "hostage_stand";			
				List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//��ȡ����runͼƬ
				ImageIcon icon=listImageIcon1.get(0);				
				this.setIcon(icon);
				this.setW(icon.getIconWidth());
				this.setH(icon.getIconHeight());
				return this; //ע������� �ոշ��ص��Ǹ���
			}

	//���ŵ�ͼ�ƶ�
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
		if(this.getHP() > 0) {
			if(backgroudMove){
				this.setX(map.get(0).getX()+840);
			}
		}
	}
	
	@Override
	public void stand(long gameTime){
		if(!this.isLive()){
//			System.out.println("stand");
			return;
		}
		
			 String key1 = "hostage_stand";	
			 List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//��ȡ����standͼƬ
			 if(gameTime -this.imgtime1>30) {
					
					this.imgtime1=gameTime;
					
					if(this.imgNum1>listImageIcon1.size()-1) {
						this.imgNum1=0;
		
					}
					
				
					ImageIcon icon=listImageIcon1.get(imgNum1++);
					this.setIcon(icon);
				
					}
					
	}

	
	@Override
	public void die(long gameTime){
		String key1 = "hostage_die"; 
		   
		List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//��ȡ����dieͼƬ
		   
		ElementManager em = ElementManager.getManager();
		ImageIcon icon2 = listImageIcon1.get(0);;
		ElementObj obj = new Die(this.getX(),this.getY(),this.getW(),this.getH(),icon2,key1,"hostage");
		em.addElement(obj,GameElement.DIE);	
	}
	public int getMoveV() {
		return moveV;
	}
	public void setMoveV(int moveV) {
		this.moveV = moveV;
	}
	
}
