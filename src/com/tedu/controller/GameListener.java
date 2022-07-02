package com.tedu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.border.EmptyBorder;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @˵�� �����࣬���ڼ����û��Ĳ��� KeyListener
 * @author lenovo
 *
 */
public class GameListener implements KeyListener {

	private ElementManager em = ElementManager.getManager();
	
	/**
	 * �ܷ�ͨ��һ����������¼���а��µļ�������ظ���������ֱ�ӽ���
	 * ͬʱ��һ�ΰ��£���¼�������У��ڶ��ΰ����ж��������Ƿ���� 
	 * �ɿ���ɾ�������еļ�¼
	 * set���� (tips:setֻ�ܴ����)
	 */
	private Set<Integer> set = new HashSet<Integer>();
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * ���£���37 ��38 ��39 ��40  tab��û��Ӧ
	 * ʵ�����ǵ��ƶ�
	 */
	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println("keyPressed "+e.getKeyCode());
		if(set.contains(e.getKeyCode())) {//������ϰ����˶����ֱ�ӽ�������
			return ;
		}
		set.add(e.getKeyCode());
		List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
		List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
		List<ElementObj> hostage = em.getElementsByKey(GameElement.HOSTAGE);
		for(ElementObj obj : play) {
			obj.keyClick(true, e.getKeyCode());
		}
		for(ElementObj obj:map){
			obj.keyClick(true, e.getKeyCode());
		}
		for(ElementObj obj:hostage){
			obj.keyClick(true, e.getKeyCode());
		}
	}

	/**
	 * �ɿ�
	 */
	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("keyReleased "+e.getKeyCode());
		if(!set.contains(e.getKeyCode())) {//������ϲ������˶����ֱ�ӽ�������
			return ;
		}
		set.remove(e.getKeyCode());
		List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
		List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
		List<ElementObj> hostage = em.getElementsByKey(GameElement.HOSTAGE);
		for(ElementObj obj : play) {
			obj.keyClick(false, e.getKeyCode());
		}
		for(ElementObj obj:map){
			obj.keyClick(false, e.getKeyCode());
		}
		for(ElementObj obj:hostage){
			obj.keyClick(false, e.getKeyCode());
		}
	}
	
}
