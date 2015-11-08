
package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
Changes an {@link edu.umw.cpsc240fall2015team3.zork.Item} into a different {@link edu.umw.cpsc240fall2015team3.zork.Item}.
*/
class TransformEvent extends Event{

		private String oldItemName, newItemName;

		/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.TransformEvent} that uses two specific {@link edu.umw.cpsc240fall2015team3.zork.Item}s.

		@param oldItemName The {@link edu.umw.cpsc240fall2015team3.zork.Item} that is being transformed.
		@param newItemName The {@link edu.umw.cpsc240fall2015team3.zork.Item} that the old {@link edu.umw.cpsc240fall2015team3.zork.Item} is being transformed into.
		*/	
		TransformEvent(String oldItemName, String newItemName){
		
		}
	
		/**Changes one {@link edu.umw.cpsc240fall2015team3.zork.Item} into a different {@link edu.umw.cpsc240fall2015team3.zork.Item}.
		*/
    public String execute(){
		return "";
		}

}
