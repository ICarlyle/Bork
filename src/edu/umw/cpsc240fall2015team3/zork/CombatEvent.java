package edu.umw.cpsc240fall2015team3.zork;

import java.util.Scanner;
import java.util.ArrayList;

class CombatEvent extends Event {

	private String enemyName;

	CombatEvent(String enemyName){
		this.enemyName = enemyName;
	}

	public String execute() {
		ArrayList<Npc> enemies = new ArrayList<Npc>();
		Scanner sc = new Scanner(System.in);
		//boolean inCombat = true;

		Room currRoom = GameState.instance().getAdventurersCurrentRoom();
		enemies = currRoom.getEnemies();
		Npc chosenEnemy = GameState.instance().getDungeon().getNpc(enemyName);
		System.out.println(chosenEnemy.talk());;
		int playerStr = GameState.instance().getStr();
		int playerDef = GameState.instance().getDef();
		
		while (!enemies.isEmpty()){
			for (Npc npc : enemies){
				npc.describe();
				break;
			}	
			break;	
		}
		return "YOU DEFEATED\n";
	}

}
