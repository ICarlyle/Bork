
package edu.umw.cpsc240fall2015team3.zork;

class ItemSpecificCommand extends Command {

    private String command;
                        

    ItemSpecificCommand(String command) {
        this.command = command;
    }

    public String execute() {
        
        String[] words = command.split(" ");

        // Consider every possible split point in the multi-word command. If
        // any split point corresponds to a verb/noun combo that makes sense,
        // assume that's what the player meant.
        for (int indexOfLastVerb=0; indexOfLastVerb<words.length-1;
            indexOfLastVerb++) {

            String verb = words[0];
            for (int i=1; i<=indexOfLastVerb; i++) {
                verb += " " + words[i];
            }
            String noun = words[indexOfLastVerb+1];
            for (int i=indexOfLastVerb+2; i<words.length; i++) {
                noun += " " + words[i];
            }

            Item itemReferredTo = null;
            try {
                itemReferredTo = 
                    GameState.instance().getItemInVicinityNamed(noun);
            } catch (Item.NoItemException e) {
                continue;  // Just try the next split point.
            }

            String msg = itemReferredTo.getMessageForVerb(verb);
            return (msg == null ? 
                "Sorry, you can't " + verb + " the " + noun + "." : msg) +
                "\n";
        }

        return "Not sure what you mean?\n";
    }
}
