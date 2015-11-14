
package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Changes the player's health by a specified value.
*/
class LowerHealthEvent extends Event{

	private int damage;
		/**Constructsa a {@link edu.umw.cpsc240fall2015team3.zork.LowerHealthEvent} object that uses a certain damage value.

		@param damage The amount that the player's health should be lowered by.
		*/	
		LowerHealthEvent(int damage){
			this.damage = damage;
		}
	
		/**Lowers the player's health by a specified amount. This simulatneously acts as a way to increase the player's health by passing in a positive value for the damage.
		*/
    public String execute(){
	GameState.instance().subtractAdventurersHealthBy(damage);
	//System.out.println("damage: " + damage + "\n");
	return "ouch\n";	
	}

}
