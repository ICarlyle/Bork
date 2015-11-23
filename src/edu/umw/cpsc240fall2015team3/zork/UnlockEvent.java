
package edu.umw.cpsc240fall2015team3.zork;

import java.util.ArrayList;

/**
@author Robert Jamal Washington
*/
/**
Unlocks a locked {@link edu.umw.cpsc240fall2015team3.zork.Room} in the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon}.
*/
class UnlockEvent extends Event{
		
		private String lockedRoomName;

		UnlockEvent(String lockedRoomName){
			this.lockedRoomName = lockedRoomName;
		}

		/**Returns an {@link edu.umw.cpsc240fall2015team3.zork.UnlockEvent} that uses a specific {@link edu.umw.cpsc240fall2015team3.zork.Room} location.

		@param lockedRoom The {@link edu.umw.cpsc240fall2015team3.zork.Room} that is to be unlocked.
		*/	
		public String execute(){
			String returnMsg = "";
			Room currRoom = GameState.instance().getAdventurersCurrentRoom();
			ArrayList<Exit> exits = currRoom.getExits();
			for (Exit exit : exits){
				if (exit.isLocked()){
					Room dest = exit.getDest();
					if (dest.getTitle().equals(lockedRoomName)){
						exit.unlock();
						returnMsg = "Should be the actual unlock message here!";	
					}
				}
			}
			return returnMsg;
		}
	
		/**Changes the status of a specified {@link edu.umw.cpsc240fall2015team3.zork.Room} from "locked" to "unlocked," allowing the player to traverse the {@link edu.umw.cpsc240fall2015team3.zork.Room}.
		*/

}
