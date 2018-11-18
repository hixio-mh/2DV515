package root;

 
import root.controller.MatchesToUser;
import root.euclidean.EuclideanDistance;

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
	private EuclideanDistance euclidean;

	public static void main(String[] args){
		SpringApplication.run(RecommendationMain.class, args);
	}

	@GetMapping("/euclidean/{userId}")
	public MatchesToUser getEuclidean(@PathVariable Long userId){
		MatchesToUser similarity =  euclidean.euclideanSimilarity(userId);
		return similarity;
	}
}
