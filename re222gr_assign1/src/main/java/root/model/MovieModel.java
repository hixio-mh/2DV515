package root.model;

public class MovieModel {
	private double matchScore;
	private String movie;
	
	public MovieModel() {
	}

	public MovieModel(String movie){
		this.movie = movie;
	}

	public void setMatchScore(double matchScore) {
		this.matchScore = matchScore;
	}

	public double getMatchScore() {
		return matchScore;
	}

	public String getMovie() {
		return movie;
	}
}
