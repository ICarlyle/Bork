/**
@author Alec
*/
package edu.umw.cpsc240fall2015team3.zork;

import java.util.ArrayList;

class TakeCommand extends Command {

    private String itemName;

    TakeCommand(String itemName) {
        this.itemName = itemName;
    }
/**
Returns a String that corresponds to what the player is trying to take.  If there is no item, the string returns a corresponding message.
*/
    public String execute() {
        if (itemName == null || itemName.trim().length() == 0) {
            return "Take what?\n";
        }
        if (itemName.equals("all")) {
            ArrayList<Item> roomContents = new ArrayList<Item>(
                GameState.instance().getAdventurersCurrentRoom().
                getContents());
            if (roomContents.size() == 0) {
                return "There's nothing here to take.\n";
            }
            String retval = "";
            for (Item item : roomContents) {
                retval += tryToTakeItemNamed(item.getPrimaryName());
            }
            return retval;
        } else {
            return tryToTakeItemNamed(itemName);
        }
    }
/**
Returns a string that tells the player 1) if they can take the item in the vicinity and 2) what the item is that they are taking.
@param name String that is the item the player is trying to take.
@throws NoItemException if theere is no item that the player is trying to take.
*/
    private String tryToTakeItemNamed(String name) {
        try {
            Room currentRoom = 
                GameState.instance().getAdventurersCurrentRoom();
            Item theItem = currentRoom.getItemNamed(name);

            if (GameState.instance().weightCarried() + theItem.getWeight()
                > GameState.MAX_CARRY_WEIGHT) {
                return "Your load is too heavy.\n";
            }

            GameState.instance().addToInventory(theItem);
            currentRoom.remove(theItem);
            return capitalize(name) + " taken.\n";

        } catch (Item.NoItemException e) {
            return "There's no " + name + " here.\n";
        }
    }
/**
Returns a capitalzied string.
@param s  the string to have the first letter capitalized
*/
    static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
