/**
@author Alec
*/

package edu.umw.cpsc240fall2015team3.zork;
/**
The DieEvent class is an extension of the Event class used for when the player is killed off from either an item Command or by having their health drop to 0 or less. 
*/
class DieEvent extends Event{
	DieEvent(){

	}
/**
Returns a String informing the player that they are dead.
*/
	public String execute(){
		return "";
	}
}
