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
		if (chosenEnemy == null){
			return "There is no " + enemyName + " here!\n";
		}
		System.out.println(chosenEnemy.talk());
		int playerStr = GameState.instance().getStr();
		int playerDef = GameState.instance().getDef();
		
		while (!enemies.isEmpty()){
			for (Npc npc : enemies){
				int npcStr = npc.strength();
				int npcDef = npc.defense();
				int npcHp = npc.health();
				//System.out.println(npc.drop());
				npc.talk();
				if (npc.health() <= 0){
					System.out.println(npc.drop());
					enemies.remove(npc);
					GameState.instance().removeNpc(npc);
				}
				break;
			}	
			break;	
		}
		return "YOU DEFEATED\n";
	}

}
