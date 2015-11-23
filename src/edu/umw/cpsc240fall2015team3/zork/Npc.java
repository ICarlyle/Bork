package edu.umw.cpsc240fall2015team3.zork;
import java.util.ArrayList;
import java.util.Scanner;
/**
@author Alec
*/

/**
The Npc class represents a hostile/nonhostile enemy in the Zork dungeon.  These NPCs cannot move on their own and are tied to specific rooms.  They have their own dialogue that they will say every combat "turn" and have their own description, inventory, stats, and a number of points that they are worth when killed.  The NPC will award a number of points and drop its "inventory" on death. 
*/
public class Npc{
	static class NoNpcException extends Exception {}
/**
Constructs a new, fully fledged npc enemy.
@param name String that is the name of this Npc
@param points int that is how many points this Npc is worth
@param health int that represents how much health this Npc has
@param strength int that represents how much strength this Npc has
@param defense int that represetns how much defense this Npc has
@param description String that holds the desciption of this Npc
@param dialog An ArrayList of Strings that holds onto all of the dialog that this Npc will say
@param isHostile boolean that stores whether this Npc is hostile or non-hostile
*/
	private String name;
	private int points, health, strength, defense;
	private ArrayList<String> dialog;
	private ArrayList<String> inventory;
	private ArrayList<String> description;
	private boolean isHostile;
	
	private void init() {
		dialog = new ArrayList<String>();
		inventory = new ArrayList<String>();
		description = new ArrayList<String>();
	 }
	
	Npc(Scanner s) throws NoNpcException, 
		Dungeon.IllegalDungeonFormatException{
	String currLine = "";
	System.out.println("START OF NPC CONSTRCUTOR");
	init();
	
	currLine = s.nextLine();
	if (currLine.equals("===")){
		System.out.println("Npc parsing fin");
		throw new Npc.NoNpcException();
	}
	this.name = currLine;
	
	System.out.println(name);
	currLine = s.nextLine(); // ***
	while (!s.hasNext("\\*\\*\\*")){
		this.description.add(s.nextLine());
	}
	System.out.println(s.nextLine()); // ***
	String isHostile = s.nextLine();
	if (isHostile.equals("isHostile")){
		this.isHostile = true;
	}
	else if (isHostile.equals("isNotHostile")){
		this.isHostile = false;
	}
	String[] healthLine = s.nextLine().split(":");
	this.health = Integer.parseInt(healthLine[1]); 
	String[] strLine = s.nextLine().split(":");
	this.strength = Integer.parseInt(strLine[1]);
	String[] defLine = s.nextLine().split(":");
	this.defense = Integer.parseInt(defLine[1]);
	String[] ptsLine = s.nextLine().split(":");
	this.points = Integer.parseInt(ptsLine[1]);
	currLine = s.nextLine();
	System.out.println("inventoryLine: " + currLine);
	if (currLine.contains("Inventory")){
		String[] inven = currLine.split(":");
		String allItems = inven[1];
		System.out.println("allItems: " + allItems);
		if (allItems.contains(",")){
			String[] allItemsArray = allItems.split(",");
			for (int i = 0; i < allItemsArray.length; i++){
				this.inventory.add(allItemsArray[i]);
			}
		}
		else {
			this.inventory.add(allItems);
		}
	currLine = s.nextLine(); // ***
	System.out.println("Shld be *** " + currLine);
	}
	if (currLine.equals("***")){
		System.out.println("ye");
		while(!s.hasNext("\\*\\*\\*")){
			this.dialog.add(s.nextLine());
		}
	}
	System.out.println("before ===" + s.nextLine()); // ---
	currLine = s.nextLine(); // ---
	System.out.println("at the end: " + currLine);
	//if (s.hasNext("===")){
	//	currLine = s.nextLine(); // ===
	//}
	}
/**
Returns a string that describes this Npc.
*/
	public String describe(){
		String stringDesc = "";
		for (int i = 0; i < description.size(); i++){
			stringDesc += description.get(i) + "\n";
		}
		return stringDesc;
	}	
/**
Returns a string that displays which items have been dropped by this Npc on death.  The items will be "dropped" into the room where the NPC died.  If the NPC has no items, no items will be dropped.
*/
	public String drop(){
		String dropMessage = "The " + name + " has dropped:\n";
		for (int i = 0; i < inventory.size(); i++){
		try{
			Item currItem = GameState.instance().getDungeon().getItem(inventory.get(i));
			Room currRoom = GameState.instance().getAdventurersCurrentRoom();
			currRoom.add(currItem);
			dropMessage += "A " + currItem.getPrimaryName() + " \n";                        inventory.remove(i);	
		}catch (Item.NoItemException e){}
		}
		GameState.instance().addToAdventurersScoreBy(this.points);
		return dropMessage + "You earned " + this.points + " points.\n";
	}
/**
Returns a string that contains one of the lines of dialog that the Npc has.  The line of dialog is randomly selected every combat turn.  
*/
	public String talk(){
		int numberedDialog = (int)(Math.round((Math.random() * dialog.size())-1));
		if (numberedDialog == -1){
			numberedDialog = 0;
		}
		String lineOfDialog = dialog.get(numberedDialog);
		return lineOfDialog + "\n";
	}
/**
Returns a string that displays how much this Npc has been hurt.  If the Npc is now dead, the String will say as such.
@param health int that is how much health the enemy is going to loose.
*/
	public String wound(int hp){
		this.health -= hp;
		return "You whack " + name + " for " + hp + " hp.\n";
	}
/**
Returns an int that is equal to how much defense this Npc has.
*/
	public int defense(){
		return defense;
	}
/**
Returns an int that is equal to how much strength this Npc has.
*/
	public int strength(){
		return strength;
	}

	public int health(){
		return health;
	}
/**
Returns a boolean value that corresponds to whether this Npc is hostile or friendly.
*/
	public boolean isHostile(){
		return isHostile;
	}
	
	public String getName(){ return name;}
}	
