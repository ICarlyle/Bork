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
		int npcStr;int npcDef;int npcHp;
		while (!enemies.isEmpty()){
			int enemyNum = enemies.size();
			for (int i =0; i<enemyNum;){
                                npcStr = enemies.get(i).strength();
                                npcDef = enemies.get(i).defense();
                                npcHp = enemies.get(i).health();
                                //System.out.println(npc.drop());
                                enemies.get(i).talk();
                                enemies.get(i).wound(10000000);
                                if (enemies.get(i).health() <= 0){
                                        System.out.println("kill");
                                        System.out.println(enemies.get(i).drop());
                                        //enemies.remove(enemies.get(i));
                                        currRoom.remove(enemies.get(i));
                                        //GameState.instance().removeNpc(npc);
                                i++;
				}
//				int npcStr = npc.strength();
//				int npcDef = npc.defense();
//				int npcHp = npc.health();
//				//System.out.println(npc.drop());
//				npc.talk();
//				npc.wound(10000000);
//				if (npc.health() <= 0){
//					System.out.println("kill");
//					System.out.println(npc.drop());
//					//enemies.remove(npc);
//					currRoom.remove(npc);
//					//GameState.instance().removeNpc(npc);
//				}
			}	
			break;	
		}
		return "YOU DEFEATED\n";
	}

}
