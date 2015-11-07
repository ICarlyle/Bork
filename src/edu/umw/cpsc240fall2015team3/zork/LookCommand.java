/**
@author Ian
*/
package edu.umw.cpsc240fall2015team3.zork;
/**
This class is an extension of the Command class and is used for displaying the room text as if the player never entered the current room
*/
class LookCommand extends Command {

    LookCommand() {
    }
/**
Returns the desription of the current room as if it is the first time the player entered the room.
*/
    public String execute() {
        return GameState.instance().getAdventurersCurrentRoom().
            describe(true) + "\n";
    }
}
