
package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/

import java.util.Random;

/**
Transports the player to another {@link edu.umw.cpsc240fall2015team3.zork.Room} in the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon}.
*/
class TeleportEvent extends Event{
		
		private Room dest;

		/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.TeleportEvent} that uses a random {@link edu.umw.cpsc240fall2015team3.zork.Room} location.
		*/
		TeleportEvent(){
			Random rand;
			ArrayList<Room>	rooms = GameState.getDungeon().getAllRooms();
			int randRoom = rand.nextInt((rooms.size() + 1));

			dest = rooms.get(randRoom);
		}

		/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.TeleportEvent} that uses a specific {@link edu.umw.cpsc240fall2015team3.zork.Room} location.

		@param dest The {@link edu.umw.cpsc240fall2015team3.zork.Room} that the player is teleported to.
		*/	
		TeleportEvent(int dest){
			this.dest = dest;
		}
	
		/**Transports the player to specified {@link edu.umw.cpsc240fall2015team3.zork.Room}, or if no {@link edu.umw.cpsc240fall2015team3.zork.Room} is specified, to a random {@link edu.umw.cpsc240fall2015team3.zork.Room} in the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon}.
		*/
    public String execute(){
	    // GameState.instance().addToTime(1);
      GameState.instance().setAdventurersCurrentRoom(dest);
			return "You were whisked away to " + dest.getTitle() + "\n.";
		}

}
