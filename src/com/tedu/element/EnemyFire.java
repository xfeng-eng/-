package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * @说明 玩家子弹类，本类的实体对象是由玩家对象调用和创建
 * @author YHCNE
 * @子类的开发步骤
 *   1.继承与元素基类;重写show方法
 *   2.按照需求选择性重写其他方法例如：move等
 *   3.思考并定义子类特有的属性
 */
public class EnemyFire extends ElementObj{
	private int attack = 1;//攻击力
	private int moveNum=3;//移动速度值
	private int enemyIconWeight;
	private int enemyIconHigh;
	private String fx;//方向
	private String fireType;
	private boolean isFire = false;//判断是否发射子弹，默认为false，发射后改变为true停止发射子弹，每次attcak时改变为false
	
	
//	剩下的大家扩展; 可以扩展出多种子弹： 激光，导弹等等。(玩家类就需要有子弹类型)
	public EnemyFire() {}//一个空的构造方法
//	对创建这个对象的过程进行封装，外界只需要传输必要的约定参数，返回值就是对象实体
	@Override   //{X:3,y:5,f:up}
	public  ElementObj createElement(String str) {//定义字符串的规则
		
		String[] split = str.split(",");
		for(String str1 : split) {//X:3
			String[] split2 = str1.split(":");// 0下标 是 x,y,f   1下标是值
			switch(split2[0]) {
			case "x": this.setX(Integer.parseInt(split2[1]));break;
			case "y":this.setY(Integer.parseInt(split2[1]));break;
			case "f":this.fx=split2[1];break;
			case "t":
				 this.fireType = split2[1];
				 String key1 = this.fireType;	
				 List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//获取敌人attack图片
				 if(this.fx.equals("left"))this.setIcon(listImageIcon1.get(0));
				 if(this.fx.equals("right"))this.setIcon(listImageIcon1.get(1));break;
			case "w":this.setEnemyIconWeight(Integer.parseInt(split2[1]));break;
			case "h":this.setEnemyIconHigh(Integer.parseInt(split2[1]));break;
			
			}
		} 
		this.setW(this.getIcon().getIconWidth());
		this.setH(this.getIcon().getIconHeight());
		//根据子弹种类和方向的不同调整子弹出现的位置
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
	 * 对于子弹来说：1.出边界  2.碰撞  3.玩家放保险
	 * 处理方式就是，当达到死亡的条件时，只进行 修改死亡状态的操作。
	 */
//	@Override
//	public void die() {
//		ElementManager em=ElementManager.getManager();
//		ImageIcon icon=new ImageIcon("image/tank/play2/player2_up.png");
//		ElementObj obj=new Play(this.getX(),this.getY(),50,50,icon);//实例化对象
////		讲对象放入到 元素管理器中
////		em.getElementsByKey(GameElement.PLAY).add(obj);
//		em.addElement(obj,GameElement.DIE);//直接添加
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
	
//    /**子弹变装*/
//	private long time=0;
//	protected void updateImage(long gameTime) {
//		if(gameTime-time>5) {
//			time=gameTime;//为下次变装做准备
//			this.setW(this.getW()+2);
//			this.setH(this.getH()+2);
////			你变图片不就完啦
//		}
//	}
	
	
}





