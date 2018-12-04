package root.model;

public class MatchModel {
	private UserModel user;
	private double matchScore;
	
	public MatchModel(){
	}

	public MatchModel(UserModel user, double matchScore){
		this.user = user;
		this.matchScore = matchScore;
	}

	public double getMatchScore() {
		return matchScore;
	}

	public String getUserName() {
		return user.getUsername();
	}

	public UserModel getUser() {
		return user;
	}
}
