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
The GameState class stores variables that need to accessed on the fly. It also stores a pointer to a dungeon, player stats, and time.
@param health int that is the player's current health
@param score int that is the player's current score
@param str int that measures how strong the player is
@param def int that measures how much damage the player can avoid
@param totalTime int that stores how much time has passed in the dungeon
*/
public class GameState {
/**$
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

    static synchronized GameState instance() {
        if (theInstance == null) {
            theInstance = new GameState();
        }
        return theInstance;
    }
/**$
Instantiates objects on creation of GameState
*/
    private GameState() {
        inventory = new ArrayList<Item>();
    }
/**$(More)
Restores the dungeon to a previous save state with the passed parameter as the title.
If there is no file by that filename throw
@param filename name of file that is to be restored
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
/**$
Runs the store method with a default save title argument.
@throws IOException if there is an error saving the file
*/
    void store() throws IOException {
        store(DEFAULT_SAVE_FILE);
    }
/**$(Add throws)
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
            }
            w.println(inventory.get(inventory.size()-1).getPrimaryName());
        }
        w.close();
    }
/**$
Stores the passed parameter as the current dungeon.
*/
    void initialize(Dungeon dungeon) {
        this.dungeon = dungeon;
        adventurersCurrentRoom = dungeon.getEntry();
    }
/**$
Instantiates and returns an arraylist of item names in the form of strings
*/
    ArrayList<String> getInventoryNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (Item item : inventory) {
            names.add(item.getPrimaryName());
        }
        return names;
    }
/**$
Adds an item to the players inventory.
@param item to be added to players inventory
*/
    void addToInventory(Item item) /* throws TooHeavyException */ {
        inventory.add(item);
    }
/**$
Removes an item from the players inventory.
@param item to be removed from players inventory
*/
    void removeFromInventory(Item item) {
        inventory.remove(item);
    }
/**$
Searches the player's inventory and the players current room for an item with the passed string name and returns the item by that name. 
If no item was found throw NoItemException
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
/**$
Searches the players current inventory for an item with the passed String name and returns the item by that name.
If no item was found by that name throw NoItemException.
*/
    Item getItemFromInventoryNamed(String name) throws Item.NoItemException {

        for (Item item : inventory) {
            if (item.goesBy(name)) {
                return item;
            }
        }
        throw new Item.NoItemException();
    }
/**$

*/
    int weightCarried() {
        int weight = 0;
        for (Item item : inventory) {
            weight += item.getWeight();
        }
        return weight;
    }
/**$
returns the player's current room.
*/
    Room getAdventurersCurrentRoom() {
        return adventurersCurrentRoom;
    }
/**$
Sets the player's current room to the room that is passed.
*/
    void setAdventurersCurrentRoom(Room room) {
        adventurersCurrentRoom = room;
    }
/**$
returns the current dungeon object.
*/
    Dungeon getDungeon() {
        return dungeon;
    }
/**$
Returns the player's current health.
*/
    void getAdventurersHealth(){}
/**$
Sets the players health to the players health minus the passed int
*/
    void subtractAdventurersHealthBy(int damage){}
/**$
Returns the player's current Score
*/
    void getAdventurersScore(){}
/**$

*/
    void addToAdventurersScoreBy(int theScore){}
/**$
*/
    void getTotalTime(){}
/**$
*/
    void addToTime(int time){}
/**$
*/
    void getStr(){}
/**$
*/
    void getDef(){}
/**$
*/
    void upStr(int str){}
/**$
*/
    void upDef(int def){}
    
    
}
