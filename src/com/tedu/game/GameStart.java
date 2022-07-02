package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

/**
 * @˵�� �����Ψһ���
 * @author lenovo
 *
 */
public class GameStart {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameJFrame gj = new GameJFrame();
		//ʵ��������
		GameListener listener = new GameListener();
		//ʵ������壬ע�뵽JFrame��
		GameMainJPanel jp = new GameMainJPanel();
		//ʵ�������߳�
		GameThread th = new GameThread();
		//ע��
		gj.setjPanel(jp);
		gj.setKeyListener(listener);
		gj.setThread(th);
		gj.start();
	}

}
