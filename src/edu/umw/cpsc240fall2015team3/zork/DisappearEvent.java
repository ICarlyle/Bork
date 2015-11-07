

package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Removes a certain item from the GameState and Dungeon.
*/
abstract class DisappearEvent {
		
		/**
		Constructs a {@link edu.umw.cpsc240fall2015team3.zork.DisappearEvent} object that will remove a specified item from {@link edu.umw.cpsc240fall2015team3.zork.GameState} and the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon} object.
		*/
		DisappearEvent(Item item){

		}
/**
Completely removes the passed item from the current Dungeon and GameState.
*/
    abstract String execute();

}
