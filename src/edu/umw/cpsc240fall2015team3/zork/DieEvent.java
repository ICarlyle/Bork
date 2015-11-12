/**
@author Alec
*/

package edu.umw.cpsc240fall2015team3.zork;
import java.util.Random;
/**
The DieEvent class is an extension of the Event class used for when the player is killed off from either an item Command or by having their health drop to 0 or less. 
*/
class DieEvent extends Event{
	DieEvent(){
	}
/**
Returns a String informing the player that they are dead.
*/
	public String execute(){
		/**static Random rand = new Random();
		randNum = rand.nextInt(2);
		System.out.println("Death message "+randNum);
		if(randNum.equals(0)){return "Your body falls apart";}
		if(randNum.equals(1)){return "No more 1-ups";}
		if(randNum.equals(2)){return "LAAAG";}
*/
		return "YOU DIED";
		
	}
}
