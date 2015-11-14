/**
@author Alec
*/

package edu.umw.cpsc240fall2015team3.zork;
/**
The singleton EventFactory class instantiates unique Event objects that are derived from item-tied Commands.  New event objects can be instantiated inside of Commands.  Events are directly tied with {@link edu.umw.cpsc240fall2015team3.zork.GameState} as they directly affect a player's interaction with the dungeon.   
*/
public class EventFactory{

    private static EventFactory theInstance;

    public static synchronized EventFactory instance() {
	if (theInstance == null) {
	    theInstance = new EventFactory();
	}
	return theInstance;
    }
	private EventFactory(){
	}
/**
Returns an Event object based on the passed String that will directly influence the {@link edu.umw.cpsc240fall2015team3.zork.GameState}.  
@param event String that contains the name of the new Event
@return Event object 
*/
//eventName is the type of action
//action is any string after the type
//numberVal is action converted to number
	public Event parse(String event){
	String parts[] = event.split(" ");
	String eventName = parts[0];
	String noun = parts.length >= 2 ? parts[1] : "";
	int numberVal = 0;
	if(noun.contains("1")||noun.contains("2")||noun.contains("3")||noun.contains("4")||noun.contains("5")||noun.contains("6")||noun.contains("7")||noun.contains("8")||noun.contains("9")||noun.contains("0")){numberVal = Integer.parseInt(noun);}
	
	if(eventName.contains("AddScore")){return new AddScoreEvent(numberVal);}
	if(eventName.contains("Die")){return new DieEvent();}
	if(eventName.contains("Disappear")){return new DisappearEvent(noun);}
	if(eventName.contains("LowerHealth")){return new LowerHealthEvent(numberVal);}
	if(eventName.contains("Teleport")){return new TeleportEvent();}
	if(eventName.contains("Transform")){return new TransformEvent(noun,noun);}
	if(eventName.contains("Win")){return new WinEvent();}
	else{return new AddScoreEvent(0);}
	}
	
}
