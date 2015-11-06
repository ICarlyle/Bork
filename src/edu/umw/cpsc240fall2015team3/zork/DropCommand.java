
package edu.umw.cpsc240fall2015team3.zork;

import java.util.ArrayList;
/**
@author Alec
*/
/**
Extends the command class; comes into effect when a player or potentially an Npc wishes to drop a certain (or all) item(s).  
*/
class DropCommand extends Command {

    private String itemName;

    DropCommand(String itemName) {
        this.itemName = itemName;
    }
/**
Returns a string that tells the player what they dropped.  If the player doesn't have any items or they don't say what they are dropping, a corresponding String is returned.
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
@throws NoItemException if the player doesn't have the item they are trying to drop.
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
