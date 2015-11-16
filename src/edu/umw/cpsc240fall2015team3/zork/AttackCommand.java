/**
@author Ian
*/
package edu.umw.cpsc240fall2015team3.zork;
/**
Searches a hlnkRoom} for a specific enemy hlnkNpc}, and administers damage to that enemy if it is found.
*/
class  AttackCommand extends Command {
    private String enemy;
/**
Constructs an hlnkAttackCommand} object that will be used on a specific enemy hlnkNpc}.

@param Enemy The name of the hlnkNpc} that will be attacked.
*/
    AttackCommand(String Enemy){}
/**
Lowers the health of an enemy hlnkNpc} and returns a message stating how much damage the enemy took from the attack. 
*/
    String execute() {
	return "";
}
}
