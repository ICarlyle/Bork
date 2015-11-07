package edu.umw.cpsc240fall2015team3.zork;
import java.util.ArrayList;
/**
@author Alec
*/

/**
The Npc class represents a hostile/nonhostile enemy in the Zork dungeon.  These NPCs cannot move on their own and are tied to specific rooms.  They have their own dialogue that they will say every combat "turn" and have their own description, inventory, stats, and a number of points that they are worth when killed.  The NPC will award a number of points and drop its "inventory" on death. 
*/
public class Npc{
/**
Constructs a new, fully fledged npc enemy.
@param name String that is the name of this Npc
@param points int that is how many points this Npc is worth
@param health int that represents how much health this Npc has
@param strength int that represents how much strength this Npc has
@param defense int that represetns how much defense this Npc has
@param description String that holds the desciption of this Npc
@param dialog ArrayList<String> that holds onto all of the dialog that this Npc will say
@param isHostile boolean that stores whether this Npc is hostile or non-hostile
*/
	Npc(String name, int points, int health, int strength, int defense, String description, ArrayList<String> dialog, boolean isHostile){
	}
/**
Returns a string that describes this Npc.
*/
	public String describe(){
		return "";
	}	
/**
Returns a string that displays which items have been dropped by this Npc on death.  The items will be "dropped" into the room where the NPC died.  If the NPC has no items, no items will be dropped.
*/
	public String drop(){
		return "";
	}
/**
Returns a string that contains one of the lines of dialog that the Npc has.  The line of dialog is randomly selected every combat turn.  
*/
	public String talk(){
		return "";
	}
/**
Returns a string that displays how much this Npc has been hurt.  If the Npc is now dead, the String will say as such.
@param health int that is how much health the enemy is going to loose.
*/
	public String wound(int health){
		return "";
	}
/**
Returns an int that is equal to how much defense this Npc has.
*/
	public int defense(){
		return 0;
	}
/**
Returns an int that is equal to how much strength this Npc has.
*/
	public int strength(){
		return 0;
	}
/**
Returns a boolean value that corresponds to whether this Npc is hostile or friendly.
*/
	public boolean isHostile(){
		return true;
	}
}	
