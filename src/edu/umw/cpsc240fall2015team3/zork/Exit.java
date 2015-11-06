
package edu.umw.cpsc240fall2015team3.zork;

import java.util.Scanner;

public class Exit {

    class NoExitException extends Exception {}

    private String dir;
    private Room src, dest;

		/**Returns an hlnkExit} object that connects a source hlnkRoom} with a destination hlnkRoom} in a specific direction.

		@param dir The direction this hlnkExit} faces.
		@param src The source hlnkRoom} of this hlnkExit}.
		@param dest The destination hlnkRoom} of this hlnkExit}.
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
        @throws IllegalDungeonFormatException A structural problem with the
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


		/**Returns information about the direction this hlnkExit} faces and which hlnkRoom} it leads to.
		*/
    String describe() {
        return "You can go " + dir + " to " + dest.getTitle() + ".";
    }

		/**Returns the direction that this hlnkExit} faces.
		*/
    String getDir() { return dir; }
		
		/**Returns the hlnkRoom} that produces this hlnkExit}.
		*/
    Room getSrc() { return src; }

		/**Returns the hlnkRoom} that this hlnkExit} connects to.
		*/
    Room getDest() { return dest; }
}
