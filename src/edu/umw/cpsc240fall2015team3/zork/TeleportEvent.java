
package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Transports the player to another {@link edu.umw.cpsc240fall2015team3.zork.Room} in the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon}.
*/
class TeleportEvent extends Event{
		
		private Room dest;

		/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.TeleportEvent} that uses a random {@link edu.umw.cpsc240fall2015team3.zork.Room} location.
		*/
		TeleportEvent(){

		}

		/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.TeleportEvent} that uses a specific {@link edu.umw.cpsc240fall2015team3.zork.Room} location.

		@param dest The {@link edu.umw.cpsc240fall2015team3.zork.Room} that the player is teleported to.
		*/	
		TeleportEvent(int dest){
		
		}
	
		/**Transports the player to specified {@link edu.umw.cpsc240fall2015team3.zork.Room}, or if no {@link edu.umw.cpsc240fall2015team3.zork.Room} is specified, to a random {@link edu.umw.cpsc240fall2015team3.zork.Room} in the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon}.
		*/
    public String execute(){

		}

}
