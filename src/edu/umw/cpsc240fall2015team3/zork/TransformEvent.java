
package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Changes an {@link edu.umw.cpsc240fall2015team3.zork.Item} into a different {@link edu.umw.cpsc240fall2015team3.zork.Item}.
*/
class TransformEvent extends Event{

	private String oldItemName;
	private String newItemName;

	/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.TransformEvent} that uses two specific {@link edu.umw.cpsc240fall2015team3.zork.Item}s.

	@param oldItemName The {@link edu.umw.cpsc240fall2015team3.zork.Item} that is being transformed.
	@param newItemName The {@link edu.umw.cpsc240fall2015team3.zork.Item} that the old {@link edu.umw.cpsc240fall2015team3.zork.Item} is being transformed into.
	*/	
	TransformEvent(String oldItemName, String newItemName){
		this.oldItemName = oldItemName;
		this.newItemName = newItemName;	
	}
	
	/**Changes one {@link edu.umw.cpsc240fall2015team3.zork.Item} into a different {@link edu.umw.cpsc240fall2015team3.zork.Item}.
	*/

/**	public String execute(){
		Item oldItem = GameState.instance().getItemInVicinityNamed(oldItemName);
		Item newItem = GameState.instance().getDungeon.getItem(newItemName);
			
		//oldItem.setAliases(newItem.getAliases());
		//oldItem.setEvents(newItem.getEvents());
		//oldItem.setMessages(newItem.getMessages());
		//oldItem.setPrimaryName(newItem.getPrimaryName());
		//oldItem.setWeight(newItem.getWeight());
		//return "The " + oldItemName + "has become " + newItemName + ".\n";
	}*/
	
	//Need to fix getItem in Dungeon
public String execute(){
    try{
System.out.println("'"+oldItemName+"'"+newItemName+"'");
    Item oldItem = GameState.instance().getItemInVicinityNamed(oldItemName);
    Item newItem = GameState.instance().getDungeon().getItem(newItemName);
    System.out.print(CommandFactory.instance().parse("take "+oldItemName).execute());
    GameState.instance().removeFromInventory(oldItem);
    //GameState.instance().removeFromCurrentRoom(oldItem);
    GameState.instance().addToInventory(newItem);
    return "";
    }catch(Item.NoItemException e) {
	return "No item found, "+oldItemName+" cannot convert to "+newItemName+"\n";
    }
    }
}
