
package edu.umw.cpsc240fall2015team3.zork;

import java.util.ArrayList;

class TakeCommand extends Command {

    private String itemName;

    TakeCommand(String itemName) {
        this.itemName = itemName;
    }

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

    static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
