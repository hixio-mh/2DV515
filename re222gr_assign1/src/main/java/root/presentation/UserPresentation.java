package root.presentation;

import static java.util.stream.Collectors.toList;

import java.util.List;

import root.model.MatchModel;
import root.model.UserModel;

public class UserPresentation {
	private String summary;
	private List<getMatches> matches;
	
	public UserPresentation() {
		
	}

	public UserPresentation(UserModel comparedUser, List<MatchModel> matches){
		summary = "User " + comparedUser.getUsername() + " with matching scores";
		this.matches = matches
				.stream()
				.map(getMatches::new)
				.collect(toList());
	}

	public String getSummary() {
		return summary;
	}

	public List<getMatches> getMatches() {
		return matches;
	}

	private class getMatches{
		private String name;
		private double score;

		getMatches(MatchModel userMatch){
			this.name = userMatch.getUserName();
			this.score = userMatch.getMatchScore();
		}

		public String getName() {
			return name;
		}

		public double getScore() {
			return score;
		}

	}
}
