package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Play extends ElementObj {
	private ElementManager em = ElementManager.getManager();
	List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
	private boolean leftRun = false;	//左跑状态 true-向左跑 false-站立
	private boolean rightRun = false;	//右跑状态 true-向右跑 false-站立
	private int mv = 3;					//主角移动速度
	private int jh = 5;					//主角跳跃高度
	private String fx = "right";		//记录当前主角面向的方向，默认是right
	private String weapon = "handgun";	//记录当前主角的武器，默认为手枪
	private String upperAction = "attack";//记录当前主角上半身的动作，attackhandgun持手枪射击 jump跳跃
	private String lowerAction = "stand";//记录当前主角下半身的动作，stand站立 run跑 jump跳跃 squatstand蹲着 squatrun蹲下跑
	private boolean pkType = false;		//攻击状态 true-攻击 false-停止
	private boolean jumpType = false;	//跳跃状态 true-跳跃 false-站立
	private boolean squatType = false;  //蹲下状态 true-蹲下 false-站立
	private boolean haveShot = false;   //子弹状态 true-已射击子弹 false-未射击子弹
	private long attackInterval = 3L;	//射击图片更换的间隔时间(不同武器，射击图片更换速度不同，需改变间隔时间)
	private long jumpInterval = 10L;	//跳跃图片更换的间隔时间
	private long actionInterval = 5L;	//动作图片更换的间隔时间
	private long attackImgTime = 0L;	//上一次射击图片更换时间
	private long jumpImgTime = 0L;		//上一次跳跃图片更换时间
	private long actionImgTime = 0L;	//上一次动作图片更换时间
	private int upperIndex = 0;			//上半身图片列表下标
	private int lowerIndex = 0;			//下半身图片列表下标
	private int tempIndex = 0;			//若跳跃时射击，则记录在跳跃时准备射击之前的upperIndex的值
	private ImageIcon icon2;			//下半身图片
	private String upperLastState = "attack";//记录主角上半身的前状态(动作)，默认为attack
	private String lowerLastState = "stand";//记录主角下半身的前状态(动作)，默认为stand
	
	public Play() {}
	
	public Play(int x, int y, int w, int h, ImageIcon icon, ImageIcon icon2) {
		super(x, y, w, h, icon);
		this.icon2 = icon2;
		this.setHP(3);//玩家血量，默认血量为10(可改)
	}
	
	/**
	 * @说明 显示主角方法(重写)
	 * @param g 画笔
	 */
	@Override
	public void showElement(Graphics g) {
		//上半身
		int x1 = this.getX();
		int y1 = this.getY();
		int w1 = this.getIcon().getIconWidth();
		int h1 = this.getIcon().getIconHeight();
		//下半身(需根据上半身进行调整)
		int x2 = x1;
		int y2 = y1+h1-8;
		int w2 = this.icon2.getIconWidth();
		int h2 = this.icon2.getIconHeight();
		switch(this.fx) {//根据主角不同方向调整下半身图片的位置
			case "right":
				x2 = x1+4;
				break;
			case "left":
				x2 = x1+w1-w2-4;
				break;
			default:
				break;
		}
		if(this.jumpType) {//跳跃时调整上下半身图片的位置
			switch(this.fx) {
				case "right":
					x1 -= 6;
					break;
				case "left":
					x1 += 6;
					break;
				default:
					break;
			}
			y2 += 2;
		} else {
			switch(this.fx) {
				case "right":
					x1 += 0;
					x2 += 0;
					break;
				case "left":
					x1 -= 44;
					x2 -= 44;
					break;
				default:
					break;	
			
			}
		}
		if(this.squatType) {//蹲下时调整上半身图片的位置
			switch(this.fx) {
				case "right":
					x1 -= 4;
					break;
				case "left":
					x1 += 4;
					break;
				default:
					break;
			}
			y1 += 14;
		}
		g.drawImage(this.getIcon().getImage(), x1, y1, w1, h1, null);//绘画上半身
		g.drawImage(this.icon2.getImage(), x2, y2, w2, h2, null);//绘画下半身
	}
	
	/**
	 * @说明 键盘按钮响应事件方法keyClick(重写)
	 * @param b1 键盘按钮按下还是松开 true-按下 false-松开
	 * @param key 键盘按钮的keyCode值
	 */
	@Override
	public void keyClick(boolean b1, int key) {
		if(b1) {
			switch (key) {
				case 65:	//A
					this.leftRun = true; 
					this.fx = "left"; 
					if(!this.jumpType) {
						this.upperAction = "attack";
						this.lowerAction = "run";
						if(this.squatType) {
							this.lowerAction = "squatrun";
						}
					}
					break;
				case 68:	//D
					this.rightRun = true; 
					this.fx = "right"; 
					if(!this.jumpType) {
						this.upperAction = "attack";
						this.lowerAction = "run";
						if(this.squatType) {
							this.lowerAction = "squatrun";
						}
					}
					break;
				case 83:	//S
					if(!jumpType) {
						this.squatType = true;
						this.upperAction = "attack";
						this.lowerAction = "squatstand";
						if(this.leftRun || this.rightRun) {
							this.lowerAction = "squatrun";
						}
					}
					break;
				case 75:	//K
					this.squatType = false;
					this.jumpType = true;//开启跳跃状态
					this.upperAction = "jump";
					this.lowerAction = "jump";
					break;
				case 74:	//J
					this.haveShot = false;//未发射子弹
					this.pkType = true;//开启攻击状态
					this.upperAction = "attack";
					break;
				default: 
					break;
			}
		} else {
			switch (key) {
				case 65:	//A
					this.leftRun = false; 
					if(!this.rightRun && !this.jumpType && !this.squatType) {
						this.lowerAction = "stand";
					}
					if(squatType && !this.leftRun && !this.rightRun) {
						this.lowerAction = "squatstand";
					}
					break;
				case 68:	//D
					this.rightRun = false; 
					if(!this.leftRun && !this.jumpType && !this.squatType) {
						this.lowerAction = "stand";
					}
					if(squatType && !this.leftRun && !this.rightRun) {
						this.lowerAction = "squatstand";
					}
					break;
				case 83:	//S
					this.squatType = false;
					this.lowerAction = "stand";
					if(this.leftRun || this.rightRun) {
						this.lowerAction = "run";
					}
					break;
				default: 
					break;
			}
		}
	}
	
	/**
	 * @说明 主角换装方法(重写)
	 * @param gameTime 当前游戏时间
	 */
	@Override
	protected void updateImage(long gameTime) {
		String key1 = this.fx+"_upper_"+this.upperAction+this.weapon;
		String key2 = this.fx+"_lower_"+this.lowerAction;
		List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);//获取上半身图片列表
		List<ImageIcon> listImageIcon2 = GameLoad.imgMap_element.get(key2);//获取下半身图片列表
		if(this.pkType && gameTime - this.attackImgTime > this.attackInterval) {
			this.attackImgTime = gameTime;
			switch(this.weapon) {//根据主角不同武器更改射击图片更换的间隔时间
				case "handgun":
					this.attackInterval = 3L;
					break;
				default:
					break;
			}
			if(this.jumpType) {
				tempIndex = this.upperIndex;
			}
			if(this.upperLastState != this.upperAction) {
				this.upperIndex = 0;
				this.upperLastState = this.upperAction;
			}
			this.setIcon(listImageIcon1.get(this.upperIndex++));
			if(this.upperIndex > listImageIcon1.size()-1) {
				this.upperIndex = 0;
				this.pkType = false;
				if(this.jumpType) {
					this.upperAction = "jump";
					key1 = this.fx+"_upper_"+this.upperAction+this.weapon;
				}
			}
		}
		if(this.jumpType && gameTime - this.jumpImgTime > this.jumpInterval) {
			this.jumpImgTime = gameTime;
			if(this.upperLastState != this.upperAction && !this.pkType) {
				this.upperIndex = 0;
				if(this.tempIndex != 0) {
					this.upperIndex = this.tempIndex < listImageIcon1.size()-1 ? this.tempIndex : listImageIcon1.size()-1;
					this.tempIndex = 0;
				}
				this.upperLastState = this.upperAction;
			}
			if(this.lowerLastState != this.lowerAction) {
				this.lowerIndex = 0;
				this.lowerLastState = this.lowerAction;
			}
			if(!this.pkType) {//攻击状态不可被打断
				this.setIcon(listImageIcon1.get(this.upperIndex++));
			}
			this.icon2 = listImageIcon2.get(this.lowerIndex++);
			if(this.upperIndex > listImageIcon1.size()-1 || this.lowerIndex > listImageIcon2.size()-1) {
				this.upperIndex = 0;
				this.lowerIndex = 0;
			}
		}
		if(!this.jumpType && gameTime - this.actionImgTime > this.actionInterval) {
			this.actionImgTime = gameTime;
			if(this.upperIndex != 0 && !this.pkType) {
				this.upperIndex = 0;
				this.upperLastState = this.upperAction;
			}
			if(this.lowerLastState != this.lowerAction) {
				this.lowerIndex = 0;
				this.lowerLastState = this.lowerAction;
			}
			this.setIcon(listImageIcon1.get(this.upperIndex));
			switch(this.lowerAction) {//根据主角下半身的动作更换图片
				case "run":
				case "squatrun":
					this.icon2 = listImageIcon2.get(this.lowerIndex++);
					if(this.lowerIndex > listImageIcon2.size()-1) {
						this.lowerIndex = 0;
					}
					break;
				case "stand":
				case "squatstand":
					this.icon2 = listImageIcon2.get(this.lowerIndex);
					break;
				default:
					break;
			}
		}
	}
	
	/**
	 * @说明 主角移动方法(重写)
	 */
	@Override
	protected void move(long gameTime) {
		//设置边界，即限制主角只能在窗体内移动
		if(this.getX() < 0) {
			this.setX(0);
			
		} 
		//地图没到尽头
		if(map.get(0).getX()>-600) {
			if(this.getX() + this.getW() >= 500) {
				this.setX(500 - this.getW());
			}
		}	
		//地图到尽头
		if(map.get(0).getX()<=-600){
			if(this.getX() + this.getW() >= 800) {
				this.setX(800 - this.getW());
			}
		}
		//根据主角状态更改x和y值
		int distance = this.mv;
		if(this.squatType) {//蹲下跑步的速度是站立跑步的2/3
			distance = this.mv * 1/3;
		}
		//移动
		if(this.leftRun) {
			this.setX(this.getX() - distance);
		}
		if(this.rightRun) {
			this.setX(this.getX() + distance);
		}
		//跳跃
		if(this.jumpType) {
			this.setY(this.getY() - this.jh);
			if(this.getY() < 50) {//200为主角跳跃的最高高度(最小y值)
				this.jh = -this.jh;
			}
			if(this.getY() > 220) {//400为主角站立时的y值
				this.setY(220);
				this.jh = -this.jh;
				this.jumpType = false;
				this.upperAction = "attack";
				if(this.leftRun || this.rightRun) {
					this.lowerAction = "run";
				} else {
					this.lowerAction = "stand";
				}
			}
		}
	}
	
	/**
	 * @说明 添加道具方法(重写)
	 * @param gameTime 当前游戏时间
	 */
	@Override	
	protected void add(long gameTime) {
		if(!this.pkType) {//如果不是发射状态，直接结束
			return;
		}
		//若当前子弹状态为false，即未发射子弹，则发射子弹
		if(this.upperIndex == 2 && !this.haveShot) {
			this.haveShot = true;//修改子弹状态，防止生成多颗子弹(一连串子弹组成激光)
			ElementObj element = GameLoad.getObj("playfire").createElement(this.fireStr());
			ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
		}
	}
	
	/**
	 * @说明 生成创建子弹所需数据的字符串方法
	 * @return String 返回创建子弹所需数据的字符串
	 */
	public String fireStr() {//定义字符串规则
		//{x:3,y:5,fx:up} json格式
		int x = this.getX();
		int y = this.getY();
		int w = 10;//子弹宽度
		int h = 10;//子弹高度
		//蹲下时的子弹位置
		if(this.squatType) {
			switch(this.fx) {//依据不一样的方向做子弹位置调整
				case "left":
					x -= 35;
					y += 28;
					break;
				case "right":
					x += this.getIcon().getIconWidth()-5;
					y += 28;
					break;
				default:
					break;
			}
		} else if(this.jumpType) {//跳跃时的子弹位置
			switch(this.fx) {//依据不一样的方向做子弹位置调整
				case "left":
					x -= 0;
					y += 14;
					break;
				case "right":
					x += this.getIcon().getIconWidth()-10;
					y += 14;
					break;
				default:
					break;
			}
		} else {//站立或奔跑时的子弹位置
			switch(this.fx) {//依据不一样的方向做子弹位置调整
				case "left":
					x -= 40;
					y += 14;
					break;
				case "right":
					x += this.getIcon().getIconWidth()-5;
					y += 14;
					break;
				default:
					break;
			}
		}
		return "x:"+x+",y:"+y+",w:"+w+",h:"+h+",fx:"+this.fx;
	}
	
	/**
	 * @说明 根据字符串创建主角方法(重写)
	 * @param str 创建主角所需要的数据(以字符串的形式: "x,y,fx")
	 */
	@Override
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		this.fx = split[2];
		String key1 = this.fx+"_upper_"+this.upperAction+this.weapon;
		String key2 = this.fx+"_lower_"+this.lowerAction;
		List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(key1);
		List<ImageIcon> listImageIcon2 = GameLoad.imgMap_element.get(key2);
		this.setIcon(listImageIcon1.get(this.upperIndex));
		this.icon2 = listImageIcon2.get(this.lowerIndex);
		int w = this.getIcon().getIconWidth() < this.icon2.getIconWidth() ? this.getIcon().getIconWidth() : this.icon2.getIconWidth();
		int h = this.getIcon().getIconHeight() + this.icon2.getIconHeight();
		this.setW(w);
		this.setH(h);
		this.setHP(999);
		//System.out.println("play: "+this.getX()+" "+this.getY()+" "+this.getW()+" "+this.getH());
		return this;
	}
	
}
