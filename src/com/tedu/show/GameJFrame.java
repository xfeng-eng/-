package com.tedu.show;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @˵�� ��Ϸ���� ��Ҫʵ�ֹ��ܣ��رգ���ʾ�������С��
 * @author lenovo
 * @����˵�� ��ҪǶ����壬�������߳�......
 * @����˵�� swing awt �����С(��¼�û��ϴ�ʹ������Ĵ�����ʽ)
 * 
 * @����	1.���󶨵�����
 * 		2.������
 * 		3.��Ϸ���߳�������
 * 		4.��ʾ����
 */
public class GameJFrame extends JFrame {
	public static int GameX = 885;							//GAMEX(�շ�������ԭ��)
	public static int GameY = 360;							//GAMEY(�շ�������ԭ��)
	private JPanel jPanel = null;							//������ʾ�����
	private KeyListener keyListener = null;					//���̼���
	private MouseMotionListener mouseMotionListener = null;	//������
	private MouseListener mouseListener = null;				//������
	private Thread thread = null;							//��Ϸ���߳�
	
	public GameJFrame() {
		init();
	}
	
	public void init() {
		this.setSize(GameX, GameY);							//���ô����С
		this.setTitle("������Ϸ-�Ͻ�ͷ");					//���ô������
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�����˳����ر�
		this.setLocationRelativeTo(null);					//������Ļ������ʾ
	}
	
	//���岼�֣����Լ� �浵������...button (������չ)
	public void addButton() {
//		this.setLayout(manager);//���ָ�ʽ��������ӿؼ�
	}
	
	//��������
	public void start() {
		if(jPanel != null) {
			this.add(jPanel);
		}
		if(keyListener != null) {
			this.addKeyListener(keyListener);
		}
		if(thread != null) {
			thread.start();//�������߳�
		}
		//����ˢ��
		
		this.setVisible(true);//��ʾ����
		//���jp ��Runnable������ʵ�����
		if(this.jPanel instanceof Runnable) {
			//�Ѿ����������ж���ǿ������ת���������
			new Thread((Runnable) this.jPanel).start();
		}
	}
	
	/*
	 * setע�룺ssmѧϰ ͨ��set����ע�������ļ��ж�ȡ���ݣ�
	 * �������ļ��е����ݸ�ֵΪ�������
	 * ����ע�룺��Ҫ��Ϲ��췽�� spring ��ioc ���ж�����Զ����ɣ�����
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
