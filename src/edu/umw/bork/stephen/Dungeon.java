
package edu.umw.bork.stephen;

import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Dungeon {

    public static class IllegalDungeonFormatException extends Exception {
        public IllegalDungeonFormatException(String e) {
            super(e);
        }
    }

    // Variables relating to dungeon file (.bork) storage.
    public static String TOP_LEVEL_DELIM = "===";
    public static String SECOND_LEVEL_DELIM = "---";
    public static String ROOMS_MARKER = "Rooms:";
    public static String EXITS_MARKER = "Exits:";
    
    // Variables relating to game state (.sav) storage.
    static String DUNGEON_FILENAME_LEADER = "Dungeon file: ";

    private String name;
    private Room entry;
    private Hashtable<String,Room> rooms;
    private String filename;

    Dungeon(String name, Room entry) {
        init();
        this.filename = null;    // null indicates not hydrated from file.
        this.name = name;
        this.entry = entry;
    }

    /**
     * Read from the .bork filename passed, and instantiate a Dungeon object
     * based on it.
     */
    public Dungeon(String filename) throws IOException,
        IllegalDungeonFormatException {

        init();
        this.filename = filename;

        BufferedReader r = new BufferedReader(new FileReader(filename));
        name = r.readLine();

        r.readLine();   // Throw away version indicator.

        // Throw away delimiter.
        if (!r.readLine().equals(TOP_LEVEL_DELIM)) {
            throw new IllegalDungeonFormatException("No '" +
                TOP_LEVEL_DELIM + "' after version indicator.");
        }

        // Throw away Rooms starter.
        if (!r.readLine().equals(ROOMS_MARKER)) {
            throw new IllegalDungeonFormatException("No '" +
                ROOMS_MARKER + "' line where expected.");
        }


        try {
            // Instantiate and add first room (the entry).
            entry = new Room(r);
            add(entry);

            // Instantiate and add other rooms.
            while (true) {
                add(new Room(r));
            }
        } catch (Room.NoRoomException e) {  /* end of rooms */ }

        // Throw away Exits starter.
        if (!r.readLine().equals(EXITS_MARKER)) {
            throw new IllegalDungeonFormatException("No '" +
                EXITS_MARKER + "' line where expected.");
        }

        try {
            // Instantiate exits.
            while (true) {
                Exit exit = new Exit(r, this);
            }
        } catch (Exit.NoExitException e) {  /* end of exits */ }

        r.close();
    }
    
    // Common object initialization tasks, regardless of which constructor
    // is used.
    private void init() {
        rooms = new Hashtable<String,Room>();
    }

    /*
     * Store the current (changeable) state of this dungeon to the writer
     * passed.
     */
    void storeState(PrintWriter w) throws IOException {
        w.println(DUNGEON_FILENAME_LEADER + getFilename());
    }

    /*
     * Restore the (changeable) state of this dungeon to that reflected in the
     * reader passed.
     */
    void restoreState(BufferedReader r) throws IOException {
        // Nothing to do for now.
    }

    public Room getEntry() { return entry; }
    public String getName() { return name; }
    public String getFilename() { return filename; }
    public void add(Room room) { rooms.put(room.getTitle(),room); }

    public Room getRoom(String roomTitle) {
        return rooms.get(roomTitle);
    }
}
