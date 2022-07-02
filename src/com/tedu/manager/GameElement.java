package com.tedu.manager;

public enum GameElement {
	/*
	 * PLAY 玩家
	 * MAPS 地图
	 * ENEMY 敌人
	 * BOSS boss
	 * PLAYFIRE 子弹
	 * DIE 死亡特效
	 * ......
	 */
	MAPS,HOSTAGE,PROP,PLAY,ENEMY,BOSS,PLAYFIRE,ENEMYFIRE,DIE;	//枚举类型的默认顺序是声明的顺序
	//此定义的枚举类型，在编译的时候，虚拟机会自动帮助生成class文件，并且会加载很多的代码和方法
	//例如
//	private GameElement(){
//		
//	}
//	private GameElement(int id){
//		
//	}
}
