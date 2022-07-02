package com.tedu.controller;

import java.util.List;
import java.util.Map;

import com.tedu.element.Boss;
import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.EnemyFire;
import com.tedu.element.Hostage;
import com.tedu.element.Play;
import com.tedu.element.PlayFire;
import com.tedu.element.Prop;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * @说明 游戏的主线程，用于控制游戏加载，游戏关卡，游戏运行时自动化
 * 		游戏判定；游戏地图切换 资源释放和重新读取......
 * @author lenovo
 * @继承 使用继承的方式实现多线程(一般建议使用接口实现)
 */
public class GameThread extends Thread {
	
	private ElementManager em;
	private long gameTime = 0L;//当前游戏时间
	
	public GameThread() {
		em = ElementManager.getManager();
	}
	
	@Override
	public void run() {//游戏的run方法
		while(true) {//扩展可以将true变为一个变量用于控制结束
			//游戏开始前 读进度条，加载游戏资源(场景资源)
			gameLoad();
			//游戏进行时 游戏过程中
			gameRun();
			//游戏当前场景结束 游戏资源回收(场景资源)
			gameOver();
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 游戏的加载
	 */
	private void gameLoad() {
		GameLoad.ImgLoad("element");
		//加载游戏数据
		GameLoad.Dataload();
		//加载地图
		GameLoad.MapLoad(0);
		//加载主角
		GameLoad.PlayLoad();
//		加载敌人NPC等
		GameLoad.loadEmery();
//		加载人质
		GameLoad.loadHostage();
//      加载BOSS
		GameLoad.loadBoss();
//		gameElementLoad();
		//全部加载完，游戏启动
	}
	
	/**
	 * @说明 游戏进行时
	 * @任务说明 游戏过程中需要做的事情：1.自动化玩家的移动，碰撞，死亡
	 * 								2.新元素的增加(NPC死亡后出现道具)
	 * 								3.游戏暂停......
	 * 先实现主角的移动
	 */
	private void gameRun() {
		while(true){//预留扩展 true可以变为变量，用于控制关卡结束等
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			gameElementAuto(all, gameTime);
			List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
			List<ElementObj> enemy = em.getElementsByKey(GameElement.ENEMY);
			List<ElementObj> hostage = em.getElementsByKey(GameElement.HOSTAGE);
			List<ElementObj> fire = em.getElementsByKey(GameElement.PLAYFIRE);
			List<ElementObj> prop = em.getElementsByKey(GameElement.PROP);
			List<ElementObj> die = em.getElementsByKey(GameElement.DIE);
			List<ElementObj> boss = em.getElementsByKey(GameElement.BOSS);
			gameElementPK(boss, fire);
			gameElementPK(enemy, fire);
			gameElementPK(play, fire);
			gameElementPK(hostage, fire);
			gameElementPK(play, prop);
//			gameElementPK(fire,map);
			gameTime++;//唯一的时间控制
			try {
				sleep(10);//默认理解为1秒刷新100次
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 游戏资源回收
	 */
	private void gameOver() {
		
	}
	
	//游戏元素自动化方法
	public void gameElementAuto(Map<GameElement, List<ElementObj>> all, long gameTime) {
		//GameElement.values();//隐藏方法 返回值是一个数组，数组的顺序就是定义枚举的类型
		for(GameElement ge : GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			//编写这样的直接操作集合数据的代码建议不要使用迭代器
			//for(int i=list.size()-1; i>=0; i--) {
			for(int i=0; i<list.size(); i++) {
				ElementObj obj = list.get(i);//读取为基类
				if(!obj.isLive()) {//如果生存状态为死亡
					//启动一个我死亡方法(方法中可以做的事情：1.死亡特效 2.掉落)
					obj.die(gameTime);
					//list.remove(i);
					list.remove(i--);
					continue;
				}
				obj.model(gameTime);
			}
		}
	}
	
	//游戏元素碰撞检测
	public void gameElementPK(List<ElementObj> objList1, List<ElementObj> objList2) {
		//用循环，做一对一判定，如果为真，就设置这2个对象的死亡状态
		for(int i=0; i<objList1.size(); i++) {
			ElementObj obj1 = objList1.get(i);
			for(int j=0; j<objList2.size(); j++) {
				ElementObj obj2 = objList2.get(j);
				if(obj1.pk(obj2)) {
					if(obj1 instanceof Enemy && obj2 instanceof PlayFire) {
						obj1.beAttacted(((PlayFire)obj2).getAttack());
						obj2.setLive(false);
					}
					if(obj1 instanceof Play && obj2 instanceof EnemyFire) {
						obj1.beAttacted(((EnemyFire)obj2).getAttack());
						obj2.setLive(false);
					}
					if(obj1 instanceof Hostage && obj2 instanceof PlayFire) {
						obj1.beAttacted(((PlayFire)obj2).getAttack());
						obj2.setLive(false);
					}
					if(obj1 instanceof Play && obj2 instanceof Prop) {
						obj1.beAttacted(-((Prop)obj2).getTreatment());
						obj2.setLive(false);
					}
					if(obj1 instanceof Boss && obj2 instanceof PlayFire) {
						obj1.beAttacted(((PlayFire)obj2).getAttack());
						obj2.setLive(false);
					}
					break;//跳出，提高效率
				}
			}
		}
	}
	
	public void beFired() {
		
	}
	
}
