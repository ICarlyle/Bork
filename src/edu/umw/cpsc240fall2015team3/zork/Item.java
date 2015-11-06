
package edu.umw.cpsc240fall2015team3.zork;

import java.util.Scanner;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
@author Robert Jamal Washington
*/
/**
Represents objects that can be found in rooms and be interacted with by the player.
*/
public class Item {

    static class NoItemException extends Exception {}
    static Random rng = new Random();

    private String primaryName;
    private ArrayList<String> aliases;
    private int weight;
    private Hashtable<String,String[]> messages;


		/**Initializes this hlnkItem}'s container variables.
		*/
    private void init() {
        messages = new Hashtable<String,String[]>();
        aliases = new ArrayList<String>();
    }

		
    /** Returns an hlnkItem} object read from the "Items" section of a .zork file by a Scanner.
        @param s The Scanner that reads the .zork file.

        @throws NoItemException The reader object is not positioned at the
        start of an hlnkItem} entry. A side effect of this is the reader's cursor
        is now positioned one line past where it was.
        @throws IllegalDungeonFormatException A structural problem with the
        dungeon file itself, detected when trying to read this room.
		*/
    Item(Scanner s) throws NoItemException,
        Dungeon.IllegalDungeonFormatException {

        init();

        // Read item name.
        String namesLine = s.nextLine();
        if (namesLine.equals(Dungeon.TOP_LEVEL_DELIM)) {
            throw new NoItemException();
        }

        String[] names = namesLine.split(",");
        primaryName = names[0];
        for (int i=1; i<names.length; i++) {
            aliases.add(names[i]);
        }
        

        // Read item weight.
        weight = Integer.valueOf(s.nextLine());

        // Read and parse verbs lines, as long as there are more.
        String verbLine = s.nextLine();
        while (!verbLine.equals(Dungeon.SECOND_LEVEL_DELIM)) {
            if (verbLine.equals(Dungeon.TOP_LEVEL_DELIM)) {
                throw new Dungeon.IllegalDungeonFormatException("No '" +
                    Dungeon.SECOND_LEVEL_DELIM + "' after item.");
            }
            String[] verbParts = verbLine.split(":");
            String[] verbAliases = verbParts[0].split(",");
            String[] messageTexts = verbParts[1].split("\\|");
            for (String verbAlias : verbAliases) {
                messages.put(verbAlias, messageTexts);
            }
            
            verbLine = s.nextLine();
        }
    }

		/**Returns whether or not this hlnkItem} has a certain primary or secondary name.

		@param name The alias to compare to the hlnkItem}'s primary and secondary names.
		*/
    boolean goesBy(String name) {
        return primaryName.equals(name) || aliases.contains(name);
    }

		/**Returns the primary name of this hlnkItem}.
		*/
    String getPrimaryName() { return primaryName; }

		/**Returns a message associated with an action the player takes with this hlnkItem}.

		@param verb The action the player uses with this hlnkItem}.
		*/
    public String getMessageForVerb(String verb) {
        String[] possibleMessages = messages.get(verb);
        return possibleMessages[rng.nextInt(possibleMessages.length)];
    }

		/**Returns a String that represents this hlnkItem}.
		*/
    public String toString() {
        return primaryName;
    }

		/**Returns the carrying weight of this hlnkItem}.
		*/
    public int getWeight() {
        return weight;
    }
}
