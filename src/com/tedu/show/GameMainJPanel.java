package com.tedu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @˵�� ��Ϸ����Ҫ���
 * @author lenovo
 * @����˵�� ��Ҫ����Ԫ�ص���ʾ��ͬʱ���н����ˢ��(���߳�)
 * 
 * @���⻰ java��������˼����Ӧ���ǣ����̳л����ǽӿ�ʵ��
 * 
 * @���߳�ˢ�� 1.����ʵ���ֳɽӿ�
 * 			  2.�����ж���һ���ڲ���ʵ��
 */
public class GameMainJPanel extends JPanel implements Runnable {
	//����������
	private ElementManager em;
	
	public GameMainJPanel() {
		init();
	}
	
	public void init() {
		em = ElementManager.getManager();//��ȡԪ�ع���������
	}
	
	/**
	 * paint�����ǽ��л滭Ԫ��
	 * �滭ʱ���й̶�˳���Ȼ滭��ͼƬ���ڵײ㣬��Ự��ͼƬ�Ḳ���Ȼ滭��
	 * Լ����������ִֻ��һ�Σ���ʵʱˢ����Ҫʹ�ö��߳�
	 */
	@Override	//���ڻ滭�� Graphics -> ���� ר�����ڻ滭��
	public void paint(Graphics g) {
		super.paint(g);
		
//		g.setColor(new Color(255, 0, 0));
//		g.setFont(new Font("΢���ź�", Font.BOLD, 48));
//		g.drawString("I Love JAVA", 200, 200);//һ��Ҫ�ڻ滭֮ǰ������ɫ������
//		
//		g.fillOval(335, 250, 30, 100);
//		g.fillOval(345, 475, 10, 50);
//		g.fillOval(250, 345, 100, 30);
//		g.fillOval(350, 345, 100, 30);
//		g.fillOval(250, 425, 100, 30);
//		g.fillOval(350, 425, 100, 30);
//		g.fillOval(300, 300, 100, 200);//���������ɫ��Բ
//		
//		g.setColor(new Color(255, 0, 255));
//		g.drawOval(400, 400, 100, 200);//ԲȦ
		
		
		//map key-value key�����򲻿��ظ���
		//set ��map��kayһ�� ���򲻿��ظ���
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
		//GameElement.values();//���ط��� ����ֵ��һ�����飬�����˳����Ƕ���ö�ٵ�����
		for(GameElement ge : GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for(int i=0; i<list.size(); i++) {
				ElementObj obj = list.get(i);//��ȡΪ����
				obj.showElement(g);//����ÿ�����Լ���showElement��������Լ�����ʾ
			}
		}
		
//		Set<GameElement> set = all.keySet();//�õ����е�key����
//		for(GameElement ge : set) {
//			List<ElementObj> list = all.get(ge);
//			for(int i=0; i<list.size(); i++) {
//				ElementObj obj = list.get(i);
//				obj.showElement(g);//����ÿ�����Լ���showElement��������Լ�����ʾ
//			}
//		}
	}

	@Override
	public void run() {//�ӿ�ʵ��
		while(true) {
//			System.out.println("���߳��˶�");
			this.repaint();
			//һ������£����̶߳���ʹ��һ�����ߣ����ڿ����ٶ�
			try {
				Thread.sleep(10);//����50���� 1��ˢ��20��
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
