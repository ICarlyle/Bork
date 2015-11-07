/**
@author Alec
*/

package edu.umw.cpsc240fall2015team3.zork;
/**
The singleton EventFactory class instantiates unique Event objects that are derived from item-tied Commands.  New event objects can be instantiated inside of Commands.  Events are directly tied with {@link edu.umw.cpsc240fall2015team3.zork.GameState} as they directly affect a player's interaction with the dungeon.   
*/
public class EventFactory{

	private EventFactory(){
	}
/**
Returns an Event object based on the passed String that will directly influence the {@link edu.umw.cpsc240fall2015team3.zork.GameState}.  
@param event String that contains the name of the new Event
@return Event object 
*/
	public Event parse(String event){
		return null;
	}

}
