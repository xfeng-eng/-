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
 * @说明 监听类，用于监听用户的操作 KeyListener
 * @author lenovo
 *
 */
public class GameListener implements KeyListener {

	private ElementManager em = ElementManager.getManager();
	
	/**
	 * 能否通过一个集合来记录多有按下的键，如果重复触发，就直接结束
	 * 同时第一次按下，记录到集合中，第二次按下判定集合中是否存在 
	 * 松开就删除集合中的记录
	 * set集合 (tips:set只能存对象)
	 */
	private Set<Integer> set = new HashSet<Integer>();
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 按下：左37 上38 右39 下40  tab键没反应
	 * 实现主角的移动
	 */
	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println("keyPressed "+e.getKeyCode());
		if(set.contains(e.getKeyCode())) {//如果集合包含此对象就直接结束方法
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
	 * 松开
	 */
	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("keyReleased "+e.getKeyCode());
		if(!set.contains(e.getKeyCode())) {//如果集合不包含此对象就直接结束方法
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
