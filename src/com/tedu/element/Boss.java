package com.tedu.element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Boss extends ElementObj{
	private ElementManager em = ElementManager.getManager();
	 List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
	 List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
	
	private ImageIcon iconBotton;
	private int iconBottonX;
	private int iconBottonY;
	private ImageIcon iconMiddle;
	private int iconMiddleX;
	private int iconMiddleY;
	private ImageIcon lunzi1;
	private int lunzi1X;
	private int lunzi1Y;
	private ImageIcon lunzi2;
	private int lunzi2X;
	private int lunzi2Y;
	private ImageIcon iconTop;
	private int TopX;
	private int TopY;
	
	
	private String enemyType;//敌人种类，控制不同种类的敌人
	private int moveV;//移动速度
	private int attackNum;//子弹速度？
	private int HP;//血量
	private long attackDistance;//敌人的攻击距离，玩家在攻击距离内就attcak
	private String fx = "left";//方向
	 private boolean attack = false;//攻击状态判定
	 private boolean stand = false;//站立状态判定
	 private boolean move = true;//移动状态判定
	 private String fireType = "bomb2";//子弹种类，控制不同种类的子弹
	 private long imgtime1=0;//用于控制敌人移动图片变化速度
	 private long imgtime2=0;//用于控制敌人射击图片变化速度
	 private long imgtime3=0;//用于控制敌人站立图片变化速度
	 private int imgNum1 = 0;//用于控制敌人移动（move）时的图片下标
	 private int imgNum2 = 0;//用于控制敌人射击（attcak）时的图片下标
	 private int imgNum3 = 0;//用于控制敌人站立（stand）时图片的下标
	 private int imgNum4 = 0;
	 private long attacktime = 0;//用于控制敌人射击时间
	 private int jpanlx1 = 0;//界面的x坐标左侧
	 private int jpanlx2 = 800;//界面的x坐标右侧
	@Override
	public void showElement(Graphics g) {
		
		
		g.drawImage(this.getIconBotton().getImage(), 
				this.getIconBottonX(), this.getIconBottonY(), 
				this.getW(), this.getH(), null);
		
	
		g.drawImage(this.getIconMiddle().getImage(), 
				this.getIconMiddleX(), this.getIconMiddleY(), 
				this.getW(), this.getH(), null);
		
		g.drawImage(this.getIconTop().getImage(), 
				this.getTopX(), this.getTopY(), 
				40, 10, null);
		g.drawImage(this.getLunzi1().getImage(), 
				this.getLunzi1X(), this.getLunzi1Y(), 
				63, 36, null);
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	
	}
	@Override  //"200,200,enemyType,MoveV,attackNum,Hp,attackDistance"
	public ElementObj createElement(String str) {
				//x,y,fx,icon,
				String[] split = str.split(",");
				this.setX(Integer.parseInt(split[0]));
				this.setY(Integer.parseInt(split[1]));
				this.setEnemyType(split[2]);
				this.setMoveV(Integer.parseInt(split[3]));
				this.setAttackNum(Integer.parseInt(split[4]));//敌人移动速度		
				this.setHP(Integer.parseInt(split[5]));//敌人血量，默认血量5(可改)
				this.setAttackDistance(Integer.parseInt(split[6]));
				this.setFx(split[7]);
				//paitai
				String key1 = this.enemyType+"_"+"paotai";			
				List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);			
				ImageIcon icon=listImageIcon1.get(0);
				this.setIcon(icon);
				this.setW(icon.getIconWidth());
				this.setH(icon.getIconHeight());
				//middle
				String key3 = this.enemyType+"_"+"middle";
				List<ImageIcon> listImageIcon3 = GameLoad.imgMap_element.get(key3);			
				ImageIcon iconMiddle=listImageIcon3.get(0);
				this.setIconMiddle(iconMiddle);
				this.setIconMiddleX(this.getX()+20);
				this.setIconMiddleY(this.getY()+40);
				//botton
				String key2 = this.enemyType+"_"+"botton";
				List<ImageIcon> listImageIcon2 = GameLoad.imgMap_element.get(key2);			
				ImageIcon iconBotton=listImageIcon2.get(0);
				this.setIconBotton(iconBotton);
				this.setIconBottonX(this.getX()+20);
				this.setIconBottonY(this.getY()+60);
				//top
				String key4 = this.enemyType+"_"+"top";
				List<ImageIcon> listImageIcon4 = GameLoad.imgMap_element.get(key4);				
				ImageIcon iconTop=listImageIcon4.get(0);
				this.setIconTop(iconTop);
				this.setTopX(this.getX()+90);
				this.setTopY(this.getY()-10);
				//lunzi1
				String key5 = this.enemyType+"_"+"lunzi1";
				List<ImageIcon> listImageIcon5 = GameLoad.imgMap_element.get(key5);				
				ImageIcon lunzi1=listImageIcon5.get(0);
				this.setLunzi1(lunzi1);
				this.setLunzi1X(this.getX()+140);
				this.setLunzi1Y(this.getY()+30);
				this.setHP(2);
				
				System.out.println(key2);
				switch(this.enemyType){
				case "enemyGun":this.setFireType("gunBullet");break;
				case "enemyRPG":this.setFireType("RPGBullet");break;
				case "tank":this.setFireType("tankBullet");break;
				
				}
			
				
				
				return this; //注意别忘啦 刚刚返回的是父类
			}
	
	public void stateSwitch(long gameTime){
//		Random r = new Random();
//		int ran = r.nextInt(250)+150;//生成随机数，每个敌人的攻击间隔随机
		if(play.isEmpty()){
			return;
		}
		if((this.getX()-play.get(0).getX()<this.attackDistance) && (this.getX()>=jpanlx1 ) && (this.getX()<=jpanlx2) ){//敌人与玩家距离小于攻击距离时，停止移动
			
			
			this.move = false;
			this.stand = true;
			
		}
		if(gameTime-this.attacktime>400 && this.getX()-play.get(0).getX()<this.attackDistance && (this.getX()>=jpanlx1 ) && (this.getX()<=jpanlx2)){
		
			this.attacktime = gameTime;
			this.attack = true;
//			this.move = false;
			this.stand = false;
		}

		return;
		
	}
	
	


	
	@Override
	public void  move(long gameTime){
		
		if(!this.move ){//如果不是move状态，return
			return;
		}		
		switch(this.fx){
			case "left":
				this.setX(this.getX()-this.moveV);
				this.setIconBottonX(this.getIconBottonX()-this.moveV);
				this.setIconMiddleX(this.getIconMiddleX()-this.moveV);
				this.setTopX(this.getTopX()-this.moveV);
				this.setLunzi1X(this.getLunzi1X()-this.moveV);
				this.setLunzi2X(this.getLunzi2X()-this.moveV);
				
				return;
			case "right":this.setX(this.getX()+this.moveV);return;
		}

	}
	@Override
	protected void updateImage(long gameTime) {
		if(move == false){
			return;
		}
		 String key1 = this.enemyType+"_botton";	
		 List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//获取敌人attack图片
		 
		 String key2 = this.enemyType+"_lunzi1";	
		 List<ImageIcon> listImageIcon2 = GameLoad.imgMap_element.get(key2);//获取敌人attack图片
		
		if(gameTime -this.imgtime1>10) {
			
			this.imgtime1=gameTime;
			
			if(this.imgNum1>listImageIcon1.size()-1) {
				this.imgNum1=0;
				
			}
			if(this.imgNum2>listImageIcon2.size()-1) {
				
				this.imgNum2=0;
			}
			switch(this.fx){
			case "left":
				ImageIcon icon=listImageIcon1.get(this.imgNum1++);
				this.setIconBotton(icon);
				ImageIcon icon2=listImageIcon2.get(this.imgNum2++);
				this.setLunzi1(icon2);
				System.out.println(icon2);
				return;
			case "right":ImageIcon icon1=listImageIcon1.get(this.imgNum1++);
			this.setIcon(icon1);return;
			
			}
			
		}
		if(gameTime -this.imgtime1>10) {
			
			this.imgtime1=gameTime;
			
			if(this.imgNum1>listImageIcon1.size()-1) {
				this.imgNum1=0;
				this.imgNum2=0;
			}
			switch(this.fx){
			case "left":
				ImageIcon icon=listImageIcon1.get(this.imgNum1++);
				this.setIconBotton(icon);
				ImageIcon icon2=listImageIcon2.get(this.imgNum2++);
				this.setIconTop(icon2);
				return;
			case "right":ImageIcon icon1=listImageIcon1.get(this.imgNum1++);
			this.setIcon(icon1);return;
			
			}
			
		}
		
	}
	
	
	
	@Override
	public void attack(long gameTime){
		if(!this.attack){//如果不是攻击状态直接return
			return;
		}
	
		
		if(gameTime -this.imgtime2>10) {			
			this.imgtime2=gameTime;
			
			
				this.attack = false;
				this.move = false;
				this.stand = true;
				this.enemyFireAdd();
		}
	
	}
	
	
	@Override
	public void die(long gameTime){
		String key1 = this.enemyType+"_die"; 
		   
		List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//鑾峰彇鏁屼汉die鍥剧墖
		   
		ElementManager em = ElementManager.getManager();
		ImageIcon icon2 = listImageIcon1.get(0);;
		ElementObj obj = new Die(this.getX(),this.getY(),this.getW(),this.getH(),icon2,key1,"tank");
		em.addElement(obj,GameElement.DIE);
	}
	@Override
	public void stand(long gameTime){
		if(!this.stand){
			return;
		}
		
			 String key1 = this.enemyType+"_botton";	
			 List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//获取敌人stand图片
			 
			 String key2 = this.enemyType+"_lunzi1";	
			 List<ImageIcon> listImageIcon2 = GameLoad.imgMap_element.get(key2);//获取敌人attack图片
			 
			 if(gameTime -this.imgtime3>30) {					
					this.imgtime3=gameTime;
					if(this.imgNum3>listImageIcon1.size()-1) {
						this.imgNum3=0;		
					}
					if(this.imgNum4>listImageIcon1.size()-1) {
						this.imgNum4=0;		
					}
					switch(this.fx){
					case "left":
						ImageIcon icon=listImageIcon1.get(imgNum3++);
						this.setIconBotton(icon);
						ImageIcon icon2=listImageIcon2.get(imgNum4++);
						this.setLunzi1(icon2);
						
						
						
						break;
					case "right":ImageIcon icon1=listImageIcon1.get(imgNum3++);
					this.setIcon(icon1);break;
					}
					
				}
			
		
	}
	public void enemyFireAdd() {//有啦时间就可以进行控制
		ElementObj obj=GameLoad.getObj("enemyfire");  		
		ElementObj element = obj.createElement(fireStr());
//		System.out.println("子弹是否为空"+element);
//		装入到集合中
		ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
//		如果要控制子弹速度等等。。。。还需要代码编写
	}
	 public String fireStr(){
		  //{x:3,y:5,f:up,t:fireType}
			int x = this.getX();
			int y = this.getY();
			
			
		  return "x:"+x+",y:"+y+",f:"+this.fx+",t:"+this.fireType+",w:"+this.getW()+",h:"+this.getH();
		 }
	
	public int getIconBottonX() {
		return iconBottonX;
	}
	public void setIconBottonX(int iconBottonX) {
		this.iconBottonX = iconBottonX;
	}
	public int getIconBottonY() {
		return iconBottonY;
	}
	public void setIconBottonY(int iconBottonY) {
		this.iconBottonY = iconBottonY;
	}
	public int getIconMiddleX() {
		return iconMiddleX;
	}
	public void setIconMiddleX(int iconMiddleX) {
		this.iconMiddleX = iconMiddleX;
	}
	public int getIconMiddleY() {
		return iconMiddleY;
	}
	public void setIconMiddleY(int iconMiddleY) {
		this.iconMiddleY = iconMiddleY;
	}
	public ImageIcon getIconBotton() {
		return iconBotton;
	}
	public void setIconBotton(ImageIcon iconBotton) {
		this.iconBotton = iconBotton;
	}
	public ImageIcon getIconMiddle() {
		return iconMiddle;
	}
	public void setIconMiddle(ImageIcon iconMiddle) {
		this.iconMiddle = iconMiddle;
	}
	public ImageIcon getLunzi1() {
		return lunzi1;
	}
	public void setLunzi1(ImageIcon lunzi1) {
		this.lunzi1 = lunzi1;
	}
	public ImageIcon getLunzi2() {
		return lunzi2;
	}
	public void setLunzi2(ImageIcon lunzi2) {
		this.lunzi2 = lunzi2;
	}
	public String getEnemyType() {
		return enemyType;
	}
	public void setEnemyType(String enemyType) {
		this.enemyType = enemyType;
	}
	public int getMoveV() {
		return moveV;
	}
	public void setMoveV(int moveV) {
		this.moveV = moveV;
	}
	public int getAttackNum() {
		return attackNum;
	}
	public void setAttackNum(int attackNum) {
		this.attackNum = attackNum;
	}

	public long getAttackDistance() {
		return attackDistance;
	}
	public void setAttackDistance(long attackDistance) {
		this.attackDistance = attackDistance;
	}
	public String getFx() {
		return fx;
	}
	public void setFx(String fx) {
		this.fx = fx;
	}
	public long getImgtime1() {
		return imgtime1;
	}
	public void setImgtime1(long imgtime1) {
		this.imgtime1 = imgtime1;
	}
	public long getImgtime2() {
		return imgtime2;
	}
	public void setImgtime2(long imgtime2) {
		this.imgtime2 = imgtime2;
	}
	public long getImgtime3() {
		return imgtime3;
	}
	public void setImgtime3(long imgtime3) {
		this.imgtime3 = imgtime3;
	}
	public int getImgNum1() {
		return imgNum1;
	}
	public void setImgNum1(int imgNum1) {
		this.imgNum1 = imgNum1;
	}
	public int getImgNum2() {
		return imgNum2;
	}
	public void setImgNum2(int imgNum2) {
		this.imgNum2 = imgNum2;
	}
	public int getImgNum3() {
		return imgNum3;
	}
	public void setImgNum3(int imgNum3) {
		this.imgNum3 = imgNum3;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public String getFireType() {
		return fireType;
	}
	public void setFireType(String fireType) {
		this.fireType = fireType;
	}
	public boolean isAttack() {
		return attack;
	}
	public void setAttack(boolean attack) {
		this.attack = attack;
	}
	public boolean isStand() {
		return stand;
	}
	public void setStand(boolean stand) {
		this.stand = stand;
	}
	public boolean isMove() {
		return move;
	}
	public void setMove(boolean move) {
		this.move = move;
	}
	public ElementManager getEm() {
		return em;
	}
	public void setEm(ElementManager em) {
		this.em = em;
	}
	public List<ElementObj> getPlay() {
		return play;
	}
	public void setPlay(List<ElementObj> play) {
		this.play = play;
	}
	public List<ElementObj> getMap() {
		return map;
	}
	public void setMap(List<ElementObj> map) {
		this.map = map;
	}
	public int getLunzi1X() {
		return lunzi1X;
	}
	public void setLunzi1X(int lunzi1x) {
		lunzi1X = lunzi1x;
	}
	public int getLunzi1Y() {
		return lunzi1Y;
	}
	public void setLunzi1Y(int lunzi1y) {
		lunzi1Y = lunzi1y;
	}
	public int getLunzi2X() {
		return lunzi2X;
	}
	public void setLunzi2X(int lunzi2x) {
		lunzi2X = lunzi2x;
	}
	public int getLunzi2Y() {
		return lunzi2Y;
	}
	public void setLunzi2Y(int lunzi2y) {
		lunzi2Y = lunzi2y;
	}
	public ImageIcon getIconTop() {
		return iconTop;
	}
	public void setIconTop(ImageIcon iconTop) {
		this.iconTop = iconTop;
	}
	public int getTopX() {
		return TopX;
	}
	public void setTopX(int topX) {
		TopX = topX;
	}
	public int getTopY() {
		return TopY;
	}
	public void setTopY(int topY) {
		TopY = topY;
	}
	public long getAttacktime() {
		return attacktime;
	}
	public void setAttacktime(long attacktime) {
		this.attacktime = attacktime;
	}
	public int getJpanlx1() {
		return jpanlx1;
	}
	public void setJpanlx1(int jpanlx1) {
		this.jpanlx1 = jpanlx1;
	}
	public int getJpanlx2() {
		return jpanlx2;
	}
	public void setJpanlx2(int jpanlx2) {
		this.jpanlx2 = jpanlx2;
	}
	public int getImgNum4() {
		return imgNum4;
	}
	public void setImgNum4(int imgNum4) {
		this.imgNum4 = imgNum4;
	}
	
	
}
