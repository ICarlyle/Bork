package edu.umw.cpsc240fall2015team3.zork;

class StatsCommand extends Command{

	StatsCommand(){
	}

	public String execute(){
		int str = GameState.instance().getStr();
		int def = GameState.instance().getDef();
		return "You have " + str + " strength and " + def + " defense.\n";
	}
	

}
