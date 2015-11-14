/**
@author Alec
*/

package edu.umw.cpsc240fall2015team3.zork;

import java.util.Scanner;
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
	
	int numberVal = 0;
	String param = "";
	String param1 = "";
	String param2 = "";
	//System.out.println("event: " + event);
	String[] itemAndEvent = event.split(":");
	String eventName = itemAndEvent[0];
	String item = itemAndEvent[1];
	if (eventName.contains("(")){
		String[] verb = item.split("\\(");
		System.out.println("verb[0] " + verb[0]);
		System.out.println("verb[1] " + verb[1]);
		verb[1].replace(")", "").trim();
	
	if (event.contains("1") || event.contains("2") || event.contains("3")
	|| event.contains("4") || event.contains("5") || event.contains("6")
	|| event.contains("7") || event.contains("8") || event.contains("9")
	|| event.contains("0")){
		numberVal = Integer.parseInt(verb[1]);
		}
	else{
		param = eventName;
		if (param.contains(",")){
			String[] allNouns = param.split(",");
			param1 = allNouns[0];
			param2 = allNouns[1];
		}
		else{
		param1 = verb[1];
		}
	}
	}
	
	if(event.contains("Score")){return new AddScoreEvent(numberVal);}
	if(event.contains("Die")){return new DieEvent();}
	if(event.contains("Disappear")){return new DisappearEvent(item);}
	if(event.contains("Wound")){return new LowerHealthEvent(numberVal);}
	if(event.contains("Teleport")){return new TeleportEvent();}
	if(event.contains("Transform")){return new TransformEvent(param1,param2);}
	if(event.contains("Win")){return new WinEvent();}
	else{return new AddScoreEvent(0);}
	}
	
}
