package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

/**
 * @˵�� ������Ԫ�ع�������ר�Ŵ洢���е�Ԫ�أ�ͬʱ���ṩ����������ͼ�Ϳ��ƻ�ȡ����
 * @author lenovo 
 * @����1���洢����Ԫ�����ݣ�������ţ�list map set 3�󼯺�
 * @����2������������ͼ�Ϳ���Ҫ���ʣ��������ͱ���ֻ��һ��������ģʽ
 */
public class ElementManager {
//	private List<Object> listMap;
//	private List<Object> listPlay;
//	private List<Object> listEnemy;
	/*
	 * String ��Ϊkey ƥ������Ԫ�� play -> List<Object> listPlay
	 * 							enemy -> List<Object> listEnemy
	 * ö�����ͣ�����map��key�������ֲ�һ������Դ�����ڻ�ȡ��Դ
	 * List�е�Ԫ�صķ���Ӧ���� Ԫ�� ����
	 * ����Ԫ�ض����Դ�ŵ�map�����У���ʾģ��ֻ��Ҫ��ȡ�����map�Ϳ�����ʾ���н�������Ҫ��Ԫ��(���û����showElement())
	 */
	private Map<GameElement, List<ElementObj>> gameElements;
	
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
	
	//���Ԫ��(����ɼ���������)
	public void addElement(ElementObj obj, GameElement ge) {
//		List<ElementObj> list = gameElements.get(ge);
//		list.add(obj);
		gameElements.get(ge).add(obj);//��Ӷ��󵽼����У���keyֵ���д洢
	}
	
	//����key����list���ϣ�ȡ��ĳһ��Ԫ��
	public List<ElementObj> getElementsByKey(GameElement ge) {
		return gameElements.get(ge);
	}
	
	
	/*
	 * ����ģʽ���ڴ�����ֻ��һ��ʵ��
	 * ��(��)��ģʽ�������ͼ���ʵ��
	 * ����ģʽ����Ҫʹ�õ�ʱ��ż���ʵ��
	 * 
	 * ��Я��ʽ��
	 * 1.��Ҫһ����̬������(����һ������) ����������
	 * 2.�ṩһ����̬�ķ���(�������ʵ��) return����������
	 * 3.һ��Ϊ��ֹ�������Լ�ʹ��(���ǿ���ʵ����)�����Ի�˽�л����췽��
	 */
	private static ElementManager EM = null;
	
	//synchronized�߳��� -> ��֤������ִ����ֻ��һ���߳�
	public static synchronized ElementManager getManager() {
		if(EM == null) {
			EM = new ElementManager();
		}
		return EM;
	}
	
	private ElementManager() {//˽�л����췽��
		init(); //ʵ��������
	}
	
//	static {//��(��)��ģʽʵ�������� //��̬���������౻���ص�ʱ��ֱ��ִ��
//		EM = new ElementManager();//ֻ��ִ��һ��
//	}
	
	//��������Ϊ�˽������ܳ��ֵĹ�����չ����дinit����׼����(���ڼ̳���д����Ϊ���췽���ǲ��ܱ��̳е�)
	public void init() {//ʵ�������������
		//HashMap hashɢ��
		gameElements = new HashMap<GameElement, List<ElementObj>>();
		//��ÿ��Ԫ�ؼ��϶����뵽map(gameElements)��
//		gameElements.put(GameElement.PLAY, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.BOSS, new ArrayList<ElementObj>());
		for(GameElement ge : GameElement.values()) {//ͨ��ѭ����ȡö�����͵ķ�ʽ��Ӽ���
			gameElements.put(ge, new ArrayList<ElementObj>());
		}
		//���ߣ��ӵ�����ըЧ��������Ч��......
	}
	
}
