/**
@author Ian
*/
package edu.umw.cpsc240fall2015team3.zork;
/**
This class is an extension of the Command class and is used for passing time.
*/
class WaitCommand extends Command {

int wait;
/**
Stores how much time should pass
*/
    WaitCommand(int wait){}
/**
Updates gametime by stored amount and returns a string that states the time entered has passed
*/
    public String execute(){
	return "";
}
}
