
package edu.umw.cpsc240fall2015team3.zork;

class UnknownCommand extends Command {

    private String bogusCommand;

    UnknownCommand(String bogusCommand) {
        this.bogusCommand = bogusCommand;
    }

    String execute() {
        return "I'm not sure what you mean by \"" + bogusCommand + "\".\n";
    }
}
