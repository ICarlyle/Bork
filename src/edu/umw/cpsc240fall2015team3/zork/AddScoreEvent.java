

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

		@param points The value to change the score by.
		*/
      		AddScoreEvent(int points){
			this.points = points;
		}

		AddScoreEvent(){
			this.points = 0;
		}
/**
Raises the player's score by the specified amount and returns a String message that says the score has been changed. If the amount passed to the method is negative, this will have the effect of lowering the player's score.
*/	
	String execute(){
		GameState.instance().addToAdventurersScoreBy(points);
		return "You earned " + points + " points!\n";	
	}

}
