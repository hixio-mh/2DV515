package root.methods;

import static com.google.common.collect.MultimapBuilder.hashKeys;
import static com.google.common.collect.Multimaps.toMultimap;
import static java.lang.Double.compare;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import root.ReadFile;
import root.model.MatchModel;
import root.model.MovieModel;
import root.model.RatingModel;
import root.model.UserModel;

import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeightedScores {
	double totRScore = 0;
	double totMScore = 0;
	@Autowired
	private ReadFile readFile;
	
	private boolean getRating(UserModel userModel, RatingModel ratingModel) {
		boolean ratings = userModel.getRatings()
		.stream()
		.noneMatch(user -> user.getMovie()
		.equals(ratingModel.getMovie()));
		
		return ratings; 		
}

	public List<MovieModel> getRecommendations(UserModel userModel, List<MatchModel> matchModel) {
		List<MovieModel> matches = new ArrayList<>();
		List<UserModel> users = readFile.getAllUsersId();
		Map<UserModel, MatchModel> userToUserMatch = matchModel.stream()
				.collect(toMap(MatchModel::getUser, identity()));

		Multimap<String, RatingModel> weightedScore = users.stream()
				.flatMap(otherUser -> otherUser.getRatings().stream())
				.filter(rating -> getRating(userModel, rating))
				.limit(3)
				.collect(toMultimap(RatingModel::getMovie, identity(), hashKeys()
				.arrayListValues()::build));

		for(String movie : weightedScore.keySet()){
			Collection<RatingModel> movieRatings =  weightedScore.get(movie);
			MovieModel movieModel = new MovieModel(movie);

			for(RatingModel ratingModel : movieRatings){
				MatchModel match = userToUserMatch.get(ratingModel.getUser());
				double ratingScore = ratingModel.getScore();
				double matchScore = match.getMatchScore();
				
				totRScore = totRScore + ratingScore * matchScore;
				totMScore = totMScore + match.getMatchScore();
			}
			movieModel.setMatchScore(totRScore/totMScore);
			matches.add(movieModel);
		}

		List<MovieModel> recommendations = matches.stream()
				.sorted((matchA, matchB) -> compare(matchB.getMatchScore(), matchA.getMatchScore()))
				.collect(toList());
		return recommendations; 
	}
}
