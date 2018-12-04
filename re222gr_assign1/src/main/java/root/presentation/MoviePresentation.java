package root.presentation;

import java.util.List;

import root.model.MovieModel;
import root.model.UserModel;

public class MoviePresentation {
	private String summary;
	private List<MovieModel> movieMatches;
	
	public MoviePresentation() {
		
	}

	public MoviePresentation(UserModel user, List<MovieModel> movieMatches){
		this.summary = "Movie recommendations for the user " + user.getUsername();
		this.movieMatches = movieMatches;
	}

	public String getSummary() {
		return summary;
	}

	public List<MovieModel> getMovieMatches() {
		return movieMatches;
	}
}
