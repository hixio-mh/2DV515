package root;

import root.methods.Euclidean;
import root.methods.WeightedScores;
import root.model.MatchModel;
import root.model.MovieModel;
import root.model.UserModel;
import root.presentation.MoviePresentation;
import root.presentation.UserPresentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RecommendationMain {
	@Autowired
	private ReadFile readFile;
	@Autowired
	private Euclidean euclidean;
	@Autowired
	private WeightedScores ws;

	public static void main(String[] args){
		SpringApplication.run(RecommendationMain.class, args);
	}

	@GetMapping("/euclidean/{userId}")
	public UserPresentation getEuclidean(@PathVariable Long userId){
		UserModel userModel = readFile.getUserId(userId);
		List<MatchModel> similarity = euclidean.euclideanSimilarity(userModel);
		UserPresentation euclideanDistance = new UserPresentation(userModel, similarity);
		return euclideanDistance;
	}

	@GetMapping("/ws/{userId}")
	public MoviePresentation getEuclideanMovieMatches(@PathVariable Long userId){
		UserModel userModel = readFile.getUserId(userId);
		List<MovieModel> recommendations = ws.getRecommendations(userModel, euclidean.euclideanSimilarity(userModel));
		MoviePresentation movies = new MoviePresentation(userModel, recommendations);
		return movies; 
	}
}
