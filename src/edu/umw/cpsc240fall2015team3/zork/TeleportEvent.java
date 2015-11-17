package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/

/**
Transports the player to another {@link edu.umw.cpsc240fall2015team3.zork.Room} in the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon}.
*/
class TeleportEvent extends Event{
	
	private int roomNum=0;
	private Boolean numEntered=false;

	/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.TeleportEvent} that uses a random {@link edu.umw.cpsc240fall2015team3.zork.Room} location.
	*/
	TeleportEvent(){
	}

	/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.TeleportEvent} that uses a specific {@link edu.umw.cpsc240fall2015team3.zork.Room} location.

	@param dest The {@link edu.umw.cpsc240fall2015team3.zork.Room} that the player is teleported to.
	*/	
	TeleportEvent(int roomNum){
		this.roomNum = roomNum;
		numEntered = true;
	}
	
	/**Transports the player to specified {@link edu.umw.cpsc240fall2015team3.zork.Room}, or if no {@link edu.umw.cpsc240fall2015team3.zork.Room} is specified, to a random {@link edu.umw.cpsc240fall2015team3.zork.Room} in the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon}.
		*/
	public String execute(){
	Room nextRoom = GameState.instance().getDungeon().getNumberedRoom();
	if(numEntered == true){nextRoom = GameState.instance().getDungeon().getSpecRoom(roomNum);}
	GameState.instance().setAdventurersCurrentRoom(nextRoom);
	GameState.instance().addToTime(1);
	System.out.println("You feel LightHeadhed and a dense grey mist appears\n");
	System.out.println(GameState.instance().getAdventurersCurrentRoom().describe());
	return "\n";
	}

}
