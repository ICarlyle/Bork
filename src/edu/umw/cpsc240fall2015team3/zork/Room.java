
package edu.umw.cpsc240fall2015team3.zork;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

/**
@author Robert Jamal Washington
*/
/**
Represents the individual sections of a {@link edu.umw.cpsc240fall2015team3.zork.Dungeon} that the player will traverse through.
*/
public class Room {

    class NoRoomException extends Exception {}

    static String CONTENTS_STARTER = "Contents: ";

    private String title;
    private String desc;
    private boolean beenHere;
    private ArrayList<Item> contents;
    private ArrayList<Exit> exits;
    private ArrayList<Npc> enemies;

		/**Returns an empty {@link edu.umw.cpsc240fall2015team3.zork.Room} with the specificied title.

		@param title The title to be given to the {@link edu.umw.cpsc240fall2015team3.zork.Room}.
		*/
    Room(String title) {
        init();
        this.title = title;
    }

		/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.Room} object generated from a Scanner object positioned at the "Room" section of a .zork file.

        @param d The containing {@link edu.umw.cpsc240fall2015team3.zork.Dungeon} object, necessary to retrieve {@link edu.umw.cpsc240fall2015team3.zork.Item} objects.

        @throws NoRoomException The reader object is not positioned at the
        start of a room entry. A side effect of this is the reader's cursor
        is now positioned one line past where it was.
        @throws Dungeon.IllegalDungeonFormatException A structural problem with the
        dungeon file itself, detected when trying to read this room.
		*/
    Room(Scanner s, Dungeon d) throws NoRoomException,
        Dungeon.IllegalDungeonFormatException {

        this(s, d, true);
    }

    /** Given a Scanner object positioned at the beginning of a "room" file
        entry, read and return a Room object representing it. 
        @param d The containing {@link edu.umw.cpsc240fall2015team3.zork.Dungeon} object, 
        necessary to retrieve {@link edu.umw.cpsc240fall2015team3.zork.Item} objects.
        @param initState should items listed for this room be added to it?
        @throws NoRoomException The reader object is not positioned at the
        start of a room entry. A side effect of this is the reader's cursor
        is now positioned one line past where it was.
        @throws Dungeon.IllegalDungeonFormatException A structural problem with the
        dungeon file itself, detected when trying to read this room.
	@param s The Scanner that reads the .sav file.
     */
    Room(Scanner s, Dungeon d, boolean initState) throws NoRoomException,
        Dungeon.IllegalDungeonFormatException {

        init();
        title = s.nextLine();
        desc = "";
        if (title.equals(Dungeon.TOP_LEVEL_DELIM)) {
            throw new NoRoomException();
        }
        
        String lineOfDesc = s.nextLine();
	
	if (lineOfDesc.contains("Enemies: ")){
		String[] enemiesLine = lineOfDesc.split(":");
		if (enemiesLine[1].contains(",")){
			String[] allEnemies = enemiesLine[1].split(",");
			for (int i = 0; i < allEnemies.length; i++){
				this.enemies.add(d.getNpc(allEnemies[i]));
			}
		}
		else{
			this.enemies.add(d.getNpc(enemiesLine[1]));
		}
		lineOfDesc = s.nextLine(); // Room descrption
	}
	System.out.println("Post Enemies:");
        while (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM) &&
               !lineOfDesc.equals(Dungeon.TOP_LEVEL_DELIM)) {

            if (lineOfDesc.startsWith(CONTENTS_STARTER)) {
                String itemsList = lineOfDesc.substring(CONTENTS_STARTER.length());
                String[] itemNames = itemsList.split(",");
                for (String itemName : itemNames) {
                    try {
                        if (initState) {
                            add(d.getItem(itemName));
				System.out.println(itemName);
                        }
                    } catch (Item.NoItemException e) {
                        throw new Dungeon.IllegalDungeonFormatException(
                            "No such item '" + itemName + "'");
                    }
                }
            } else {
                desc += lineOfDesc + "\n";
            }
            lineOfDesc = s.nextLine();
        }

        // throw away delimiter
        if (!lineOfDesc.equals(Dungeon.SECOND_LEVEL_DELIM)) {
            throw new Dungeon.IllegalDungeonFormatException("No '" +
                Dungeon.SECOND_LEVEL_DELIM + "' after room.");
        }
    }

    
		/**Initializes this {@link edu.umw.cpsc240fall2015team3.zork.Room} in a state specified by the contents of a .sav file. If the file read is not a valid .sav file, throws a {@link edu.umw.cpsc240fall2015team3.zork.GameState.IllegalSaveFormatException}.
		*/
    private void init() {
        contents = new ArrayList<Item>();
        exits = new ArrayList<Exit>();
	enemies = new ArrayList<Npc>();
        beenHere = false;
    }

		/**Returns the title of this {@link edu.umw.cpsc240fall2015team3.zork.Room}.
		*/
    String getTitle() { return title; }

		/**Sets the description of thiis {@link edu.umw.cpsc240fall2015team3.zork.Room}.

		@param desc The String to set the description to.
		*/
    void setDesc(String desc) { this.desc = desc; }

		/**Generates a part of a .zork file that preserves the current state of this {@link edu.umw.cpsc240fall2015team3.zork.Room}.

		@param w The PrintWriter that writes the .bork file for the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon}.
		*/
    void storeState(PrintWriter w) throws IOException {
        w.println(title + ":");
        w.println("beenHere=" + beenHere);
        if (contents.size() > 0) {
            w.print(CONTENTS_STARTER);
            for (int i=0; i<contents.size()-1; i++) {
                w.print(contents.get(i).getPrimaryName() + ",");
            }
            w.println(contents.get(contents.size()-1).getPrimaryName());
        }
        w.println(Dungeon.SECOND_LEVEL_DELIM);
    }

		/**Initializes this {@link edu.umw.cpsc240fall2015team3.zork.Room} in a state specified by the contents of a .sav file.

		@param s The Scanner that reads the .sav file.
		@param d The {@link edu.umw.cpsc240fall2015team3.zork.Dungeon} that contains this {@link edu.umw.cpsc240fall2015team3.zork.Room}.

		@throws GameState.IllegalSaveFormatException The file read is not a valid .sav file.
		*/
    void restoreState(Scanner s, Dungeon d) throws 
        GameState.IllegalSaveFormatException {

        String line = s.nextLine();
        if (!line.startsWith("beenHere")) {
            throw new GameState.IllegalSaveFormatException("No beenHere.");
        }
        beenHere = Boolean.valueOf(line.substring(line.indexOf("=")+1));

        line = s.nextLine();
        if (line.startsWith(CONTENTS_STARTER)) {
            String itemsList = line.substring(CONTENTS_STARTER.length());
            String[] itemNames = itemsList.split(",");
            for (String itemName : itemNames) {
                try {
                    add(d.getItem(itemName));
                } catch (Item.NoItemException e) {
                    throw new GameState.IllegalSaveFormatException(
                        "No such item '" + itemName + "'");
                }
            }
            s.nextLine();  // Consume "---".
        }
    }

		/**Returns information about this {@link edu.umw.cpsc240fall2015team3.zork.Room}, including its title, its description, and its {@link edu.umw.cpsc240fall2015team3.zork.Item} contents. After being called, changes the boolean beenHere to true.
		*/
    public String describe() {
        return describe(false);
    }
		
		/**Returns information about this {@link edu.umw.cpsc240fall2015team3.zork.Room}, including its title, its description, its {@link edu.umw.cpsc240fall2015team3.zork.Item} contents, and the {@link edu.umw.cpsc240fall2015team3.zork.Exit}s leading from this {@link edu.umw.cpsc240fall2015team3.zork.Room}. After being called, changes the boolean beenHere to true.

		@param full Determines whether or not to return the complete description of this {@link edu.umw.cpsc240fall2015team3.zork.Room}.
		*/
    public String describe(boolean full) {
        String description;
	//System.out.print("Time:["+GameState.instance().getTotalTime()+"]"); //Prints time
        if (beenHere && !full) {
            description = title;
        } else {
            description = title + "\n" + desc;
        }
        for (Item item : contents) {
            description += "\nThere is a " + item.getPrimaryName() + " here.";
        }
        if (contents.size() > 0) { description += "\n"; }
        if (!beenHere || full) {
            for (Exit exit : exits) {
                description += "\n" + exit.describe();
            }
        }
        //if(beenHere || full){for (Exit exit : exits){description += "\n" + exit.describe();}}  //Enable to see Exits everytime

        beenHere = true;
        return description;
    }
    
		/**Returns the {@link edu.umw.cpsc240fall2015team3.zork.Room} connected by the {@link edu.umw.cpsc240fall2015team3.zork.Exit} in the direction specified by the parameter. If there is no {@link edu.umw.cpsc240fall2015team3.zork.Exit} in that direction, returns NULL.

		@param dir The direction for which to search for an {@link edu.umw.cpsc240fall2015team3.zork.Exit}.
		*/
    public Room leaveBy(String dir) {
        for (Exit exit : exits) {
            if (exit.getDir().equals(dir)) {
                return exit.getDest();
            }
        }
        return null;
    }

		/**Connects this {@link edu.umw.cpsc240fall2015team3.zork.Room} to another {@link edu.umw.cpsc240fall2015team3.zork.Room} via an {@link edu.umw.cpsc240fall2015team3.zork.Exit}.

		@param exit The {@link edu.umw.cpsc240fall2015team3.zork.Exit} object to be added to the {@link edu.umw.cpsc240fall2015team3.zork.Room}.
		*/
    void addExit(Exit exit) {
        exits.add(exit);
    }

		/**Inserts an {@link edu.umw.cpsc240fall2015team3.zork.Item} into this {@link edu.umw.cpsc240fall2015team3.zork.Room}.

		@param item The {@link edu.umw.cpsc240fall2015team3.zork.Item} to be added to the {@link edu.umw.cpsc240fall2015team3.zork.Room}.
		*/
    void add(Item item) {
        contents.add(item);
    }

		/**Deletes an {@link edu.umw.cpsc240fall2015team3.zork.Item} from this {@link edu.umw.cpsc240fall2015team3.zork.Room}.

		@param item The {@link edu.umw.cpsc240fall2015team3.zork.Item} to be removed to the {@link edu.umw.cpsc240fall2015team3.zork.Room}.
		*/
    void remove(Item item) {
        contents.remove(item);
    }

				
		/**Returns the {@link edu.umw.cpsc240fall2015team3.zork.Item} present in the {@link edu.umw.cpsc240fall2015team3.zork.Room} that has the primary or secondary name given in the parameter. 	

		@param name The identifier used to search for the {@link edu.umw.cpsc240fall2015team3.zork.Item}.

		@throws Item.NoItemException There is no {@link edu.umw.cpsc240fall2015team3.zork.Item} that goes by the specified name present in the {@link edu.umw.cpsc240fall2015team3.zork.Room}
		*/
    Item getItemNamed(String name) throws Item.NoItemException {
        for (Item item : contents) {
            if (item.goesBy(name)) {
                return item;
            }
        }
        throw new Item.NoItemException();
    }

		/**Returns a collection of {@link edu.umw.cpsc240fall2015team3.zork.Item} objects contained by the {@link edu.umw.cpsc240fall2015team3.zork.Room}.
		*/
    ArrayList<Item> getContents() {
        return contents;
    }
}
