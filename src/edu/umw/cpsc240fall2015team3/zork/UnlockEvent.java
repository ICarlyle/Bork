
package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Unlocks a locked {@link edu.umw.cpsc240fall2015team3.zork.Room} in the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon}.
*/
class UnlockEvent extends Event{
		
		private Room lockedRoom;

		/**Returns an {@link edu.umw.cpsc240fall2015team3.zork.UnlockEvent} that uses a specific {@link edu.umw.cpsc240fall2015team3.zork.Room} location.

		@param lockedRoom The {@link edu.umw.cpsc240fall2015team3.zork.Room} that is to be unlocked.
		*/	
		UnlockEvent(int lockedRoom){
		
		}
	
		/**Changes the status of a specified {@link edu.umw.cpsc240fall2015team3.zork.Room} from "locked" to "unlocked," allowing the player to traverse the {@link edu.umw.cpsc240fall2015team3.zork.Room}.
		*/
    public String execute(){
		return "";
		}

}
