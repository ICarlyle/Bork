/**
@author Ian
*/
package edu.umw.cpsc240fall2015team3.zork;
/**
This class is an extension of the Command class and is used for transfering the player between rooms.
*/
class MovementCommand extends Command {

    private String dir;             
/**
Stores the direction the player enters
*/
    MovementCommand(String dir) {
        this.dir = dir;
    }
/**
Finds a room from an exit with the direction the player entered, sets that room as current, and return a string stating if the the player can or cannot go to that exit. 
If the movement command does not go to a room, returns a string desribing how the player can't go that direction
*/
    public String execute() {
        Room currentRoom = GameState.instance().getAdventurersCurrentRoom();
        Room nextRoom = currentRoom.leaveBy(dir);
        if (nextRoom != null) {  // could try/catch here.
            GameState.instance().setAdventurersCurrentRoom(nextRoom);
            return "\n" + nextRoom.describe() + "\n";
        } else {
            return "You can't go " + dir + ".\n";
        }
    }
}
