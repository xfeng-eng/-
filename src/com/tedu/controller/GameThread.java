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
 * @˵�� ��Ϸ�����̣߳����ڿ�����Ϸ���أ���Ϸ�ؿ�����Ϸ����ʱ�Զ���
 * 		��Ϸ�ж�����Ϸ��ͼ�л� ��Դ�ͷź����¶�ȡ......
 * @author lenovo
 * @�̳� ʹ�ü̳еķ�ʽʵ�ֶ��߳�(һ�㽨��ʹ�ýӿ�ʵ��)
 */
public class GameThread extends Thread {
	
	private ElementManager em;
	private long gameTime = 0L;//��ǰ��Ϸʱ��
	
	public GameThread() {
		em = ElementManager.getManager();
	}
	
	@Override
	public void run() {//��Ϸ��run����
		while(true) {//��չ���Խ�true��Ϊһ���������ڿ��ƽ���
			//��Ϸ��ʼǰ ����������������Ϸ��Դ(������Դ)
			gameLoad();
			//��Ϸ����ʱ ��Ϸ������
			gameRun();
			//��Ϸ��ǰ�������� ��Ϸ��Դ����(������Դ)
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
	 * ��Ϸ�ļ���
	 */
	private void gameLoad() {
		GameLoad.ImgLoad("element");
		//������Ϸ����
		GameLoad.Dataload();
		//���ص�ͼ
		GameLoad.MapLoad(0);
		//��������
		GameLoad.PlayLoad();
//		���ص���NPC��
		GameLoad.loadEmery();
//		��������
		GameLoad.loadHostage();
//      ����BOSS
		GameLoad.loadBoss();
//		gameElementLoad();
		//ȫ�������꣬��Ϸ����
	}
	
	/**
	 * @˵�� ��Ϸ����ʱ
	 * @����˵�� ��Ϸ��������Ҫ�������飺1.�Զ�����ҵ��ƶ�����ײ������
	 * 								2.��Ԫ�ص�����(NPC��������ֵ���)
	 * 								3.��Ϸ��ͣ......
	 * ��ʵ�����ǵ��ƶ�
	 */
	private void gameRun() {
		while(true){//Ԥ����չ true���Ա�Ϊ���������ڿ��ƹؿ�������
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
			gameTime++;//Ψһ��ʱ�����
			try {
				sleep(10);//Ĭ�����Ϊ1��ˢ��100��
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��Ϸ��Դ����
	 */
	private void gameOver() {
		
	}
	
	//��ϷԪ���Զ�������
	public void gameElementAuto(Map<GameElement, List<ElementObj>> all, long gameTime) {
		//GameElement.values();//���ط��� ����ֵ��һ�����飬�����˳����Ƕ���ö�ٵ�����
		for(GameElement ge : GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			//��д������ֱ�Ӳ����������ݵĴ��뽨�鲻Ҫʹ�õ�����
			//for(int i=list.size()-1; i>=0; i--) {
			for(int i=0; i<list.size(); i++) {
				ElementObj obj = list.get(i);//��ȡΪ����
				if(!obj.isLive()) {//�������״̬Ϊ����
					//����һ������������(�����п����������飺1.������Ч 2.����)
					obj.die(gameTime);
					//list.remove(i);
					list.remove(i--);
					continue;
				}
				obj.model(gameTime);
			}
		}
	}
	
	//��ϷԪ����ײ���
	public void gameElementPK(List<ElementObj> objList1, List<ElementObj> objList2) {
		//��ѭ������һ��һ�ж������Ϊ�棬��������2�����������״̬
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
					break;//���������Ч��
				}
			}
		}
	}
	
	public void beFired() {
		
	}
	
}
