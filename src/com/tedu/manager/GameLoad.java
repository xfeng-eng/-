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
 * @˵�� fake������(���ߣ����ڶ�ȡ�����ļ��Ĺ��ߡ���������)
 * @author lenovo
 *
 */
public class GameLoad {
	
	private static ElementManager em = ElementManager.getManager();
	//����ͼƬ����
	public static Map<String, List<ImageIcon>> imgMap_element = new HashMap<>();
    //	dataMap���ڴ����Ϸ����
	public static Map<String,String> dataMap = new HashMap<>();

	//�û���ȡ�ļ�����
	private static Properties pro = new Properties();
	
	/**
	  * ˵�� ��ͼ���ط���
	  * @param mapId ��ͼid�����ݴ˶�ȡ�ļ�
	  */
	
	
	/**
	 * @˵�� ����ͼƬ����
	 * ����ͼƬ �����ͼƬ֮��ֻ��һ��·������
	 */
	public static void ImgLoad(String type) {//���Դ���������Ϊ��ͬ�Ĺ���Ҫ��һ����ͼƬ��Դ
		switch(type) {
			case "element":
				ElementImgLoad();
				break;
			default:
				break;
		}
	}
	//������Ϸ���ݣ���������������
	public static void Dataload(){
		System.out.println("dataload compelet");
			String texturl="com/tedu/text/Data.pro";//�ļ����������Ը����й���
			ClassLoader classLoader = GameLoad.class.getClassLoader();
			InputStream texts = classLoader.getResourceAsStream(texturl);
//			imgMap���ڴ������
			pro.clear();
			try {
				pro.load(texts);
				Set<Object> set = pro.keySet();//��һ��set����
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
		//�õ��ļ�·��(���Ը����й���)
		String proUrl = "com/tedu/text/ImgData.pro";
		//ʹ��io������ȡ�ļ����� �õ��������
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream datas = classLoader.getResourceAsStream(proUrl);
		pro.clear();
		try {
			pro.load(datas);
			Set<Object> keySet = pro.keySet();//��һ��set����
			for(Object o : keySet) {
				String key = o.toString();
				String url = pro.getProperty(o.toString());
				//System.out.println(key+"������������"+url);
				File[] listFiles = new File(url).listFiles();
				List<ImageIcon> listImageIcon = createListByListFiles(listFiles);
				if(listImageIcon == null) {
					System.out.println("image\\play\\Ŀ¼�²����пյ��ļ���! ");
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
	 * @˵�� ���ر���ͼƬ����
	 * @param mapId ��ͼid
	 */
	public static void MapLoad(int mapId) {
		ObjLoad();
		String mapStr="0,0,right";
		ElementObj obj = getObj("map");
		ElementObj map = obj.createElement(mapStr);
		em.addElement(map, GameElement.MAPS);
	}
	
	/**
	 * @˵�� ������ҷ���
	 */
	public static void PlayLoad() {
		ObjLoad();
		String playStr="100,220,right";
		ElementObj obj = getObj("play");	
		ElementObj play = obj.createElement(playStr);
		em.addElement(play, GameElement.PLAY);
	}
	/**
	 * ����Boss
	 */
	public static void loadBoss(){
		ObjLoad();
		
		
		  
		    	//"x,y,enemyType,MoveV,attackNum,Hp,attackDistance,fx"
		    	//"x���꣬y���꣬�������࣬�ƶ��ٶȣ���������Ѫ�����������룬����"
		    		
		    	  String enemyStr= dataMap.get("tank");
					System.out.println("����ǹ��"+enemyStr);
					ElementObj obj=getObj("boss");
					ElementObj emeny = obj.createElement(enemyStr);
					
					em.addElement(emeny, GameElement.BOSS);
	
	
	}
	/**
	 * ���ص���
	 * @param str
	 * @return
	 *  //"200,200,enemyType,MoveV,attackNum,Hp,fx"
	 */
	public static void loadEmery(){
		ObjLoad();
		
		Timer timer = new Timer();
		//��ͼ�Ҳ���ֵ���
		timer.scheduleAtFixedRate(new TimerTask() {
		      public void run() {
		    	//"x,y,enemyType,MoveV,attackNum,Hp,attackDistance,fx"
		    	//"x���꣬y���꣬�������࣬�ƶ��ٶȣ���������Ѫ�����������룬����"
		    		
		    	  String enemyStr= dataMap.get("enemyGun_right");
					System.out.println("����ǹ��"+enemyStr);
					ElementObj obj=getObj("enemy");
					ElementObj emeny = obj.createElement(enemyStr);
					
					em.addElement(emeny, GameElement.ENEMY);
		      }
		}, 1000, 8000);
		
		//��ͼ�����ֵ���
		timer.scheduleAtFixedRate(new TimerTask() {
			   public void run() {
				   String enemyStr= dataMap.get("enemyRPG_left");
				   System.out.println("�����ڱ�"+enemyStr);
						ElementObj obj=getObj("enemy");
						ElementObj emeny = obj.createElement(enemyStr);
						em.addElement(emeny, GameElement.ENEMY);
			      }
		}, 1000, 10000);
			    
	}
	/**
	 * ��������
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
	 * ���ص���
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
	 * @˵�� Ԫ���༯�ϳ�ʼ������
	 */
	private static Map<String, Class<?>> objMap = new HashMap<>();
	
	public static void ObjLoad(){
		//�õ��ļ�·��(���Ը����й���)
		String proUrl = "com/tedu/text/obj.pro";
		//ʹ��io������ȡ�ļ����� �õ��������
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream objs = classLoader.getResourceAsStream(proUrl);
		pro.clear();
		try {
			pro.load(objs);
			Set<Object> keySet = pro.keySet();//��һ��set����
			for(Object o : keySet) {
				String classUrl = pro.getProperty(o.toString());
				//ʹ�÷���ķ�ʽֱ�ӽ����ȡ
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
				return (ElementObj) newInstance;//�������ͺ�new Play()�ȼ�
				//�½���һ��GamePlay����
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
