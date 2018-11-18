package root.controller;

import java.util.List;

public class MatchesToUser {
	private String summary;
	private List<UserMatchesUser> matches;
	
	public MatchesToUser(){
	}

	public MatchesToUser(UserModel chosenUser, List<UserMatchesUser> matches){
		String user = chosenUser.getUsername().toUpperCase();
		this.matches = matches;
		summary = "User: " + user + " with "  + "matching scores";
	}

	public String getSummary() {
		return summary;
	}

	public List<UserMatchesUser> getMatches() {
		return matches;
	}
}
