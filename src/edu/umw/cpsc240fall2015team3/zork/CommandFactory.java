
package edu.umw.cpsc240fall2015team3.zork;

import java.util.List;
import java.util.Arrays;

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

    public Command parse(String command) {
        String parts[] = command.split(" ");
        String verb = parts[0];
        String noun = parts.length >= 2 ? parts[1] : "";
        if (verb.equals("save")) {
            return new SaveCommand(noun);
        }
        if (verb.equals("take")) {
            return new TakeCommand(pasteSecondAndBeyond(parts));
        }
        if (verb.equals("drop")) {
            return new DropCommand(pasteSecondAndBeyond(parts));
        }
        if (verb.equals("look")) {
            return new LookCommand();
        }
        if (verb.equals("i") || verb.equals("inventory")) {
            return new InventoryCommand();
        }
        if (MOVEMENT_COMMANDS.contains(verb)) {
            return new MovementCommand(verb);
        }
        if (parts.length >= 2) {
            return new ItemSpecificCommand(command);
        }
        return new UnknownCommand(command);
    }

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
