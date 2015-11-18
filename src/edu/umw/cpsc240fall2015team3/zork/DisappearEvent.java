

package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Removes a certain item from the GameState and Dungeon.
*/
class DisappearEvent extends Event{

	private String itemName;
		/**
		Constructs a {@link edu.umw.cpsc240fall2015team3.zork.DisappearEvent} object that will remove a specified item from {@link edu.umw.cpsc240fall2015team3.zork.GameState} and the {@link edu.umw.cpsc240fall2015team3.zork.Dungeon} object.
		*/
		DisappearEvent(String itemName){
			this.itemName = itemName;
		}
/**
Completely removes the passed item from the current Dungeon and GameState.
*/
    String execute(){
	try{
	    Item theItem = GameState.instance().getItemInVicinityNamed(itemName);
	    //GameState.instance().removeItemFromDungeon(theItem);
	    GameState.instance().removeFromCurrentRoom(theItem);
	    GameState.instance().removeFromInventory(theItem);
	    return itemName+" poof!";
	}
	catch(Item.NoItemException e) {
	    return "You don't have a "+itemName+".\n";
	}
    }
}
