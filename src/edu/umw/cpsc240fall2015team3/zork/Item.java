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
    private int weight, str, def;
    private ArrayList<String> MessageArrayList;
    private Hashtable<String,String> messages;
    //private ArrayList<String> events;
    private Hashtable<String, ArrayList<String>> events;
    private ArrayList<String> eventArray;

		/**Initializes this {@link edu.umw.cpsc240fall2015team3.zork.Item}'s container variables.
		*/
    private void init() {
        messages = new Hashtable<String,String>();
        aliases = new ArrayList<String>();
	//events = new ArrayList<String>();
   	events = new Hashtable<String, ArrayList<String>>();
	eventArray = new ArrayList<String>();
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
	String[] weightLine = s.nextLine().split(":");
        this.weight = Integer.valueOf(weightLine[1]);
	String[] strLine = s.nextLine().split(":");
	this.str = Integer.valueOf(strLine[1]);
	String[] defLine = s.nextLine().split(":");
	this.def = Integer.valueOf(defLine[1]);
	//GameState.instance().upStr(str);
	//GameState.instance().upDef(def);
	
	ArrayList<String> messageHolder = new ArrayList<String>();

        // Read and parse verbs lines, as long as there are more.
        String verbLine = s.nextLine();
        while (!verbLine.equals(Dungeon.SECOND_LEVEL_DELIM)) {
            if (verbLine.equals(Dungeon.TOP_LEVEL_DELIM)) {
                throw new Dungeon.IllegalDungeonFormatException("No '" +
                    Dungeon.SECOND_LEVEL_DELIM + "' after item.");
            }
	    //System.out.println("baseString: " + verbLine);
            String[] verbParts = verbLine.split(":");
	    //System.out.println("verbParts[0]: " + verbParts[0]);
	    //System.out.println("verbparts[1]: " + verbParts[1]);
	    String verbAndAct = verbParts[0];
	    String verbMessage= verbParts[1];
	    String verb = verbAndAct; //Sets basic verb to left phrase
	    if(verbAndAct.contains("[")){ //If have extras, shorten to base verb
		String verbFormer = verbAndAct.replace("[","SPLIT");
		String[] verbFormerSplit = verbFormer.split("SPLIT");
		String newVerb = verbFormerSplit[0];
		verb = newVerb;
	    }
//System.out.println(verb+" SPLIT "+verbMessage);
	    messages.put(verb, verbMessage);


	    if (verbAndAct.contains("[")){ //event parsing
		verbAndAct = verbAndAct.replace("[","SPLIT");
		verbAndAct = verbAndAct.replace("]","");
//System.out.println("\nverbAndAct: "+verbAndAct);
		String[] verbAndActSplit = verbAndAct.split("SPLIT");
		String eventSegment = verbAndActSplit[1];
//System.out.println("\nVERB:'"+verb+"' | EVENTS:'"+eventSegment+"'");
		String[] eventList = eventSegment.split(",");
		int eventNum = eventSegment.length()-eventSegment.replace(",","").length()+1;
		int i=0;
		//ArrayList<String> eventArray = new ArrayList<String>();
		while(i<eventNum){
		//System.out.println("Event:'"+eventList[i]+"' Added to"+verb);
		eventArray.add(eventList[i]);
		//Add single element to an event list here
		//I have separated The Strings:  [verb][actions][message]
		i++;		
		}
		events.put(verb,eventArray);
		eventArray = new ArrayList<String>();
		//messages.put(eventList[0], verbParts[1]);
//		if (stringEvents.contains(",")){
//			String[] allStringEvents = stringEvents.split(",");
//			for (int i = 0; i < allStringEvents.length; i++){
//				events.add(allStringEvents[i]);
//			}
            //String[] verbAliases = verbParts[0].split(",");
	//System.out.println("verbAliases: " + verbAliases[0]);
	//System.out.println("verbAlias 2: " + verbAliases[1]);
	//System.out.println("MessagteTexts: " + messageTexts[0]);
                //messages.put(eventList[0], verbParts[1]);
            }
	    verbLine = s.nextLine();
	}
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
    public String getMessageForVerb(String verbVar) {
	if(messages.get(verbVar)==null){return "";}
	else{return messages.get(verbVar);}
	//String[] possibleMessages = messages.get(verb);
        //return possibleMessages[rng.nextInt(possibleMessages.length())];
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

    public int getStr(){
	return str;
    }

    public int getDef(){
	return def;
    }
    	
    public ArrayList<String> getEvents(String verbVar){
	return events.get(verbVar);
    }
		
		public ArrayList<String> getAliases(){
			return aliases;
		}

		//public Hashtable<String, String> getMessages(){
		//	return messages;
		//}

		public void setAliases(ArrayList<String> theAliases){
			aliases = theAliases;
		}

//		public void setEvents(ArrayList<String> theEvents){
//			events = theEvents;
//		}

//		public void setMessages(Hashtable<String,String> theMessages){
//			messages = theMessages;
//		}

		public void setPrimaryName(String thePrimaryName){
			primaryName = thePrimaryName;
		}
		public void setWeight(int theWeight){
			weight = theWeight;
		}
}
