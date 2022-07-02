package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * @˵�� ����ӵ��࣬�����ʵ�����������Ҷ�����úʹ���
 * @author YHCNE
 * @����Ŀ�������
 *   1.�̳���Ԫ�ػ���;��дshow����
 *   2.��������ѡ������д�����������磺move��
 *   3.˼���������������е�����
 */
public class EnemyFire extends ElementObj{
	private int attack = 1;//������
	private int moveNum=3;//�ƶ��ٶ�ֵ
	private int enemyIconWeight;
	private int enemyIconHigh;
	private String fx;//����
	private String fireType;
	private boolean isFire = false;//�ж��Ƿ����ӵ���Ĭ��Ϊfalse�������ı�Ϊtrueֹͣ�����ӵ���ÿ��attcakʱ�ı�Ϊfalse
	
	
//	ʣ�µĴ����չ; ������չ�������ӵ��� ���⣬�����ȵȡ�(��������Ҫ���ӵ�����)
	public EnemyFire() {}//һ���յĹ��췽��
//	�Դ����������Ĺ��̽��з�װ�����ֻ��Ҫ�����Ҫ��Լ������������ֵ���Ƕ���ʵ��
	@Override   //{X:3,y:5,f:up}
	public  ElementObj createElement(String str) {//�����ַ����Ĺ���
		
		String[] split = str.split(",");
		for(String str1 : split) {//X:3
			String[] split2 = str1.split(":");// 0�±� �� x,y,f   1�±���ֵ
			switch(split2[0]) {
			case "x": this.setX(Integer.parseInt(split2[1]));break;
			case "y":this.setY(Integer.parseInt(split2[1]));break;
			case "f":this.fx=split2[1];break;
			case "t":
				 this.fireType = split2[1];
				 String key1 = this.fireType;	
				 List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//��ȡ����attackͼƬ
				 if(this.fx.equals("left"))this.setIcon(listImageIcon1.get(0));
				 if(this.fx.equals("right"))this.setIcon(listImageIcon1.get(1));break;
			case "w":this.setEnemyIconWeight(Integer.parseInt(split2[1]));break;
			case "h":this.setEnemyIconHigh(Integer.parseInt(split2[1]));break;
			
			}
		} 
		this.setW(this.getIcon().getIconWidth());
		this.setH(this.getIcon().getIconHeight());
		//�����ӵ�����ͷ���Ĳ�ͬ�����ӵ����ֵ�λ��
		switch(this.fireType){
		
		case"gunBullet":
			switch(this.fx){
			case "right":this.setX(this.getX()+this.getEnemyIconWeight());
				this.setY(this.getY()+ this.getEnemyIconHigh()/2-10);break;
			case "left":this.setX(this.getX());
				this.setY(this.getY()+ this.getEnemyIconHigh()/2-10);break;
		
		}
						
		case"RPGBullet":
			switch(this.fx){
			case "right":this.setX(this.getX()+this.getEnemyIconWeight());
				this.setY(this.getY()+ this.getEnemyIconHigh()/2-30);break;
			case "left":this.setX(this.getX());
				this.setY(this.getY()+ this.getEnemyIconHigh()/2-30);break;
		}
						
		}
		//System.out.println("enemy: "+this.getX()+" "+this.getY()+" "+this.getW()+" "+this.getH());
		return this;
	}
	
	@Override
	public void showElement(Graphics g) {	
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
//		g.setColor(Color.red);// new Color(255,255,255)
//		g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());
	}	
	@Override
	protected void move(long gameTime) {
		if(this.getX()<0 || this.getX() >900 || 
				this.getY() <0 || this.getY()>600) {
			this.setLive(false);
			return;
		}
		switch(this.fx) {
		case "up": this.setY(this.getY()-this.moveNum);break;
		case "left": this.setX(this.getX()-this.moveNum);break;
		case "right": this.setX(this.getX()+this.moveNum);break;
		case "down": this.setY(this.getY()+this.moveNum);break;
		}
		
	}
	
	
	/**
	 * �����ӵ���˵��1.���߽�  2.��ײ  3.��ҷű���
	 * ����ʽ���ǣ����ﵽ����������ʱ��ֻ���� �޸�����״̬�Ĳ�����
	 */
//	@Override
//	public void die() {
//		ElementManager em=ElementManager.getManager();
//		ImageIcon icon=new ImageIcon("image/tank/play2/player2_up.png");
//		ElementObj obj=new Play(this.getX(),this.getY(),50,50,icon);//ʵ��������
////		��������뵽 Ԫ�ع�������
////		em.getElementsByKey(GameElement.PLAY).add(obj);
//		em.addElement(obj,GameElement.DIE);//ֱ�����
//	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getMoveNum() {
		return moveNum;
	}
	public void setMoveNum(int moveNum) {
		this.moveNum = moveNum;
	}
	public String getFx() {
		return fx;
	}
	public void setFx(String fx) {
		this.fx = fx;
	}
	public int getEnemyIconWeight() {
		return enemyIconWeight;
	}
	public void setEnemyIconWeight(int enemyIconWeight) {
		this.enemyIconWeight = enemyIconWeight;
	}
	public int getEnemyIconHigh() {
		return enemyIconHigh;
	}
	public void setEnemyIconHigh(int enemyIconHigh) {
		this.enemyIconHigh = enemyIconHigh;
	}
	public String getFireType() {
		return fireType;
	}
	public void setFireType(String fireType) {
		this.fireType = fireType;
	}
	public boolean isFire() {
		return isFire;
	}
	public void setFire(boolean isFire) {
		this.isFire = isFire;
	}
	
//    /**�ӵ���װ*/
//	private long time=0;
//	protected void updateImage(long gameTime) {
//		if(gameTime-time>5) {
//			time=gameTime;//Ϊ�´α�װ��׼��
//			this.setW(this.getW()+2);
//			this.setH(this.getH()+2);
////			���ͼƬ��������
//		}
//	}
	
	
}





