

package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Removes a certain item from the GameState and Dungeon.
*/
class DisappearEvent extends Event{

	private String itemName
		/**
		Constructs a {@link edu.umw.cpsc240fall2015team3.zork.DisappearEvent} object that will remove a specified item from {@link edu.umw.cpsc240fall2015team3.zork.GameState} and the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon} object.
		*/
		DisappearEvent(String name){
			itemName = name;
		}
/**
Completely removes the passed item from the current Dungeon and GameState.
*/
    String execute(){
    return "";
    }

}
