
package edu.umw.cpsc240fall2015team3.zork;

class LookCommand extends Command {

    LookCommand() {
    }

    public String execute() {
        return GameState.instance().getAdventurersCurrentRoom().
            describe(true) + "\n";
    }
}
