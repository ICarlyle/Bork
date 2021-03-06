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
//System.out.println("===>-<===>-<EventFactory>-<===>-<===");
//System.out.println("STARTER: "+event);
	int numberVal = 0;
	String param = "";
	String param1 = "";
	String param2 = "";
	String item= "";
	String eventName = "";
	//System.out.println("event: " + event);
	if (event.contains(":")){
	String[] itemAndEvent = event.split(":");
	eventName = itemAndEvent[0];
	item = itemAndEvent[1];
//System.out.println(eventName+"'"+item);
	}
	if (event.contains("(")){
		String[] verb = eventName.split("\\(");
		//System.out.println("verb[0] " + verb[0]);
		//System.out.println("verb[1] " + verb[1]);
		verb[1].replace(")", "").trim();
	
	if (event.contains("1") || event.contains("2") || event.contains("3")
	|| event.contains("4") || event.contains("5") || event.contains("6")
	|| event.contains("7") || event.contains("8") || event.contains("9")
	|| event.contains("0")){
		numberVal = Integer.parseInt(verb[1].replace(")", "").trim());
		}
	else{
		param = eventName;
		if (param.contains(",")){
			String[] allNouns = param.split(",");
			param1 = allNouns[0];
			param2 = allNouns[1];
		}
		else{
		param1 = verb[1].replace(")","").trim();
		}
	}
	}

	//System.out.println("EventName: " + eventName);
	if(event.contains("Score")){//System.out.println("§ScoreEventTriggered");
return new AddScoreEvent(numberVal);}
	if(event.contains("Die")){//System.out.println("§DieEventTriggered");
return new DieEvent(event);}
	if(event.contains("Disappear")){//System.out.println("§DisappearEventTriggered");
return new DisappearEvent(item);}
	if(event.contains("Wound")){//System.out.println("§WoundEventTriggered");
//System.out.println(numberVal);
return new LowerHealthEvent(numberVal);}
        if(event.contains("Teleport(")){//System.out.println("§TeleportSPECEventTriggered");
return new TeleportEvent(numberVal);}
	if(event.contains("Teleport")){//System.out.println("§TeleportEventTriggered");
return new TeleportEvent();}
	if(event.contains("Transform")){//System.out.println("§TransformEventTriggered");
return new TransformEvent(item,param1);}
	if(event.contains("Win")){//System.out.println("§WinEventTriggered");
return new WinEvent(event);}
	if(event.contains("Unlock")){//System.out.println("Unlock event triggered");
		String[] eventNameParts = eventName.split("\\(");
		String unlockRoomName = eventNameParts[1].replace(")", "");
		System.out.println(unlockRoomName);
		return new UnlockEvent(unlockRoomName);
	}
	else{
System.out.println("§ElseEventTriggered");return new LowerHealthEvent(0);}
}	
}
