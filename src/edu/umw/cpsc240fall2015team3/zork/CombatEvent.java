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
		if (chosenEnemy == null||!chosenEnemy.getName().equals(enemyName)){
			return "There is no " + enemyName + " here!\n";
		}
		int playerStr = GameState.instance().getStr();
		int playerDef = GameState.instance().getDef();
		playerStr=20;
		int npcStr;int npcDef;int npcHp;int npcDam=0;
		if (!enemies.isEmpty()){
			enemies = currRoom.getEnemies();
			int enemyNum = enemies.size();
			for(Npc npc : enemies){
				npcStr = npc.strength();
				npcDef = npc.defense()+1;
				npcHp = npc.health();
System.out.println("ENEMY ENCOUNTER: "+npc.getName());
System.out.println("     HP: "+npcHp+" Def: "+npcDef+" Str: "+npcStr);
				System.out.println(npc.talk());
/*Enemy Attack*/ 		
				npcDam = npcStr;
GameState.instance().subtractAdventurersHealthBy(npcDam);
EventFactory.instance().parse("Wound").execute();

/*Attack the Enemy */		if(npc.getName().equals(enemyName)){
				npc.wound(playerStr);
				
					if (npc.health() <= 0){
					System.out.println(enemyName+" killed");
					System.out.println(npc.drop());
					//enemies.remove(npc);
					currRoom.remove(npc);
					//GameState.instance().removeNpc(npc);
				}
				}

			}	
			//break;
			//return "YOU DEFEATED\n";
System.out.println("The Player takes "+npcDam+" damage.");
System.out.println("The "+enemyName+" takes "+playerStr+" damage.");
			return "";
		}
		return "There is no " + enemyName + " here!\n";
	}

}
