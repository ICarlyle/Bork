
package edu.umw.cpsc240fall2015team3.zork;

import java.util.Scanner;

/**
@author Robert Jamal Washington
*/
/**
Connects one {@link edu.umw.cpsc240fall2015team3.zork.Room} object to another {@link edu.umw.cpsc240fall2015team3.zork.Room} object.
*/
public class Exit {

    class NoExitException extends Exception {}

    private String dir;
    private Room src, dest;

		/**Returns an {@link edu.umw.cpsc240fall2015team3.zork.Exit} object that connects a source {@link edu.umw.cpsc240fall2015team3.zork.Room} with a destination {@link edu.umw.cpsc240fall2015team3.zork.Room} in a specific direction.

		@param dir The direction this {@link edu.umw.cpsc240fall2015team3.zork.Exit} faces.
		@param src The source {@link edu.umw.cpsc240fall2015team3.zork.Room} of this {@link edu.umw.cpsc240fall2015team3.zork.Exit}.
		@param dest The destination {@link edu.umw.cpsc240fall2015team3.zork.Room} of this {@link edu.umw.cpsc240fall2015team3.zork.Exit}.
		*/
    Exit(String dir, Room src, Room dest) {
        init();
        this.dir = dir;
        this.src = src;
        this.dest = dest;
        src.addExit(this);
    }

    /** Given a Scanner object positioned at the beginning of an "exit" file
        entry, read and return an Exit object representing it. 
        @param d The dungeon that contains this exit (so that Room objects 
        may be obtained.)
        @throws NoExitException The reader object is not positioned at the
        start of an exit entry. A side effect of this is the reader's cursor
        is now positioned one line past where it was.
        @throws Dungeon.IllegalDungeonFormatException A structural problem with the
        dungeon file itself, detected when trying to read this room.
     */
    Exit(Scanner s, Dungeon d) throws NoExitException,
        Dungeon.IllegalDungeonFormatException {

        init();
        String srcTitle = s.nextLine();
        if (srcTitle.equals(Dungeon.TOP_LEVEL_DELIM)) {
            throw new NoExitException();
        }
        src = d.getRoom(srcTitle);
        dir = s.nextLine();
        dest = d.getRoom(s.nextLine());
        
        // I'm an Exit object. Great. Add me as an exit to my source Room too,
        // though.
        src.addExit(this);

        // throw away delimiter
        if (!s.nextLine().equals(Dungeon.SECOND_LEVEL_DELIM)) {
            throw new Dungeon.IllegalDungeonFormatException("No '" +
                Dungeon.SECOND_LEVEL_DELIM + "' after exit.");
        }
    }

    // Common object initialization tasks.
    private void init() {
    }


		/**Returns information about the direction this {@link edu.umw.cpsc240fall2015team3.zork.Exit} faces and which {@link edu.umw.cpsc240fall2015team3.zork.Room} it leads to.
		*/
    String describe() {
        return "You can go " + dir + " to " + dest.getTitle() + ".";
    }

		/**Returns the direction that this {@link edu.umw.cpsc240fall2015team3.zork.Exit} faces.
		*/
    String getDir() { return dir; }
		
		/**Returns the {@link edu.umw.cpsc240fall2015team3.zork.Room} that produces this {@link edu.umw.cpsc240fall2015team3.zork.Exit}.
		*/
    Room getSrc() { return src; }

		/**Returns the {@link edu.umw.cpsc240fall2015team3.zork.Room} that this {@link edu.umw.cpsc240fall2015team3.zork.Exit} connects to.
		*/
    Room getDest() { return dest; }
}
