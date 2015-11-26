
package edu.umw.cpsc240fall2015team3.zork;
/**
@author Alec
*/

import java.util.Hashtable;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;
/**
The Dungeon class represents the dungeon that the player will play through.  This class includes methods for adding/getting items, getting/adding rooms, storing and restoring the state of the dungeon.  The Dungeon object is read in from a .zork/.sav file. 
*/
public class Dungeon {
		/**Is called when a {@link edu.umw.cpsc240fall2015team3.zork.Dungeon} is attempted to be read from an invalid .zork or .sav file.
		*/
    public static class IllegalDungeonFormatException extends Exception {
        public IllegalDungeonFormatException(String e) {
            super(e);
        }
    }

    // Variables relating to both dungeon file and game state storage.
    public static String TOP_LEVEL_DELIM = "===";
    public static String SECOND_LEVEL_DELIM = "---";

    // Variables relating to dungeon file (.bork) storage.
    public static String ENEMIES_MARKER = "Enemies:";
    public static String ROOMS_MARKER = "Rooms:";
    public static String EXITS_MARKER = "Exits:";
    public static String ITEMS_MARKER = "Items:";
    
    // Variables relating to game state (.sav) storage.
    static String FILENAME_LEADER = "Dungeon file: ";
    static String ROOM_STATES_MARKER = "Room states:";

    private String name;
    private Room entry;
    private Hashtable<String,Room> rooms;
    private Hashtable<String,Item> items;
    private Hashtable<String,Npc> enemies;
    private String filename;
/**
@param name String that represents the name of this dungeon
@param entry Room object that will be the first room of the dungeon.
*/
    Dungeon(String name, Room entry) {
        init();
        this.filename = null;    // null indicates not hydrated from file.
        this.name = name;
        this.entry = entry;
        rooms = new Hashtable<String,Room>();
	//enemies = new Hashtable<String,Npc>();
    }

/**
Reads from the file passed and instantiates a new dungeon based on it.
@param filename String that holds onto the .zork dungeon.
@throws IllegalDungeonFormatException if the .zork file is inaccurate to the format specifacations
@throws FileNotFoundException if the system cannot find the .zork file
*/
    public Dungeon(String filename) throws FileNotFoundException, 
        IllegalDungeonFormatException {

        this(filename, true);
    }

    /**
     * Read from the .zork filename passed, and instantiate a Dungeon object
     * based on it, including (possibly) the items in their original locations.
	@throws FileNotFoundException if the system cannot find the file
	@throws IllegalDungeonFormatException if the .zork file format is inaccurate
     */
    public Dungeon(String filename, boolean initState) 
        throws FileNotFoundException, IllegalDungeonFormatException {

        init();
        this.filename = filename;

        Scanner s = new Scanner(new FileReader(filename));
        name = s.nextLine();

        s.nextLine();   // Throw away version indicator.

        // Throw away delimiter.
        if (!s.nextLine().equals(TOP_LEVEL_DELIM)) {
            throw new IllegalDungeonFormatException("No '" +
                TOP_LEVEL_DELIM + "' after version indicator.");
        }
	// Throw away Enemies Starter
	if (!s.nextLine().equals(ENEMIES_MARKER)) {
		throw new IllegalDungeonFormatException("No '" +
			ENEMIES_MARKER + "' line where expected.");
	}
	try {
		while(true){
			//add(new Npc(s));
			add(new Npc(s));
			//System.out.println("NpcNameFromDung: " + npc.getName());
			//System.out.println("DungNpcName: " + npc.getName());
		}
	} catch (Npc.NoNpcException e){} // end of enemies section
        // Throw away Items starter.
        if (!s.nextLine().equals(ITEMS_MARKER)) {
            throw new IllegalDungeonFormatException("No '" +
                ITEMS_MARKER + "' line where expected.");
        }

        try {
            // Instantiate items.
            while (true) {
                add(new Item(s));
            }
        } catch (Item.NoItemException e) {  /* end of items */ }
	System.out.println("Item parsing fin");
        // Throw away Rooms starter.
        if (!s.nextLine().equals(ROOMS_MARKER)) {
            throw new IllegalDungeonFormatException("No '" +
                ROOMS_MARKER + "' line where expected.");
        }

        try {
            // Instantiate and add first room (the entry).
            entry = new Room(s, this, initState);
            add(entry);

            // Instantiate and add other rooms.
            while (true) {
                add(new Room(s, this, initState));
            }
        } catch (Room.NoRoomException e) {  /* end of rooms */ }

        // Throw away Exits starter.
        if (!s.nextLine().equals(EXITS_MARKER)) {
            throw new IllegalDungeonFormatException("No '" +
                EXITS_MARKER + "' line where expected.");
        }

        try {
            // Instantiate exits.
            while (true) {
                Exit exit = new Exit(s, this);
            }
        } catch (Exit.NoExitException e) {  /* end of exits */ }

        s.close();
    }
    
/**
Initializes new variables for the dungeon object in a new/restored dungeon
*/
    private void init() {
        rooms = new Hashtable<String,Room>();
        items = new Hashtable<String,Item>();
	enemies = new Hashtable<String,Npc>();
    }

    /**
     * Store the current (changeable) state of this dungeon to the writer
     * passed.
	@param w PrintWriter that will save the state of this dungeon.
     */
    void storeState(PrintWriter w) throws IOException {
        w.println(FILENAME_LEADER + getFilename());
        w.println(ROOM_STATES_MARKER);
	//System.out.println("Dung Store: " + ROOM_STATES_MARKER);
        for (Room room : rooms.values()) {
            room.storeState(w);
        }
        w.println(TOP_LEVEL_DELIM);
    }

    /**
     * Restore the (changeable) state of this dungeon to that reflected in the
     * reader passed.
	@param s Scanner that reads from a .sav file in order to restore a dungeon object.
	@throws GameState.IllegalSaveFormatException if the .sav file format is inaccurate
     */
    void restoreState(Scanner s) throws GameState.IllegalSaveFormatException {

        // Note: the filename has already been read at this point.
        
        if (!s.nextLine().equals(ROOM_STATES_MARKER)) {
            throw new GameState.IllegalSaveFormatException("No '" +
                ROOM_STATES_MARKER + "' after dungeon filename in save file.");
        }

        String roomName = s.nextLine();
        while (!roomName.equals(TOP_LEVEL_DELIM)) {
            getRoom(roomName.substring(0,roomName.length()-1)).
                restoreState(s, this);
            roomName = s.nextLine();
        }
    }
/**
Returns the room that is the entrance of this dungeon.
*/
    public Room getEntry() { return entry; }
/**
Returns the String that is the name of this dungeon.
*/
    public String getName() { return name; }
/**
Returns the string that is the filename of this dungeon.
*/
    public String getFilename() { return filename; }
/**
Stores a room object into this dungeon so that it can be retrieved later.  If the same room is added twice, no effect.  If room is null, nothing happens.  If 2 rooms with the same name are added, the first room is replaced with the second.
@param room the room to be added
*/
    public void add(Room room) { 
	this.rooms.put(room.getTitle(),room); }
    public void add(Npc npc) {
	System.out.println("[enemiesPut]: " + npc.getName());
	System.out.println("enemies: " + this.enemies);
	this.enemies.put(npc.getName(),npc);  }

    public Npc getNpc(String npc){
	return enemies.get(npc);
    }
/**
Stores an item object into this dungeon so that it can be retrieved later.  If the same item is added twice, no effect.  If the item is null, nothing happens.  If 2 items with the same name are added, the first item  is replaced with the second.
*/
    public void add(Item item) { items.put(item.getPrimaryName(),item); }
    public void removeItem(Item theItem){
		String key;
		items.remove(theItem.getPrimaryName());
		Set<String> keySet = rooms.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()){
			key = it.next();
			ArrayList<Item> items = getRoom(key).getContents();
			for (int i = 0; i < items.size(); i++){
				if (items.get(i).getPrimaryName().equals(theItem.getPrimaryName())){
				entry.remove(theItem);
				}
			}
		}
	}
/**
Returns a room object with the same title as the passes String.  If there is no room with the passed title, returns null.
*/
    public Room getRoom(String roomTitle) {
        return rooms.get(roomTitle);
    }

    /**
     * Returns the Item object whose primary name is passed.  
	@throws Item.NoItemException if the passed string does not match any items
     */
    public Item getItem(String primaryItemName) throws Item.NoItemException {
        if (items.get(primaryItemName) == null) {
            throw new Item.NoItemException();
        }
        return items.get(primaryItemName);
    }
/**
Returns Number of the Rooms contained in this Dungeon.
*/
public int getNumberRooms() {
    return rooms.size();
}
/**
Returns a Random Room contained in this Dungeon.
*/
public Room getNumberedRoom() {
//System.out.println("Stan TELEPORT");
    Object[] keys = rooms.keySet().toArray();
    Object key = keys[new Random().nextInt(keys.length)];
    return rooms.get(key);
}
/**
Returns a specific Room contained in this Dungeon.
*/
public Room getSpecRoom(int roomNum) {
//System.out.println("Spec Tele ACTIVATE");
    Object[] keys = rooms.keySet().toArray();
    while(roomNum > keys.length){roomNum = roomNum - keys.length;}
    Object key = keys[roomNum];
    return rooms.get(key);
}

/**
Returns all of the Rooms contained in this Dungeon.
*/
//    public ArrayList<Room> getAllRooms() { 
//	 return rooms.values();
//    }
}
