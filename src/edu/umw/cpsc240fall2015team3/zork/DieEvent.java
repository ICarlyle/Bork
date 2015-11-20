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
		Random rand = new Random();
		int randNum = rand.nextInt(2)+1;
		//System.out.println("Death message "+randNum);
		System.out.println("\n");
		//ENTER ENFGAME MESSAGE HERE
		System.out.print(CommandFactory.instance().parse("score").execute());
		if(randNum==1){System.out.print("Your body falls apart");}
		if(randNum==2){System.out.print("No more 1-ups");}
		if(randNum==3){System.out.print("LAAAG");}
 		System.out.println(", You Died.");
		System.exit(2);
		return "";
		
	}
}
