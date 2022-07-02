package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

/**
 * @说明 本类是元素管理器，专门存储所有的元素，同时，提供方法给予视图和控制获取数据
 * @author lenovo 
 * @问题1：存储所有元素数据，怎样存放？list map set 3大集合
 * @问题2：管理器是视图和控制要访问，管理器就必须只有一个，单例模式
 */
public class ElementManager {
//	private List<Object> listMap;
//	private List<Object> listPlay;
//	private List<Object> listEnemy;
	/*
	 * String 作为key 匹配所有元素 play -> List<Object> listPlay
	 * 							enemy -> List<Object> listEnemy
	 * 枚举类型，当作map的key用来区分不一样的资源，用于获取资源
	 * List中的元素的泛型应该是 元素 基类
	 * 所有元素都可以存放到map集合中，显示模块只需要获取到这个map就可以显示所有界面所需要的元素(调用基类的showElement())
	 */
	private Map<GameElement, List<ElementObj>> gameElements;
	
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
	
	//添加元素(多半由加载器调用)
	public void addElement(ElementObj obj, GameElement ge) {
//		List<ElementObj> list = gameElements.get(ge);
//		list.add(obj);
		gameElements.get(ge).add(obj);//添加对象到集合中，按key值进行存储
	}
	
	//根据key返回list集合，取出某一类元素
	public List<ElementObj> getElementsByKey(GameElement ge) {
		return gameElements.get(ge);
	}
	
	
	/*
	 * 单例模式：内存有且只有一个实例
	 * 饿(恶)汉模式：启动就加载实例
	 * 饱汉模式：需要使用的时候才加载实例
	 * 
	 * 便携方式：
	 * 1.需要一个静态的属性(定义一个常量) 单例的引用
	 * 2.提供一个静态的方法(返回这个实例) return单例的引用
	 * 3.一般为防止其他人自己使用(类是可以实例化)，所以会私有化构造方法
	 */
	private static ElementManager EM = null;
	
	//synchronized线程锁 -> 保证本方法执行中只有一个线程
	public static synchronized ElementManager getManager() {
		if(EM == null) {
			EM = new ElementManager();
		}
		return EM;
	}
	
	private ElementManager() {//私有化构造方法
		init(); //实例化方法
	}
	
//	static {//饿(恶)汉模式实例化对象 //静态语句块是在类被加载的时候直接执行
//		EM = new ElementManager();//只会执行一次
//	}
	
	//本方法是为了将来可能出现的功能扩展，重写init方法准备的(便于继承重写，因为构造方法是不能被继承的)
	public void init() {//实例化在这里完成
		//HashMap hash散列
		gameElements = new HashMap<GameElement, List<ElementObj>>();
		//将每种元素集合都放入到map(gameElements)中
//		gameElements.put(GameElement.PLAY, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.BOSS, new ArrayList<ElementObj>());
		for(GameElement ge : GameElement.values()) {//通过循环读取枚举类型的方式添加集合
			gameElements.put(ge, new ArrayList<ElementObj>());
		}
		//道具，子弹，爆炸效果，死亡效果......
	}
	
}
