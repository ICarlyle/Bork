

package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Changes the players score by the passed value.
*/
class AddScoreEvent extends Event {

	private int points;
		
		/**
		Constructs a {@link edu.umw.cpsc240fall2015team3.zork.AddScoreEvent} object that uses the passed score value.
		*/
      		AddScoreEvent(int points){
			this.points = points;
		}

		AddScoreEvent(){
			this.points = 0;
		}
/**
Raises the player's score by the specified amount.
@return a corresponding String message
*/	
	String execute(){
		GameState.instance().addToAdventurersScoreBy(points);
		return "You earned " + points + " points!\n";	
	}

}
