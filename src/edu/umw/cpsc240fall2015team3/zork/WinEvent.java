/**
@author Alec
*/

package edu.umw.cpsc240fall2015team3.zork;
/**
The WinEvent class is an extension of the Event class used for when the player has succesfully beaten the game.  After this command is executed the system will exit.  Will be tied to an item command.
*/
class WinEvent extends Event{
	WinEvent(){
	}
/**
Returns a String informing the player that they have won.
*/
	public String execute(){
		return "";	
	}
}
