/**
@author Ian
*/
package edu.umw.cpsc240fall2015team3.zork;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
The GameState class represents the current state of the game and has methods that change the game's state.
These methods include the ability to save and restore the games state, the player's status and inventory, and access to the current Dungeon and Room.
*/

//health: int that is the player's current health
//score: int that is the player's current score
//str: int that measures how strong the player is
//def: int that measures how much damage the player can avoid
//totalTime: int that stores how much time has passed in the dungeon

public class GameState {
/**
Prints the error message after being called.
*/
    public static class IllegalSaveFormatException extends Exception {
        public IllegalSaveFormatException(String e) {
            super(e);
        }
    }

    static int MAX_CARRY_WEIGHT = 40;

    static String DEFAULT_SAVE_FILE = "bork_save";
    static String SAVE_FILE_EXTENSION = ".sav";
    static String SAVE_FILE_VERSION = "Bork v3.0 save data";

    static String ADVENTURER_MARKER = "Adventurer:";
    static String CURRENT_ROOM_LEADER = "Current room: ";
    static String INVENTORY_LEADER = "Inventory: ";


    private static GameState theInstance;
    private Dungeon dungeon;
    private ArrayList<Item> inventory;
    private Room adventurersCurrentRoom;

    private int health;
    private int str;
    private int def;
    private int totalTime;
    private int score;


    static synchronized GameState instance() {
        if (theInstance == null) {
            theInstance = new GameState();
        }
        return theInstance;
    }
/**
Instantiates objects on creation of GameState
*/
    private GameState() {
        inventory = new ArrayList<Item>();
	health = 100;
    }
/**
Restores the dungeon to a previous save state with the passed parameter as the title.
@param filename name of file that is to be restored.
@throws FileNotFoundException If there is no file by that filename.
@throws IllegalSaveFormatException If the format of the save file it reads has bad syntax.
@throws Dungeon.IllegalDungeonFormatException if the dungeon file it reads has bad syntax
*/
    void restore(String filename) throws FileNotFoundException,
        IllegalSaveFormatException, Dungeon.IllegalDungeonFormatException {

        Scanner s = new Scanner(new FileReader(filename));

        if (!s.nextLine().equals(SAVE_FILE_VERSION)) {
            throw new IllegalSaveFormatException("Save file not compatible.");
        }

        String dungeonFileLine = s.nextLine();

        if (!dungeonFileLine.startsWith(Dungeon.FILENAME_LEADER)) {
            throw new IllegalSaveFormatException("No '" +
                Dungeon.FILENAME_LEADER + 
                "' after version indicator.");
        }

        dungeon = new Dungeon(dungeonFileLine.substring(
            Dungeon.FILENAME_LEADER.length()), false);
        dungeon.restoreState(s);

        s.nextLine();  // Throw away "Adventurer:".
        String currentRoomLine = s.nextLine();
        adventurersCurrentRoom = dungeon.getRoom(
            currentRoomLine.substring(CURRENT_ROOM_LEADER.length()));
        if (s.hasNext()) {
            String inventoryList = s.nextLine().substring(
                INVENTORY_LEADER.length());
            String[] inventoryItems = inventoryList.split(",");
            for (String itemName : inventoryItems) {
                try {
                    addToInventory(dungeon.getItem(itemName));
                } catch (Item.NoItemException e) {
                    throw new IllegalSaveFormatException("No such item '" +
                        itemName + "'");
                }
            }
        }
    }
/**
Runs the store method with a default save title argument.
@throws IOException if there is an error saving the file
*/
    void store() throws IOException {
        store(DEFAULT_SAVE_FILE);
    }
/**
Creates a new file with the title of the argument passed with a save file extension.
@throws IOException if there is an error saving the file
*/
    void store(String saveName) throws IOException {
        String filename = saveName + SAVE_FILE_EXTENSION;
        PrintWriter w = new PrintWriter(new FileWriter(filename));
        w.println(SAVE_FILE_VERSION);
        dungeon.storeState(w);
        w.println(ADVENTURER_MARKER);
        w.println(CURRENT_ROOM_LEADER + adventurersCurrentRoom.getTitle());
        if (inventory.size() > 0) {
            w.print(INVENTORY_LEADER);
            for (int i=0; i<inventory.size()-1; i++) {
                w.print(inventory.get(i).getPrimaryName() + ",");
            w.println(inventory.get(inventory.size()-1).getPrimaryName());
        }
        w.close();
    }
}
/**
Stores the passed parameter as the current dungeon.
*/
    void initialize(Dungeon dungeon) {
        this.dungeon = dungeon;
        adventurersCurrentRoom = dungeon.getEntry();
    }
/**
Returns the current inventory of the player.  If the player has no items, then no items are returned.
@return Arraylist containing every item in the player's inventory
*/
    ArrayList<String> getInventoryNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (Item item : inventory) {
            names.add(item.getPrimaryName());
        }
        return names;
    }
/**
Adds an item to the players inventory.
@param item to be added to players inventory
*/
    void addToInventory(Item item) /* throws TooHeavyException */ {
        inventory.add(item);
    }
/**
Removes an item from the players inventory.
@param item to be removed from players inventory
*/
    void removeFromInventory(Item item) {
        inventory.remove(item);
    }
    void removeFromCurrentRoom(Item item) {
	adventurersCurrentRoom.remove(item);
    }

    void removeItemFromDungeon(Item removeThis){
		dungeon.removeItem(removeThis);
	}
/**
Searches the player's inventory and the players current room for an item by the passed string name and returns the item by that name.  If no item is found, an exception is thrown. 
@throws Item.NoItemException if no item is found
*/
    Item getItemInVicinityNamed(String name) throws Item.NoItemException {

        // First, check inventory.
        for (Item item : inventory) {
            if (item.goesBy(name)) {
                return item;
            }
        }

        // Next, check room contents.
        for (Item item : adventurersCurrentRoom.getContents()) {
            if (item.goesBy(name)) {
                return item;
            }
        }

        throw new Item.NoItemException();
    }
/**
Searches the players current inventory for an item with the passed String name and returns the item by that name.
@throws Item.NoItemException If no item was found by that name.
*/
    Item getItemFromInventoryNamed(String name) throws Item.NoItemException {

        for (Item item : inventory) {
            if (item.goesBy(name)) {
                return item;
            }
        }
        throw new Item.NoItemException();
    }
/**
Returns the amount of weight in the player's inventory
*/
    int weightCarried() {
        int weight = 0;
        for (Item item : inventory) {
            weight += item.getWeight();
        }
        return weight;
    }
/**
returns the player's current room.
*/
    Room getAdventurersCurrentRoom() {
        return adventurersCurrentRoom;
    }
/**
Sets the player's current room to the room that is passed.
*/
    void setAdventurersCurrentRoom(Room room) {
        adventurersCurrentRoom = room;
    }
/**
returns the current dungeon object.
*/
    Dungeon getDungeon() {
        return dungeon;
    }
/**
Returns the player's current health.
*/
    int getAdventurersHealth(){return health;}
/**
Sets the players health to the players health minus the passed int
*/
    void subtractAdventurersHealthBy(int damage){health = health - damage;}
/**
Returns the player's current Score
*/
    int getAdventurersScore(){return score;}
/**
Adds the passed argument to the players score
*/
    void addToAdventurersScoreBy(int theScore){score = score + theScore;}
/**
Returns the total game time played
*/
    int getTotalTime(){return totalTime;}
/**
Adds the passed value to total time played
*/
    void addToTime(int time){totalTime = totalTime+time;}
/**
Returns player strength
*/
    int getStr(){return str;}
/**
returns player defence
*/
    int getDef(){return def;}
/**
Adds passed parameter to strength
*/
    void upStr(int strVar){str = str+strVar;}
    void removeNpc(Npc npc){
    	adventurersCurrentRoom.remove(npc);
    }
/**
Adds passed parameter to defence
*/
    void upDef(int defVar){def = def+defVar;}
    void lowerStr(int lowerBy){str -= lowerBy;}
    void lowerDef(int lowerBy){def -= lowerBy;}
}
