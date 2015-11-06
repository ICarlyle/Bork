/**
@author Alec
*/
package edu.umw.cpsc240fall2015team3.zork;

import java.util.ArrayList;
/**
Extends Command class; comes into effect when a player wants to view their inventory.  Will inform the player what they are currently "holding".
*/
class InventoryCommand extends Command {

    InventoryCommand() {
    }
/**
Returns a string that tells the player what they currently have.  If they have nothing, the string says that they are empty-handed.
*/
    public String execute() {
        ArrayList<String> names = GameState.instance().getInventoryNames();
        if (names.size() == 0) {
            return "You are empty-handed.\n";
        }
        String retval = "You are carrying:\n";
        for (String itemName : names) {
            retval += "   A " + itemName + "\n";
        }
        return retval;
    }
}
