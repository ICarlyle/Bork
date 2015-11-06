/**
@author Ian
*/
package edu.umw.cpsc240fall2015team3.zork;
/**
This class will store the command the player enteres and will tell the player the command is unknown when the execute method is called.
*/
class UnknownCommand extends Command {

    private String bogusCommand;
/**
Stores the command the player entered
*/
    UnknownCommand(String bogusCommand) {
        this.bogusCommand = bogusCommand;
    }
/**
Returns a String that states the command the player entered is not understood
*/
    String execute() {
        return "I'm not sure what you mean by \"" + bogusCommand + "\".\n";
    }
}
