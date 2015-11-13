
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

		/**Is called when there are no more {@link edu.umw.cpsc240fall2015team3.zork.Item} objects to be read from a .zork file.
		*/
    static class NoItemException extends Exception {}
    static Random rng = new Random();

    private String primaryName;
    private ArrayList<String> aliases;
    private int weight;
    private Hashtable<String,String[]> messages;
    private ArrayList<String> events;


		/**Initializes this {@link edu.umw.cpsc240fall2015team3.zork.Item}'s container variables.
		*/
    private void init() {
        messages = new Hashtable<String,String[]>();
        aliases = new ArrayList<String>();
	events = new ArrayList<String>();
    }

		
    /** Returns an {@link edu.umw.cpsc240fall2015team3.zork.Item} object read from the "Items" section of a .zork file by a Scanner.
        @param s The Scanner that reads the .zork file.

        @throws NoItemException The reader object is not positioned at the
        start of an {@link edu.umw.cpsc240fall2015team3.zork.Item} entry. A side effect of this is the reader's cursor
        is now positioned one line past where it was.
        @throws Dungeon.IllegalDungeonFormatException A structural problem with the
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
	System.out.println("baseString: " + verbLine);
            String[] verbParts = verbLine.split(":");
	System.out.println("verbParts[0]: " + verbParts[0]);
	System.out.println("verbparts[1]: " + verbParts[1]);
	String[] messageTexts = null;
	if (verbParts[0].contains("[")){ //event parsing
		System.out.println("aaa");
		String[] eventList = verbParts[0].split("[");
		System.out.println("eventList[0]: " + eventList[0]);
		System.out.println("eventList[1]: " + eventList[1]);
		//messages.put(eventList[0], verbParts[1]);
		String stringEvents = eventList[1].replace("]", "").trim();
		if (stringEvents.contains(",")){
			String[] allStringEvents = stringEvents.split(",");
			for (int i = 0; i < allStringEvents.length; i++){
				events.add(allStringEvents[i]);

	}
            //String[] verbAliases = verbParts[0].split(",");
	//System.out.println("verbAliases: " + verbAliases[0]);
	//System.out.println("verbAlias 2: " + verbAliases[1]);
	//System.out.println("MessagteTexts: " + messageTexts[0]);
                //messages.put(eventList[0], verbParts[1]);
            }
            
            verbLine = s.nextLine();
        }

		/**Returns whether or not this {@link edu.umw.cpsc240fall2015team3.zork.Item} has a certain primary or secondary name.

		@param name The alias to compare to the {@link edu.umw.cpsc240fall2015team3.zork.Item}'s primary and secondary names.
		*/
    boolean goesBy(String name) {
        return primaryName.equals(name) || aliases.contains(name);
    }

		/**Returns the primary name of this {@link edu.umw.cpsc240fall2015team3.zork.Item}.
		*/
    String getPrimaryName() { return primaryName; }

		/**Returns a message associated with an action the player takes with this {@link edu.umw.cpsc240fall2015team3.zork.Item}.

		@param verb The action the player uses with this {@link edu.umw.cpsc240fall2015team3.zork.Item}.
		*/
    public String getMessageForVerb(String verb) {
        String[] possibleMessages = messages.get(verb);
        return possibleMessages[rng.nextInt(possibleMessages.length)];
    }

		/**Returns a String that represents this {@link edu.umw.cpsc240fall2015team3.zork.Item}.
		*/
    public String toString() {
        return primaryName;
    }

		/**Returns the carrying weight of this {@link edu.umw.cpsc240fall2015team3.zork.Item}.
		*/
    public int getWeight() {
        return weight;
    }
    	
	public ArrayList<String> getEvents(){
		return events;
	}
}
