/**
@author Ian
*/
package edu.umw.cpsc240fall2015team3.zork;
/**
This class will search a room for an enemy named the passed argument then call methods to simulate fighting.
*/
class  AttackCommand extends Command {
    private String enemy;
/**
Stores the name of enemy to attack
*/
    AttackCommand(String Enemy){}
/**
Returns a string that states how much damage was done
*/
    String execute() {
	return "";
}
}
