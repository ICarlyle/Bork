package edu.umw.cpsc240fall2015team3.zork;

import java.util.ArrayList;
/**
@author Alec
*/
/**
Removes items from a player's inventory.  
*/
class DropCommand extends Command {

    private String itemName;
	
		/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.DropCommand} that uses a specific {@link edu.umw.cpsc240fall2015team3.zork.Item} called by the player.

		@param itemName The name of the {@link edu.umw.cpsc240fall2015team3.zork.Item} to be removed from the player's inventory.
		*/

    DropCommand(String itemName) {
        this.itemName = itemName;
    }
/**
Removes an {@link edu.umw.cpsc240fall2015team3.zork.Item} from the player's inventory and returns a String that says what was removed. If the itemName parameter from the constuctor has the value of "all," then every item in the inventory is removed. If the player doesn't have any items or they don't say what they are dropping, a corresponding String is returned.
*/
    public String execute() {
        if (itemName == null || itemName.trim().length() == 0) {
            return "Drop what?\n";
        }
        if (itemName.equals("all")) {
            ArrayList<String> inventory = (ArrayList<String>) 
                GameState.instance().getInventoryNames();
            if (inventory.size() == 0) {
                return "You're not carrying anything.\n";
            }
            String retval = "";
            for (String name : inventory) {
                retval += dropItemNamed(name);
            }
            return retval;
        } else {
            return dropItemNamed(itemName);
        }
    }
/**
Returns a string that tells the player what item they dropped.`
*/
    private String dropItemNamed(String name) {
        try {
            Item theItem = GameState.instance().getItemFromInventoryNamed(
                name);
            GameState.instance().removeFromInventory(theItem);
            GameState.instance().getAdventurersCurrentRoom().add(theItem);
            return TakeCommand.capitalize(name) + " dropped.\n";
        } catch (Item.NoItemException e) {
            return "You don't have a " + name + ".\n";
        }
    }
}
