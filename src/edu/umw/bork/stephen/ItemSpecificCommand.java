
package edu.umw.bork.stephen;

class ItemSpecificCommand extends Command {

    private String verb;
    private String noun;
                        

    ItemSpecificCommand(String verb, String noun) {
        this.verb = verb;
        this.noun = noun;
    }

    public String execute() {
        
        Item itemReferredTo = null;
        try {
            itemReferredTo = Adventurer.instance().getItemNamed(noun);
        } catch (Adventurer.UnknownItemException e) {
            return "There's no " + noun + " here.";
        }
        
        return "Congratulations, you " + verb + "ed the " + noun + ".";
    }
}