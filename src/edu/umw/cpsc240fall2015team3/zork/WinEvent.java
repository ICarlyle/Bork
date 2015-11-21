/**
@author Alec
*/

package edu.umw.cpsc240fall2015team3.zork;
/**
The WinEvent class is an extension of the Event class used for when the player has succesfully beaten the game.  After this command is executed the system will exit.  Will be tied to an item command.
*/
class WinEvent extends Event{

	String itemName;
	String verb;

	WinEvent(String eventAndItem){
		System.out.println("WinEvent: " + eventAndItem);
		/**if (eventAndItem.contains(":")){
			String[] itemAndEvent = eventAndItem.split(":");
			this.verb = eventAndItem[0];
			this.itemName = eventAndName[1];
		}*/
	}
/**
Returns a String informing the player that they have won.
*/
	public String execute(){
	//	Item item = GameState.instance().getItemInVicinityNamed(itemName);
		
	//	String WinMessage = item.getMessageForVerb(verb);
	//	System.out.println(WinMessage);
		System.out.println("Winner winner");
		System.exit(2);
		return "YOU WIN";	
	}
}
