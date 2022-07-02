package com.tedu.show;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @说明 游戏窗体 主要实现功能：关闭，显示，最大最小化
 * @author lenovo
 * @功能说明 需要嵌入面板，启动主线程......
 * @窗体说明 swing awt 窗体大小(记录用户上次使用软件的窗体样式)
 * 
 * @分析	1.面板绑定到窗体
 * 		2.监听绑定
 * 		3.游戏主线程软启动
 * 		4.显示窗体
 */
public class GameJFrame extends JFrame {
	public static int GameX = 885;							//GAMEX(驼峰命名法原则)
	public static int GameY = 360;							//GAMEY(驼峰命名法原则)
	private JPanel jPanel = null;							//正在显示的面板
	private KeyListener keyListener = null;					//键盘监听
	private MouseMotionListener mouseMotionListener = null;	//鼠标监听
	private MouseListener mouseListener = null;				//鼠标监听
	private Thread thread = null;							//游戏主线程
	
	public GameJFrame() {
		init();
	}
	
	public void init() {
		this.setSize(GameX, GameY);							//设置窗体大小
		this.setTitle("测试游戏-合金弹头");					//设置窗体标题
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置退出并关闭
		this.setLocationRelativeTo(null);					//设置屏幕居中显示
	}
	
	//窗体布局：可以加 存档，读档...button (功能扩展)
	public void addButton() {
//		this.setLayout(manager);//布局格式，可以添加控件
	}
	
	//启动方法
	public void start() {
		if(jPanel != null) {
			this.add(jPanel);
		}
		if(keyListener != null) {
			this.addKeyListener(keyListener);
		}
		if(thread != null) {
			thread.start();//启动主线程
		}
		//界面刷新
		
		this.setVisible(true);//显示界面
		//如果jp 是Runnable的子类实体对象
		if(this.jPanel instanceof Runnable) {
			//已经做了类型判定，强制类型转换不会出错
			new Thread((Runnable) this.jPanel).start();
		}
	}
	
	/*
	 * set注入：ssm学习 通过set方法注入配置文件中读取数据，
	 * 将配置文件中的数据赋值为类的属性
	 * 构造注入：需要配合构造方法 spring 中ioc 进行对象的自动生成，管理
	 */
	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}

	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}

	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	
	
}
