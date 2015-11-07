
package edu.umw.cpsc240fall2015team3.zork;

/**
@author Alec
*/
/**
Performs a reactive action on the {@link edu.umw.cpsc240fall2015team3.zork.GameState}.
*/
abstract class Event{
/**
Performs an action that affects the {@link edu.umw.cpsc240fall2015team3.zork.GameState} and returns a message about this action.
*/
	abstract String execute();

}
