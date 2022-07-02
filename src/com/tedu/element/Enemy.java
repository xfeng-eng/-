package com.tedu.element;

import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Enemy extends ElementObj{
	private ElementManager em = ElementManager.getManager();
	 List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
	 List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
	
	 private String enemyType;//敌人种类，控制不同种类的敌人
	  private long imgtime1=0;//用于控制敌人移动图片变化速度
	  private long imgtime2=0;//用于控制敌人射击图片变化速度
	  private long imgtime3=0;//用于控制敌人站立图片变化速度
	  private long attacktime = 0;//用于控制敌人射击时间
	  private long attackDistance;//敌人的攻击距离，玩家在攻击距离内就attcak
	  private int imgNum1 = 0;//用于控制敌人移动（move）时的图片下标
	  private int imgNum2 = 0;//用于控制敌人射击（attcak）时的图片下标
	  private int imgNum3 = 0;//用于控制敌人站立（stand）时图片的下标
	  
	  private int jpanlx1 = 0;//界面的x坐标左侧
	  private int jpanlx2 = 800;//界面的x坐标右侧
	  private int moveV;//移动速度
	  private int attackNum;//子弹速度？
	  private boolean attack = false;//攻击状态判定
	  private boolean stand = false;//站立状态判定
	  private boolean move = true;//移动状态判定
	  private String fx = "left";//方向
	  private String fireType = "bomb2";//子弹种类，控制不同种类的子弹
	 
	 private int mapX = 0;//地图x值
	 private int mapV = 3;//地图移动速度
	
	  
		
	
	 public Enemy(){
		 
	 }
	 public Enemy(int x, int y, int w, int h, ImageIcon icon) {
			super(x, y, w, h, icon);	
		}
	@Override  //"200,200,enemyType,MoveV,attackNum,Hp,attackDistance"
	public ElementObj createElement(String str) {
				//x,y,fx,icon,
				String[] split = str.split(",");
				this.setX(Integer.parseInt(split[0]));
				this.setY(Integer.parseInt(split[1]));
				this.setEnemyType(split[2]);
				this.setMoveV(Integer.parseInt(split[3]));
				this.setAttackNum(Integer.parseInt(split[4]));//鏁屼汉绉诲姩閫熷害		
				this.setHP(Integer.parseInt(split[5]));//鏁屼汉琛�閲忥紝榛樿琛�閲�5(鍙敼)
				this.setAttackDistance(Integer.parseInt(split[6]));
				this.setFx(split[7]);
				String key1 = this.enemyType+"_"+this.fx+"_run";
				
//				List<ImageIcon> listImageIcon1 = GameLoad.imgs.get(key1);//鑾峰彇涓婂崐韬浘鐗囧垪琛�
			
				List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//鑾峰彇鏁屼汉run鍥剧墖
				ImageIcon icon=listImageIcon1.get(0);

				switch(this.enemyType){
				case "enemyGun":this.setFireType("gunBullet");break;
				case "enemyRPG":this.setFireType("RPGBullet");break;
				
				
				}
			
				
				this.setIcon(icon);
				this.setW(icon.getIconWidth());
				this.setH(icon.getIconHeight());
				return this; //娉ㄦ剰鍒繕鍟� 鍒氬垰杩斿洖鐨勬槸鐖剁被
			}
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
		this.getX(), this.getY(), 
		this.getIcon().getIconWidth(), this.getIcon().getIconHeight() , null);
	}
	public void stateSwitch(long gameTime){
//		Random r = new Random();
//		int ran = r.nextInt(250)+150;//鐢熸垚闅忔満鏁帮紝姣忎釜鏁屼汉鐨勬敾鍑婚棿闅旈殢鏈�
		if(play.isEmpty()){
			return;
		}
		if((Math.abs(this.getX()-play.get(0).getX())<this.attackDistance) && (this.getX()>=jpanlx1 ) && (this.getX()<=jpanlx2) ){//鏁屼汉涓庣帺瀹惰窛绂诲皬浜庢敾鍑昏窛绂绘椂锛屽仠姝㈢Щ鍔�
			
			
			this.move = false;
			this.stand = true;
			
		}
		if(gameTime-this.attacktime>400 && Math.abs(this.getX()-play.get(0).getX())<this.attackDistance && (this.getX()>=jpanlx1 ) && (this.getX()<=jpanlx2)){
		
			this.attacktime = gameTime;
			this.attack = true;
//			this.move = false;
			this.stand = false;
		}

		return;
		
	}
	
	


	
	@Override
	public void  move(long gameTime){
		if(!this.move ){//濡傛灉涓嶆槸move鐘舵�侊紝return
			   if(this.stand && !map.isEmpty()) {
			    int x = map.get(0).getX();
			    if(this.mapX != x) {
			     this.mapX = x;
			     switch(((Maps)map.get(0)).getFx()) {
			      case "left":
			       this.setX(this.getX() + this.mapV);
			       break;
			      case "right":
			       this.setX(this.getX() - this.mapV);
			       break;
			      default:
			       break;
			     }
			    }
			   }
//			   return;
			  }
		if(Math.abs(this.getX()-play.get(0).getX()) >= this.attackDistance) {
			this.move = true;
			this.stand = false;
		}		
		switch(this.fx){
//		play.get(0).getX()
			case "left":
				if(this.getX()-play.get(0).getX() >= this.attackDistance) {
					this.setX(this.getX()-this.moveV);
				}
				if(this.getX()-play.get(0).getX() <= 0) {
					this.fx = "right";
				}
//				System.out.println(this.getX());
				break;
			case "right":
				if(play.get(0).getX()-this.getX() >= this.attackDistance) {
					this.setX(this.getX()+this.moveV);
				}
				if(play.get(0).getX()-this.getX() <=0) {
					this.fx = "left";
				}
				break;
		}

	}
	@Override
	protected void updateImage(long gameTime) {
		if(move == false){
			return;
		}
		 String key1 = this.enemyType+"_"+this.fx+"_run";	
		 List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//鑾峰彇鏁屼汉attack鍥剧墖
		
		if(gameTime -this.imgtime1>20) {
			
			this.imgtime1=gameTime;
			
			
			if(this.imgNum1>listImageIcon1.size()-1) {
				this.imgNum1=0;
			}
			switch(this.fx){
			case "left":ImageIcon icon=listImageIcon1.get(this.imgNum1++);
								this.setIcon(icon);break;
			case "right":ImageIcon icon1=listImageIcon1.get(this.imgNum1++);
			this.setIcon(icon1);break;
			
			}
			
		}
		
	}
	
	
	
	@Override
	public void attack(long gameTime){
		if(!this.attack){//濡傛灉涓嶆槸鏀诲嚮鐘舵�佺洿鎺eturn
			return;
		}
		
		 String key1 = this.enemyType+"_"+this.fx+"_attack";	
		 List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//鑾峰彇鏁屼汉attack鍥剧墖
	
		if(gameTime -this.imgtime2>10) {			
			this.imgtime2=gameTime;
			if(this.imgNum2>listImageIcon1.size()-1) {
				this.imgNum2=0;
				this.attack = false;
				this.move = false;
				this.stand = true;
				this.enemyFireAdd();
			}
			switch(this.fx){
			case "left":ImageIcon icon=listImageIcon1.get(imgNum2++);
			this.setIcon(icon);break;
			case "right":ImageIcon icon1=listImageIcon1.get(imgNum2++);
			this.setIcon(icon1);break;
			}			
		}		
	}
	@Override
	public void stand(long gameTime){
		if(!this.stand){
			return;
		}
		
			 String key1 = this.enemyType+"_"+this.fx+"_stand";	
			 List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//鑾峰彇鏁屼汉stand鍥剧墖
			 if(gameTime -this.imgtime3>30) {					
					this.imgtime3=gameTime;
					if(this.imgNum3>listImageIcon1.size()-1) {
						this.imgNum3=0;		
					}					
					switch(this.fx){
					case "left":ImageIcon icon=listImageIcon1.get(imgNum3++);
					this.setIcon(icon);break;
					case "right":ImageIcon icon1=listImageIcon1.get(imgNum3++);
					this.setIcon(icon1);break;
					}
					
				}
			
		
	}
	@Override
	public void die(long gameTime){
		String key1 = this.enemyType+"_"+this.fx+"_die"; 
		   
		List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//鑾峰彇鏁屼汉die鍥剧墖
		   
		ElementManager em = ElementManager.getManager();
		ImageIcon icon2 = listImageIcon1.get(0);;
		ElementObj obj = new Die(this.getX(),this.getY(),this.getW(),this.getH(),icon2,key1,"enemy");
		em.addElement(obj,GameElement.DIE);
	}
	
	/**
	 * 鍙戝皠瀛愬脊
	 * @param gameTime
	 */
	public void enemyFireAdd() {//鏈夊暒鏃堕棿灏卞彲浠ヨ繘琛屾帶鍒�
	
//		if( this.isFire ) {//濡傛灉鏄笉鍙戝皠鐘舵�� 灏辩洿鎺eturn 鎴栬�呭鏋滃彂灏勮繃瀛愬脊锛岀洿鎺eturn
//			return;
//		}
//		System.out.println(isFire);
//		this.attack=false;//鎸変竴娆★紝鍙戝皠涓�涓瓙寮广�傛嫾鎵嬮��(涔熷彲浠ュ鍔犲彉閲忔潵鎺у埗)
//		new PlayFile(); // 鏋勯�犱竴涓被 闇�瑕佸仛姣旇緝澶氱殑宸ヤ綔  鍙互閫夋嫨涓�绉嶆柟寮忥紝浣跨敤灏忓伐鍘�
//		灏嗘瀯閫犲璞＄殑澶氫釜姝ラ杩涜灏佽鎴愪负涓�涓柟娉曪紝杩斿洖鍊肩洿鎺ユ槸杩欎釜瀵硅薄
//		浼犻�掍竴涓浐瀹氭牸寮�   {X:3,y:5,f:up} json鏍煎紡

		ElementObj obj=GameLoad.getObj("enemyfire");  		
		ElementObj element = obj.createElement(fireStr());
//		System.out.println("瀛愬脊鏄惁涓虹┖"+element);
//		瑁呭叆鍒伴泦鍚堜腑
		ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
//		濡傛灉瑕佹帶鍒跺瓙寮归�熷害绛夌瓑銆傘�傘�傘�傝繕闇�瑕佷唬鐮佺紪鍐�
	}	
	
	 public String fireStr(){
	  //{x:3,y:5,f:up,t:fireType}
		int x = this.getX();
		int y = this.getY();
		
		
	  return "x:"+x+",y:"+y+",f:"+this.fx+",t:"+this.fireType+",w:"+this.getW()+",h:"+this.getH();
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
		public String getFx() {
			return fx;
		}
		public void setFx(String fx) {
			this.fx = fx;
		}
		public String getEnemyType() {
			return enemyType;
		}
		public void setEnemyType(String enemyType) {
			this.enemyType = enemyType;
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
		public long getAttacktime() {
			return attacktime;
		}
		public void setAttacktime(long attacktime) {
			this.attacktime = attacktime;
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
		public boolean isAttack() {
			return attack;
		}
		public void setAttack(boolean attack) {
			this.attack = attack;
		}
		public String getFireType() {
			return fireType;
		}
		public void setFireType(String fireType) {
			this.fireType = fireType;
		}
		public long getAttackDistance() {
			return attackDistance;
		}
		public void setAttackDistance(long attackDistance) {
			this.attackDistance = attackDistance;
		}
		public boolean isStand() {
			return stand;
		}
		public void setStand(boolean stand) {
			this.stand = stand;
		}
		
		

}
