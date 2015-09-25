
package edu.umw.bork.stephen;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GameState {

    public static class IllegalSaveFormatException extends Exception {
        public IllegalSaveFormatException(String e) {
            super(e);
        }
    }

    static String DEFAULT_SAVE_FILE = "/tmp/bork_save";
    static String SAVE_FILE_EXTENSION = ".sav";

    static String CURRENT_ROOM_LEADER = "Current room: ";
    static String HEALTH_LEADER = "Health: ";

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

    private GameState() {
        inventory = new ArrayList<Item>();
    }

    void restore(String filename) throws FileNotFoundException,
        IllegalSaveFormatException, Dungeon.IllegalDungeonFormatException {

        Scanner s = new Scanner(new FileReader(filename));

        s.nextLine();   // Throw away version indicator.
        String dungeonFileLine = s.nextLine();

        if (!dungeonFileLine.startsWith(Dungeon.FILENAME_LEADER)) {
            throw new IllegalSaveFormatException("No '" +
                Dungeon.FILENAME_LEADER + 
                "' after version indicator.");
        }

        dungeon = new Dungeon(dungeonFileLine.substring(
            Dungeon.FILENAME_LEADER.length()));
        dungeon.restoreState(s);

        s.nextLine();  // Throw away "Adventurer:".
        String currentRoomLine = s.nextLine();
        GameState.instance().setAdventurersCurrentRoom(dungeon.getRoom(
            currentRoomLine.substring(CURRENT_ROOM_LEADER.length())));
    }

    void store() throws IOException {
        store(DEFAULT_SAVE_FILE);
    }

    void store(String saveName) throws IOException {
        String filename = saveName + SAVE_FILE_EXTENSION;
        PrintWriter w = new PrintWriter(new FileWriter(filename));
        w.println("Bork v3.0 save data");
        dungeon.storeState(w);
        w.println(Dungeon.ADVENTURER_MARKER);
        w.println(CURRENT_ROOM_LEADER + 
            GameState.instance().getAdventurersCurrentRoom().getTitle());
        w.close();
    }

    void initialize(Dungeon dungeon) {
        this.dungeon = dungeon;
        adventurersCurrentRoom = dungeon.getEntry();
    }

    Dungeon getDungeon() {
        return dungeon;
    }

    ArrayList<Item> getInventory() {
        return inventory;
    }

    void addToInventory(Item item) /* throws TooHeavyException */ {
        inventory.add(item);
    }

    void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    Item getItemFromInventoryNamed(String name) 
        throws Item.NoItemException {

        Item item = Item.getItemNamed(name);
        if (inventory.contains(item)) {
            return item;
        }
        throw new Item.NoItemException();
    }

    Room getAdventurersCurrentRoom() {
        return adventurersCurrentRoom;
    }

    void setAdventurersCurrentRoom(Room room) {
        adventurersCurrentRoom = room;
    }

}
