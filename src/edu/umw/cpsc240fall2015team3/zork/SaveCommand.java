
package edu.umw.cpsc240fall2015team3.zork;

/**
@author Robert Jamal Washington
*/
/**
A {@link edu.umw.cpsc240fall2015team3.zork.Command} that saves the {@link edu.umw.cpsc240fall2015team3.zork.GameState} to a .sav file.
*/
class SaveCommand extends Command {

    private static String DEFAULT_SAVE_FILENAME = "zork";

    private String saveFilename;

		/**Returns a {@link edu.umw.cpsc240fall2015team3.zork.SaveCommand} and sets the .sav filename to a name chosen by the player, or to a default filename if no name is given.

		@param saveFilename The filename to be given to the .sav file.
		*/
    SaveCommand(String saveFilename) {
        if (saveFilename == null || saveFilename.length() == 0) {
            this.saveFilename = DEFAULT_SAVE_FILENAME;
        } else {
            this.saveFilename = saveFilename;
        }
    }
		/**Generates a .sav file with the current {@link edu.umw.cpsc240fall2015team3.zork.GameState} and returns a message indicating that the save was completed. If the save failed, return a message indicating that the save could not be completed.
		*/
    public String execute() {
        try {
            GameState.instance().store(saveFilename);
            return "Data saved to " + saveFilename +
                GameState.SAVE_FILE_EXTENSION + ".\n";
        } catch (Exception e) {
            System.err.println("Couldn't save!");
            e.printStackTrace();
            return "";
        }
    }
}
