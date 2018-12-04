package root.model;

public class RatingModel {
	private String movie;
	private double score;
	private UserModel user;
	
	public RatingModel() {
	}

	public RatingModel(String movie, double score, UserModel user){
		this.movie = movie;
		this.score = score;
		this.user = user;
	}

	public double getScore() {
		return score;
	}

	public String getMovie() {
		return movie;
	}

	public UserModel getUser() {
		return user;
	}
}
