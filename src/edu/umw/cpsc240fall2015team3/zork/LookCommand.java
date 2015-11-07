/**
@author Ian
*/
package edu.umw.cpsc240fall2015team3.zork;
/**
When executed, this command displays the room text as if the player never entered the current room
*/
class LookCommand extends Command {

    LookCommand() {
    }
/**
Desribes the current room as if it is the first time the player entered the room.
*/
    public String execute() {
        return GameState.instance().getAdventurersCurrentRoom().
            describe(true) + "\n";
    }
}
