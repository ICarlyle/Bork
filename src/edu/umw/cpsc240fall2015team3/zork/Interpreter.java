
package edu.umw.cpsc240fall2015team3.zork;

import java.util.Scanner;
/**
@author Alec
*/
/**
The interpreter class reads in a .zork/.sav file to create/restore a dungeon and continuously prompts the user for input commands in order to play through the dungeon.
*/
public class Interpreter {

    private static GameState state; // not strictly necessary; GameState is 
                                    // singleton

    public static String USAGE_MSG = 
        "Usage: Interpreter borkFile.bork|saveFile.sav.";
/**
Instantiates a new Dungeon object based on a .zork or a .sav file and continuously prompts the player for input for commands to play through the dungeon.  If there is a problem with the file format, a usage error is printed and the system exits.  If a .bork file is supplied, then a brand new dungeon is loaded based on that .bork file.  If a .sav file is supplied, then a saved dungeon is restored complete with appropriate item and enemy placements.  The old player stats as well as room locked/unlocked information is read in through the .sav file in order to restore the previously played dungeon.   
*/
    public static void main(String args[]) {

        if (args.length < 1) {
            System.err.println(USAGE_MSG);
            System.exit(1);
        }

        String command;
        Scanner commandLine = new Scanner(System.in);

        try {
            state = GameState.instance();
            if (args[0].endsWith(".bork")) {
                state.initialize(new Dungeon(args[0]));
                System.out.println("\n\n\n\n\nWelcome to " + 
                    state.getDungeon().getName() + "!");
            } else if (args[0].endsWith(".sav")) {
                state.restore(args[0]);
                System.out.println("\nWelcome back to " + 
                    state.getDungeon().getName() + "!");
            } else {
                System.err.println(USAGE_MSG);
                System.exit(2);
            }

            System.out.print("\n" + 
                state.getAdventurersCurrentRoom().describe() + "\n");

            command = promptUser(commandLine);

            while (!command.equals("q")) {
		if(command.equals("EVENT")){
		    System.out.println("Welcome, hacker. These hacks may be gameBreaking, be warned. Enter the Event classname CAPS matters.");
		    command = promptUser(commandLine);
		    System.out.print(EventFactory.instance().parse(command).execute());
		    
		} else {
		//System.out.print("\n\n\n"); 		//Spacer that makes it look cleaner
                System.out.print(
                    CommandFactory.instance().parse(command).execute());

                command = promptUser(commandLine);
		}
	    }

            System.out.println("Bye!");

        } catch(Exception e) { 
            e.printStackTrace(); 
        }
    }
/**
@return String input of the user to be converted into a command
*/
    private static String promptUser(Scanner commandLine) {

        System.out.print("> ");
        return commandLine.nextLine();
    }

}
