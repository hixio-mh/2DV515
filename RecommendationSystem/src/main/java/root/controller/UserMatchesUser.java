package root.controller;

public class UserMatchesUser {
	private String name;
	private double matchScore;
	
	public UserMatchesUser() {
	}

	public UserMatchesUser(String name, double matchScore){
		this.matchScore = matchScore;
		this.name = name;
	}

	public double getMatchScore() {
		return matchScore;
	}

	public String getName() {
		return name;
	}
}
