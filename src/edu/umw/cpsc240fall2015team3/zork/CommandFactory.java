/**
@author Alec
*/
package edu.umw.cpsc240fall2015team3.zork;

import java.util.List;
import java.util.Arrays;
/**
The Singleton CommandFactory class instantiates different Command objects based on the input read in in {@link edu.umw.cpsc240fall2015team3.zork.Interpreter#promptUser}.  
*/
public class CommandFactory {

    private static CommandFactory theInstance;
    public static List<String> MOVEMENT_COMMANDS = 
        Arrays.asList("n","w","e","s","u","d" );

    public static synchronized CommandFactory instance() {
        if (theInstance == null) {
            theInstance = new CommandFactory();
        }
        return theInstance;
    }

    private CommandFactory() {
    }
/**
Returns a Command derived from the string that a user entered in {@link edu.umw.cpsc240fall2015team3.zork.Interpreter#promptUser}.  The command or the actual item may be more than one word long, and tied with either an item or stand alone.

E.G.: 

Take MagicWand
Score
u [{@link MovementCommand}

If a command is entered incorrectly or String command isn't actually a command, an UnknownCommand Command is returned. 
@param command String that is the (multiple word) command read in {@link edu.umw.cpsc240fall2015team3.zork.Interpreter#promptUser}
@return Command object that corresponds to the input in {@link edu.umw.cpsc240fall2015team3.zork.Interpreter#promptUser} 
*/
    public Command parse(String command) {
        String parts[] = command.split(" ");
        String verb = parts[0];
        String noun = parts.length >= 2 ? parts[1] : "";
	int numberVal = 0;
	if(noun.contains("1")||noun.contains("2")||noun.contains("3")||noun.contains("4")||noun.contains("5")||noun.contains("6")||noun.contains("7")||noun.contains("8")||noun.contains("9")||noun.contains("0")){numberVal = Integer.parseInt(noun);}

        if (verb.equals("save")) {
            return new SaveCommand(noun);
        }
	if (verb.equals("score")) {
	    return new ShowScoreCommand();
	}
	if (verb.equals("health")) {
	    return new ShowHealthCommand();
	}
        if (verb.equals("take")) {
            return new TakeCommand(pasteSecondAndBeyond(parts));
        }
        if (verb.equals("drop")) {
            return new DropCommand(pasteSecondAndBeyond(parts));
        }
        if (verb.equals("look")||verb.equals("l")) {
            return new LookCommand();
        }
        if (verb.equals("i") || verb.equals("inventory")) {
            return new InventoryCommand();
        }
	if (verb.equals("attack")){
	    return new AttackCommand(pasteSecondAndBeyond(parts));
	}
	if (verb.equals("stats")){
		return new StatsCommand();
	}
        if (MOVEMENT_COMMANDS.contains(verb)) {
            return new MovementCommand(verb);
        }
	if (verb.equals("wait")){
	    return new WaitCommand(numberVal);
	}
        if (parts.length >= 2) {
            return new ItemSpecificCommand(command);
        }
        return new UnknownCommand(command);
    }
/**
Returns a String for commands that interact with multi-word items
@param parts Array that contains the input entered in 
*/
    private String pasteSecondAndBeyond(String[] parts) {
        if (parts.length < 2) {
            return "";
        }
        String string = parts[1];
        for (int i=2; i<parts.length; i++) {
            string += " " + parts[i];
        }
        return string;
    }
}
