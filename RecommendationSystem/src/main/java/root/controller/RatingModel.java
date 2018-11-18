package root.controller;

public class RatingModel {
	private String movieName;
	private double movieScore;

	public RatingModel() {
	}

	public RatingModel(String movieName, double movieScore) {
		this.movieName = movieName;
		this.movieScore = movieScore;
	}

	public double getMovieScore() {
		return movieScore;
	}

	public String getMovieName() {
		return movieName;
	}
}
