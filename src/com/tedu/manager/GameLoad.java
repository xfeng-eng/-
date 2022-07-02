package com.tedu.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

import com.tedu.element.ElementObj;



/**
 * @说明 fake加载器(工具：用于读取配置文件的工具——工具类)
 * @author lenovo
 *
 */
public class GameLoad {
	
	private static ElementManager em = ElementManager.getManager();
	//主角图片集合
	public static Map<String, List<ImageIcon>> imgMap_element = new HashMap<>();
    //	dataMap用于存放游戏数据
	public static Map<String,String> dataMap = new HashMap<>();

	//用户读取文件的类
	private static Properties pro = new Properties();
	
	/**
	  * 说明 地图加载方法
	  * @param mapId 地图id，依据此读取文件
	  */
	
	
	/**
	 * @说明 加载图片方法
	 * 加载图片 代码和图片之间只差一个路径问题
	 */
	public static void ImgLoad(String type) {//可以带参数，因为不同的关需要不一样的图片资源
		switch(type) {
			case "element":
				ElementImgLoad();
				break;
			default:
				break;
		}
	}
	//加载游戏数据，决定敌人数量等
	public static void Dataload(){
		System.out.println("dataload compelet");
			String texturl="com/tedu/text/Data.pro";//文件的命名可以更加有规律
			ClassLoader classLoader = GameLoad.class.getClassLoader();
			InputStream texts = classLoader.getResourceAsStream(texturl);
//			imgMap用于存放数据
			pro.clear();
			try {
				pro.load(texts);
				Set<Object> set = pro.keySet();//是一个set集合
				for(Object o:set) {
					String url=pro.getProperty(o.toString());
					dataMap.put(o.toString(), url);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	public static void ElementImgLoad() {
		//得到文件路径(可以更加有规律)
		String proUrl = "com/tedu/text/ImgData.pro";
		//使用io流来获取文件对象 得到类加载器
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream datas = classLoader.getResourceAsStream(proUrl);
		pro.clear();
		try {
			pro.load(datas);
			Set<Object> keySet = pro.keySet();//是一个set集合
			for(Object o : keySet) {
				String key = o.toString();
				String url = pro.getProperty(o.toString());
				//System.out.println(key+"——————"+url);
				File[] listFiles = new File(url).listFiles();
				List<ImageIcon> listImageIcon = createListByListFiles(listFiles);
				if(listImageIcon == null) {
					System.out.println("image\\play\\目录下不能有空的文件夹! ");
				}
				imgMap_element.put(key, listImageIcon);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<ImageIcon> createListByListFiles(File[] listFiles) {
		List<ImageIcon> listImageIcon = new ArrayList<ImageIcon>();
		for(int i=0; i<listFiles.length; i++) {
			//System.out.println(listFiles[i].toString());
			ImageIcon icon = new ImageIcon(listFiles[i].toString());
			listImageIcon.add(icon);
		}
		if(listImageIcon.size() < 1) {
			return null;
		}
		return listImageIcon;
	}
	
	/**
	 * @说明 加载背景图片方法
	 * @param mapId 地图id
	 */
	public static void MapLoad(int mapId) {
		ObjLoad();
		String mapStr="0,0,right";
		ElementObj obj = getObj("map");
		ElementObj map = obj.createElement(mapStr);
		em.addElement(map, GameElement.MAPS);
	}
	
	/**
	 * @说明 加载玩家方法
	 */
	public static void PlayLoad() {
		ObjLoad();
		String playStr="100,220,right";
		ElementObj obj = getObj("play");	
		ElementObj play = obj.createElement(playStr);
		em.addElement(play, GameElement.PLAY);
	}
	/**
	 * 加载Boss
	 */
	public static void loadBoss(){
		ObjLoad();
		
		
		  
		    	//"x,y,enemyType,MoveV,attackNum,Hp,attackDistance,fx"
		    	//"x坐标，y坐标，敌人种类，移动速度，攻击力，血量，攻击距离，方向"
		    		
		    	  String enemyStr= dataMap.get("tank");
					System.out.println("出现枪兵"+enemyStr);
					ElementObj obj=getObj("boss");
					ElementObj emeny = obj.createElement(enemyStr);
					
					em.addElement(emeny, GameElement.BOSS);
	
	
	}
	/**
	 * 加载敌人
	 * @param str
	 * @return
	 *  //"200,200,enemyType,MoveV,attackNum,Hp,fx"
	 */
	public static void loadEmery(){
		ObjLoad();
		
		Timer timer = new Timer();
		//地图右侧出现敌人
		timer.scheduleAtFixedRate(new TimerTask() {
		      public void run() {
		    	//"x,y,enemyType,MoveV,attackNum,Hp,attackDistance,fx"
		    	//"x坐标，y坐标，敌人种类，移动速度，攻击力，血量，攻击距离，方向"
		    		
		    	  String enemyStr= dataMap.get("enemyGun_right");
					System.out.println("出现枪兵"+enemyStr);
					ElementObj obj=getObj("enemy");
					ElementObj emeny = obj.createElement(enemyStr);
					
					em.addElement(emeny, GameElement.ENEMY);
		      }
		}, 1000, 8000);
		
		//地图左侧出现敌人
		timer.scheduleAtFixedRate(new TimerTask() {
			   public void run() {
				   String enemyStr= dataMap.get("enemyRPG_left");
				   System.out.println("出现炮兵"+enemyStr);
						ElementObj obj=getObj("enemy");
						ElementObj emeny = obj.createElement(enemyStr);
						em.addElement(emeny, GameElement.ENEMY);
			      }
		}, 1000, 10000);
			    
	}
	/**
	 * 加载人质
	 * @param str
	 * @return
	 */
	public static void loadHostage(){
		ObjLoad();
		String enemyStr="840,220,1,1";
		ElementObj obj=getObj("hostage");
		ElementObj emeny = obj.createElement(enemyStr);
		em.addElement(emeny, GameElement.HOSTAGE);
	}
	
	/**
	 * 加载道具
	 * @param str
	 * @return
	 */
	public static void loadProp(){
		ObjLoad();
		String enemyStr="300,240,Apple,5";
		ElementObj obj=getObj("prop");
		ElementObj emeny = obj.createElement(enemyStr);
		em.addElement(emeny, GameElement.PROP);
	}
	
	/**
	 * @说明 元素类集合初始化方法
	 */
	private static Map<String, Class<?>> objMap = new HashMap<>();
	
	public static void ObjLoad(){
		//得到文件路径(可以更加有规律)
		String proUrl = "com/tedu/text/obj.pro";
		//使用io流来获取文件对象 得到类加载器
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream objs = classLoader.getResourceAsStream(proUrl);
		pro.clear();
		try {
			pro.load(objs);
			Set<Object> keySet = pro.keySet();//是一个set集合
			for(Object o : keySet) {
				String classUrl = pro.getProperty(o.toString());
				//使用反射的方式直接将类获取
				Class<?> forName = Class.forName(classUrl);
				objMap.put(o.toString(), forName);
				//System.out.println(o.toString()+"="+forName.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ElementObj getObj(String objName) {
		try {
			Class<?> class1 = objMap.get(objName);
			Object newInstance = class1.newInstance();
			if(newInstance instanceof ElementObj) {
				return (ElementObj) newInstance;//这个对象就和new Play()等价
				//新建了一个GamePlay的类
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		ImgLoad("play");
//	}
}
