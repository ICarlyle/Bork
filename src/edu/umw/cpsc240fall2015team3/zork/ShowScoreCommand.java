package edu.umw.cpsc240fall2015team3.zork;

class ShowScoreCommand extends Command{

	ShowScoreCommand(){}

	public String execute(){
		int score = GameState.instance().getAdventurersScore();
		String rank = "";
		if (score <= 0){rank = "Hungry Hungry Hoarder";}
		if (score > 0 && score <= 10){rank = "Howling Scrounger";}
		if (score > 10 $$ score <= 20){rank = "Sluggish Stasher";}
		if (score > 20 && score <= 30){rank = "Circumstantial Conglomerator";}
		if (score > 30 && score <= 40){rank = "Bountiful Baron";}
		if (score > 40){rank = "The Undying";}
		return "You have accumulated " + score + " points.\nThis gives you a rank of " + rank + ".\n";
	}

}
