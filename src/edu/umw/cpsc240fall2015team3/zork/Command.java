package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Performs an action on the {@link edu.umw.cpsc240fall2015team3.zork.GameState}.
*/
abstract class Command {
		
		/**Performs an action that affects the {@link edu.umw.cpsc240fall2015team3.zork.GameState} and returns a message pertaining to that action.
		*/
    abstract String execute();

}
