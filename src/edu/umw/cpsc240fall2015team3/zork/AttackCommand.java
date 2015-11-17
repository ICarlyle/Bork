/**
@author Ian
*/
package edu.umw.cpsc240fall2015team3.zork;
/**
Searches a {@link edu.umw.cpsc240fall2015team3.zork.Room} for a specific enemy {@link edu.umw.cpsc240fall2015team3.zork.Npc}, and administers damage to that enemy if it is found.
*/
class  AttackCommand extends Command {
    private String enemy;
/**
Constructs an {@link edu.umw.cpsc240fall2015team3.zork.AttackCommand} object that will be used on a specific enemy {@link edu.umw.cpsc240fall2015team3.zork.Npc}.

@param Enemy The name of the {@link edu.umw.cpsc240fall2015team3.zork.Npc} that will be attacked.
*/
    AttackCommand(String Enemy){}
/**
Lowers the health of an enemy {@link edu.umw.cpsc240fall2015team3.zork.Npc} and returns a message stating how much damage the enemy took from the attack. 
*/
    String execute() {
	return "";
}
}
